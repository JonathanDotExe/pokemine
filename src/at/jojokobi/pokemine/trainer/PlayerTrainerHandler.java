package at.jojokobi.pokemine.trainer;

import static at.jojokobi.pokemine.pokemon.PokemonFileProvider.*;
import static at.jojokobi.pokemine.moves.MoveLoader.*;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import at.jojokobi.mcutil.io.XMLUtil;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.gui.StarterChooseGUI;
import at.jojokobi.pokemine.gui.TradeGUI;
import at.jojokobi.pokemine.gui.TrainerGUI;
import at.jojokobi.pokemine.moves.MoveHandler;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.PokemonSpecies;
import at.jojokobi.pokemine.pokemon.PokemonType;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;
import at.jojokobi.pokemine.pokemon.status.PrimStatChange;

public class PlayerTrainerHandler implements Listener{
	
	//Constants
	public static final String TRAINER_ELEMENT = "trainer";
	public static final String TRAINER_NAME_ELEMENT = "name";
	public static final String TRAINER_RANK_ELEMENT = "rank";
	public static final String TRAINER_PARTY_ELEMENT = "party";
	public static final String TRAINER_PC_ELEMENT = "pc";
	public static final String TRAINER_BADGES_ELEMENT = "badges";
	public static final String TRAINER_BADGE_ELEMENT = "badge";
	public static final String TRAINER_POKEDEX_ELEMENT = "pokedex";
	public static final String TRAINER_POKEDEX_ENTRY_ELEMENT = "entry";
	public static final String TRAINER_MONEY_ELEMENT = "money";
	
	public static final String BADGE_TYPE_ELEMENT = "type";
	public static final String BADGE_LEVEL_ELEMENT = "level";
	
	public static final String POKEMON_ELEMENT = "pokemon";
	public static final String POKEMON_SPECIES_ELEMENT = "species";
	public static final String POKEMON_NAME_ELEMENT = "name";
	public static final String POKEMON_LEVEL_ELEMENT = "level";
	public static final String POKEMON_EP_ELEMENT = "ep";
	public static final String POKEMON_DVS_ELEMENT = "dvs";
	public static final String POKEMON_FP_ELEMENT = "fp";
	public static final String POKEMON_PRIM_STAT_ELEMENT = "prim_stat_change";
	public static final String POKEMON_SHINY_ELEMENT = "shiny";
	public static final String POKEMON_WALKED_DISTANCE_ELEMENT = "walked_distance";
	public static final String POKEMON_SELECTED_MOVE_ELEMENT = "selected_move";

	public static final String POKEMON_MOVES_ELEMENT = "moves";
	
	public static final String PATH = "/players";
	
	//Private Variables
	private List<Trainer> trainers = new ArrayList<Trainer>();
	private PokeminePlugin plugin;
	private File savefolder;
	
	
	public PlayerTrainerHandler (PokeminePlugin plugin) {
		this (plugin, new File (plugin.getDataFolder(), PATH));
	}
	
	public PlayerTrainerHandler (PokeminePlugin plugin, File savefolder) {
		this.plugin = plugin;
		this.savefolder = savefolder;
		savefolder.mkdirs();
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getClickedInventory() == event.getWhoClicked().getInventory()) {
			//Show player GUI
			if (event.getWhoClicked() instanceof Player) {
				Player player = (Player) event.getWhoClicked();
				if (event.getClick() == ClickType.DOUBLE_CLICK) {
					Trainer trainer = plugin.getPlayerTrainerHandler().getTrainer(player);
					TrainerGUI gui = new TrainerGUI(player, trainer, plugin);
					plugin.getGUIHandler().addGUI(gui);
					gui.show();
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin (PlayerJoinEvent event) {
		//Load
		Player player = event.getPlayer();
		Trainer trainer = loadTrainerWithGUI(player);
		trainers.add(trainer);
		player.sendMessage("Hello " + trainer.getRank().getName() + " " + trainer.getName() + "!");
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit (PlayerQuitEvent event) {
		//Save
		Player player = event.getPlayer();
		Trainer trainer = getTrainer(player);
		saveTrainer(trainer, savefolder, PokemonHandler.getInstance());
		trainers.remove(trainer);
	}
	
	@EventHandler
	public void onDisable (PluginDisableEvent event) {
		if (event.getPlugin() == plugin) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				Trainer trainer = getTrainer(player);
				saveTrainer(trainer, savefolder, PokemonHandler.getInstance());
				trainers.remove(trainer);
			}
		}
	}
	
	@EventHandler
	public void onEnable (PluginEnableEvent event) {
		if (event.getPlugin() == plugin) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				Trainer trainer = loadTrainerWithGUI(player);
				trainers.add(trainer);
				player.sendMessage("Hello " + trainer.getRank() + " " + trainer.getName() + "!");
			}
		}
	}
	
	/**
	 * Checks battle input
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerInteract (PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Trainer trainer = getTrainer(player);
		Battle battle = plugin.getBattleHandler().getTrainersBattle(trainer);
		//Checks if player is batteling
		if (battle != null) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
//				for (Pokemon pokemon : battle.getPokemon()) {
//					if (pokemon.getOwner() == trainer && !pokemon.hasNextAction(battle)) {
//						BattleGUI gui = new BattleGUI(trainer, battle, battle.containerForPokemon(pokemon), plugin);
//						plugin.getGUIHandler().addGUI(gui);
//						gui.show();
//					}
//				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageEntity (EntityDamageByEntityEvent event) {
		//Checks if a Player hit an ArmorStand
		if (event.getDamager() instanceof Player) {
			if (event.getEntity() instanceof ArmorStand) {
				ArmorStand stand = (ArmorStand) event.getEntity();
				Player player = (Player) event.getDamager();
				PokemonEntity entity = plugin.getEntityHandler().getCustomEntityForEntity(stand, PokemonEntity.class);
				Trainer trainer = getTrainer(player);
				if (plugin.getBattleHandler().getTrainersBattle(trainer) == null && entity != null && entity.getPokemon().getOwner() != trainer && !plugin.getBattleHandler().isBattling(entity.getPokemon()) && plugin.getBattleHandler().getTrainersBattle(entity.getPokemon().getOwner()) == null) {
					Pokemon pokemon = trainer.getNextUsablePokemon();
					if (pokemon != null && !plugin.getBattleHandler().isBattling(pokemon) ) {
						new Battle(plugin.getBattleHandler(), pokemon, entity.getPokemon()).start();
						trainer.message("You encountered a wild " + entity.getPokemon().getName() + "!");
						plugin.getEntityHandler().addEntity(new PokemonEntity(player.getLocation(), pokemon, plugin.getEntityHandler()));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteractAtEntity (PlayerInteractAtEntityEvent event) {
		//Check trade
		if (event.getPlayer().isSneaking() && event.getRightClicked() instanceof Player) {
			Trainer player = getTrainer(event.getPlayer());
			Trainer other = getTrainer((Player) event.getRightClicked());
			TradeGUI gui = new TradeGUI(event.getPlayer(), player, (Player) event.getRightClicked(), other, PokemonHandler.getInstance(), plugin.getGUIHandler());
			plugin.getGUIHandler().addGUI(gui);
			gui.show();
		}
	}
	
	@EventHandler
	public void onPlayerMove (PlayerMoveEvent event) {
		double distance = event.getFrom().distance(event.getTo());
		for (Pokemon poke : getTrainer(event.getPlayer()).getParty()) {
			if (poke != null) {
				poke.addWalkedDistance(distance/10);
			}
		}
	}
	
	@EventHandler
	public void onPlayerExpChangeEvent (PlayerExpChangeEvent event) {
		if (event.getAmount() > 0) {
			Trainer trainer = getTrainer(event.getPlayer());
			for (Pokemon pokemon : trainer.getParty()) {
				if (pokemon != null) {
					pokemon.gainEp((int) (event.getAmount() * pokemon.getLevel()/10.0f));
				}
			}
		}
	}
	
	/**
	 * 
	 * Returns the Trainer representation of the player.
	 * 
	 * @param player
	 * @return
	 */
	public Trainer getTrainer (Player player) {
		Trainer trainer = null;
		boolean found = false;
		for (int i = 0; i < trainers.size() && !found; i++) {
			Trainer t = trainers.get(i);
			if (t.getEntity().equals(player)) {
				trainer = t;
				found = true;
			}
		}
		if (!found) {
			trainer = loadTrainerWithGUI(player);
		}
		return trainer;
	}
	
	private Trainer loadTrainerWithGUI (Player player) {
		Trainer trainer = loadTrainer(player, savefolder, PokemonHandler.getInstance(), MoveHandler.getInstance(), TrainerRankHandler.getInstance());
		if (trainer == null) {
			trainer = new PlayerTrainer(player);
			trainer.setName(player.getDisplayName());
			trainer.setMoney(3000);
			StarterChooseGUI gui = new StarterChooseGUI(player, trainer, PokemonHandler.getInstance());
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				@Override
				public void run() {
					plugin.getGUIHandler().addGUI(gui);
					gui.show();
				}
			}, 1L);
		}
		return trainer;
	}
	
	/**
	 * 
	 * Parses a saved Trainer
	 * 
	 * @param player
	 * @param folder
	 * @param handler
	 * @param moveHandler
	 * @return
	 */
	public static Trainer loadTrainer (Player player, File folder, PokemonHandler handler, MoveHandler moveHandler, TrainerRankHandler rankHandler) {
		PlayerTrainer trainer = null;
		File yamlFile = new File(folder, player.getUniqueId().toString() + ".yml");
		if (yamlFile.exists()) {
			//Load YAML
			FileConfiguration config = new YamlConfiguration();
			try {
				config.load(yamlFile);
				trainer = config.getSerializable(TRAINER_ELEMENT, PlayerTrainer.class);
				trainer.setPlayer(player);
				trainer.setName(player.getDisplayName());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
		else {
			String filename = player.getUniqueId().toString() + ".xml";
			try (InputStream input = new FileInputStream(new File (folder, filename))) {
				//Initialization
				PlayerTrainer temp = new PlayerTrainer(player);
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true);
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(input);
				document.getDocumentElement().normalize();
				Element trainerElement = document.getDocumentElement();
				loadTrainer(temp, trainerElement, handler, moveHandler, rankHandler);
				trainer = temp;
				trainer.setName(player.getDisplayName());
			}
			catch (FileNotFoundException e) {
//				StarterChooseGUI gui = new StarterChooseGUI(trainer, handler);
//				guiHandler.addGUI(gui);
//				gui.show();
//				player.sendMessage("Hello " + player.getName() + "! Seems like it's the fist time you're here.");
//				player.sendMessage("Better you take this Squirtle real quick to be save out there");
//				trainer.givePokemon(new Pokemon(handler.getItem("squirtle"), (byte) 5));
			}
			catch (ParserConfigurationException | IOException | SAXException e) {
				e.printStackTrace();
			}
		}
		return trainer;
	}
	
	public static void loadTrainer (Trainer trainer, Element element, PokemonHandler handler, MoveHandler moveHandler, TrainerRankHandler rankHandler) {
		//Load
		trainer.setName(element.getElementsByTagName(TRAINER_NAME_ELEMENT).item(0).getTextContent());
		trainer.setRank(rankHandler.getItem(PokeminePlugin.POKEMINE_NAMESPACE, element.getElementsByTagName(TRAINER_RANK_ELEMENT).item(0).getTextContent()));
		//Party
		Element partyNode = (Element) element.getElementsByTagName(TRAINER_PARTY_ELEMENT).item(0);
		NodeList partyNodes = partyNode.getElementsByTagName(POKEMON_ELEMENT);
		for (int i = 0; i < partyNodes.getLength(); i++) {
			Pokemon pokemon = parsePokemon(trainer, (Element) partyNodes.item(i), handler, moveHandler);
			trainer.givePokemon(pokemon);
		}
		//PC
		Element pcNode = (Element) element.getElementsByTagName(TRAINER_PC_ELEMENT).item(0);
		NodeList pcNodes = pcNode.getElementsByTagName(POKEMON_ELEMENT);
		for (int i = 0; i < pcNodes.getLength(); i++) {
			Pokemon pokemon = parsePokemon(trainer, (Element) pcNodes.item(i), handler, moveHandler);
			trainer.givePokemon(pokemon);
			trainer.moveToPc(pokemon);
		}
		//Badges
		NodeList badgesNodes = element.getElementsByTagName(TRAINER_BADGES_ELEMENT);
		if (badgesNodes.getLength() > 0 &&  badgesNodes.item(0).getNodeType() == Node.ELEMENT_NODE) {
			NodeList badges = ((Element) badgesNodes.item(0)).getElementsByTagName(TRAINER_BADGE_ELEMENT);
			for (int i = 0; i < badges.getLength(); i++) {
				if (badges.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element badge = (Element) badges.item(i);
					Node type = badge.getElementsByTagName(BADGE_TYPE_ELEMENT).item(0);
					Node level = badge.getElementsByTagName(BADGE_LEVEL_ELEMENT).item(0);
					trainer.recieveBadge(PokemonType.stringToType(type.getTextContent()), Byte.parseByte(level.getTextContent()));
				}
			}
		}
		//Player trainer stuff
		Trainer player = trainer;
		//Pokedex
		//Get Pokedex Node
		NodeList pokedexNodes = element.getElementsByTagName(TRAINER_POKEDEX_ELEMENT);
		//Get Entry
		if (pokedexNodes.getLength() > 0 && pokedexNodes.item(0).getNodeType() == Node.ELEMENT_NODE) {
			NodeList entryNodes = ((Element) pokedexNodes.item(0)).getElementsByTagName(TRAINER_POKEDEX_ENTRY_ELEMENT);
			for (int i = 0; i < entryNodes.getLength(); i++) {
				player.addToPokedex(handler.getItem(PokeminePlugin.POKEMINE_NAMESPACE, entryNodes.item(i).getTextContent()));
			}
		}
		//Money
		NodeList moneyNodes = element.getElementsByTagName(TRAINER_MONEY_ELEMENT);
		if (moneyNodes.getLength() > 0) {
			player.setMoney(Integer.parseInt(moneyNodes.item(0).getTextContent()));
		}
	}
	
	/**
	 * 
	 * Parses a saved Pokemon
	 * 
	 * @param pkElement
	 * @param handler
	 * @param moveHandler
	 * @return
	 */
	public static Pokemon parsePokemon (Trainer owner, Element pkElement, PokemonHandler handler, MoveHandler moveHandler) {
		Pokemon pokemon = new Pokemon (handler.getItem(PokeminePlugin.POKEMINE_NAMESPACE, pkElement.getElementsByTagName(POKEMON_SPECIES_ELEMENT).item(0).getTextContent()), Byte.parseByte(pkElement.getElementsByTagName(POKEMON_LEVEL_ELEMENT).item(0).getTextContent()), owner);
		pokemon.setEp(Integer.parseInt(pkElement.getElementsByTagName(POKEMON_EP_ELEMENT).item(0).getTextContent()));
		pokemon.setFriendship(Integer.parseInt(pkElement.getElementsByTagName(POKEMON_FRIENDSHIP_ELEMENT).item(0).getTextContent()));
		//Name
		if (pkElement.getElementsByTagName(POKEMON_SPECIES_NAME_ELEMENT).getLength() > 0) {
			pokemon.setName(pkElement.getElementsByTagName(POKEMON_SPECIES_NAME_ELEMENT).item(0).getTextContent());
		}
		//DV
		if (pkElement.getElementsByTagName(POKEMON_DVS_ELEMENT).getLength() > 0 && pkElement.getElementsByTagName(POKEMON_DVS_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element dvs = (Element) pkElement.getElementsByTagName(POKEMON_DVS_ELEMENT).item(0);
			pokemon.setHealthDV(Integer.parseInt(dvs.getElementsByTagName(POKEMON_HEALTH_ELEMENT).item(0).getTextContent()));
			pokemon.setAttackDV(Integer.parseInt(dvs.getElementsByTagName(POKEMON_ATTACK_ELEMENT).item(0).getTextContent()));
			pokemon.setDefenseDV(Integer.parseInt(dvs.getElementsByTagName(POKEMON_DEFENSE_ELEMENT).item(0).getTextContent()));
			pokemon.setSpecialAttackDV(Integer.parseInt(dvs.getElementsByTagName(POKEMON_SPECIAL_ATTACK_ELEMENT).item(0).getTextContent()));
			pokemon.setSpecialDefenseDV(Integer.parseInt(dvs.getElementsByTagName(POKEMON_SPECIAL_DEFENSE_ELEMENT).item(0).getTextContent()));
			pokemon.setSpeedDV(Integer.parseInt(dvs.getElementsByTagName(POKEMON_SPEED_ELEMENT).item(0).getTextContent()));
		}
		//FP
		if (pkElement.getElementsByTagName(POKEMON_FP_ELEMENT).getLength() > 0 && pkElement.getElementsByTagName(POKEMON_FP_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element fp = (Element) pkElement.getElementsByTagName(POKEMON_FP_ELEMENT).item(0);
			pokemon.setHealthEV(Integer.parseInt(fp.getElementsByTagName(POKEMON_HEALTH_ELEMENT).item(0).getTextContent()));
			pokemon.setAttackEV(Integer.parseInt(fp.getElementsByTagName(POKEMON_ATTACK_ELEMENT).item(0).getTextContent()));
			pokemon.setDefenseEV(Integer.parseInt(fp.getElementsByTagName(POKEMON_DEFENSE_ELEMENT).item(0).getTextContent()));
			pokemon.setSpecialAttackEV(Integer.parseInt(fp.getElementsByTagName(POKEMON_SPECIAL_ATTACK_ELEMENT).item(0).getTextContent()));
			pokemon.setSpecialDefenseEV(Integer.parseInt(fp.getElementsByTagName(POKEMON_SPECIAL_DEFENSE_ELEMENT).item(0).getTextContent()));
			pokemon.setSpeedEV(Integer.parseInt(fp.getElementsByTagName(POKEMON_SPEED_ELEMENT).item(0).getTextContent()));
		}
		//Moves
		if (pkElement.getElementsByTagName(MOVE_ELEMENT).getLength() > 0 && pkElement.getElementsByTagName(MOVE_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
			NodeList moves = pkElement.getElementsByTagName(MOVE_ELEMENT);
			MoveInstance[] moveset = new MoveInstance[Pokemon.MAX_MOVES];
			for (int i = 0; i < Math.min(moves.getLength(), moveset.length); i++) {
				if (moves.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element moveElement = (Element) moves.item(i);
					String identifier = moveElement.getElementsByTagName(MOVE_IDENTIFIER_ELEMENT).item(0).getTextContent();
					int pp = Integer.parseInt(moveElement.getElementsByTagName(MOVE_PP_ELEMENT).item(0).getTextContent());
					int maxpp = Integer.parseInt(moveElement.getElementsByTagName(MOVE_MAXPP_ELEMENT).item(0).getTextContent());
					MoveInstance move = new MoveInstance(moveHandler.getItem(PokeminePlugin.POKEMINE_NAMESPACE, identifier));
					move.setMaxPP(maxpp);
					move.setPp(pp);
					moveset[i] = move;
				}
			}
			pokemon.setMoves(moveset);
		}
		//Health
		boolean healthSet = false;
		NodeList healthNodes = pkElement.getElementsByTagName(POKEMON_HEALTH_ELEMENT);
		for (int i = 0; i < healthNodes.getLength() && !healthSet; i++) {
			Element health = (Element) healthNodes.item(i);
			if (health.getParentNode() == pkElement) {
				pokemon.setHealth(Integer.parseInt(health.getTextContent()));
				healthSet = true;
			}
		}
		//Prim Stat Change
		NodeList statNodes = pkElement.getElementsByTagName(POKEMON_PRIM_STAT_ELEMENT);
		if (statNodes.getLength() > 0 && statNodes.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element stat = (Element) statNodes.item(0);
			pokemon.setPrimStatChange(PrimStatChange.parsePrimStatChange(stat));
		}
		//Shiny
		NodeList shinyNodes = pkElement.getElementsByTagName(POKEMON_SHINY_ELEMENT);
		if (shinyNodes.getLength() > 0) {
			Node shiny = shinyNodes.item(0);
			pokemon.setShiny(Boolean.parseBoolean(shiny.getTextContent()));
		}
		//Walked Distance
		NodeList distanceNodes = pkElement.getElementsByTagName(POKEMON_WALKED_DISTANCE_ELEMENT);
		if (distanceNodes.getLength() > 0) {
			Node distance = distanceNodes.item(0);
			pokemon.setWalkedDistance(Double.parseDouble(distance.getTextContent()));
		}
		//Item
		NodeList itemNodes = pkElement.getElementsByTagName(XMLUtil.ITEM_TAG);
		if (itemNodes.getLength() > 0 && itemNodes.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element item = (Element) itemNodes.item(0);
			pokemon.setItem(XMLUtil.xmlToItem(item));
		}
		
		//Item
		Node selectedMove = pkElement.getElementsByTagName(POKEMON_SELECTED_MOVE_ELEMENT).item(0);
		if (selectedMove != null) {
			try {
				pokemon.setSelectedMoveIndex(Integer.parseInt(selectedMove.getTextContent()));
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		return pokemon;
	}
	
	/**
	 * 
	 * Saves a trainers game state
	 * 
	 * @param trainer
	 * @param folder
	 * @param handler
	 */
	public static void saveTrainer (Trainer trainer, File folder, PokemonHandler handler) {
		String filename = trainer.getEntity().getUniqueId().toString() + ".yml";
		FileConfiguration config = new YamlConfiguration();
		config.set(TRAINER_ELEMENT, trainer);
		try {
			config.save(new File(folder, filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		String filename = trainer.getEntity().getUniqueId().toString() + ".xml";
//		try (FileOutputStream output = new FileOutputStream(new File (folder, filename))){
//			//Initialization
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			factory.setNamespaceAware(true);
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			Document document = builder.newDocument();
//			//Root-Element
//			Element trainerElement = document.createElement(TRAINER_ELEMENT);
//			trainerElement.setAttribute("xmlns", "https://jojokobi.lima-city.de/pokemine");
//			trainerElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
//			trainerElement.setAttribute("xsi:schemaLocation", "https://jojokobi.lima-city.de/pokemine https://jojokobi.lima-city.de/pokemine/trainer");
//			document.appendChild(trainerElement);
//			//Data
//			saveTrainer(trainer, document, trainerElement, handler);
//			//Save to File
//			TransformerFactory transformerFactory = TransformerFactory.newInstance();
//			Transformer transformer = transformerFactory.newTransformer();
//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			DOMSource source = new DOMSource(document);
//			StreamResult result = new StreamResult(output);
//			transformer.transform(source, result);
//		}
//		catch (ParserConfigurationException | TransformerException | IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public static void saveTrainer (Trainer trainer, Document document, Element trainerElement, PokemonHandler handler) {
		//Data
		Element name = document.createElement(TRAINER_NAME_ELEMENT);
		name.setTextContent(trainer.getName());
		Element rank = document.createElement(TRAINER_RANK_ELEMENT);
		rank.setTextContent(trainer.getRank().getIdentifier());
		//Party
		Element party = document.createElement(TRAINER_PARTY_ELEMENT);
		for (Pokemon pokemon : trainer.getParty()) {
			if (pokemon != null) {
				Element pkElement = document.createElement(POKEMON_ELEMENT);
				savePokemon(pokemon, document, pkElement);
				party.appendChild(pkElement);
			}
		}
		//PC
		Element pc = document.createElement(TRAINER_PC_ELEMENT);
		for (Pokemon pokemon : trainer.getPokemon()) {
			Element pkElement = document.createElement(POKEMON_ELEMENT);
			savePokemon(pokemon, document, pkElement);
			pc.appendChild(pkElement);
			
		}
		//Badges
		Element badges = document.createElement(TRAINER_BADGES_ELEMENT);
		for (PokemonType badge : trainer.getBadges().keySet()) {
			Element badgeElement = document.createElement(TRAINER_BADGE_ELEMENT);
			Element type = document.createElement(BADGE_TYPE_ELEMENT);
			type.setTextContent(badge.toString());
			Element level = document.createElement(BADGE_LEVEL_ELEMENT);
			level.setTextContent(trainer.getBadge(badge) + "");
			badgeElement.appendChild(type);
			badgeElement.appendChild(level);
			badges.appendChild(badgeElement);
		}
		trainerElement.appendChild(name);
		trainerElement.appendChild(rank);
		//Money
		Element money = document.createElement(TRAINER_MONEY_ELEMENT);
		money.setTextContent(trainer.getMoney() + "");
		trainerElement.appendChild(money);
		
		trainerElement.appendChild(party);
		trainerElement.appendChild(pc);
		trainerElement.appendChild(badges);
		//Pokedex
		//Pokedex
		Element pokedex = document.createElement(TRAINER_POKEDEX_ELEMENT);
		//Add Entries
		for (PokemonSpecies species : trainer.getPokedexSpecies()) {
			Element entry = document.createElement(TRAINER_POKEDEX_ENTRY_ELEMENT);
			entry.setTextContent(species.getIdentifier());
			pokedex.appendChild(entry);
		}
		trainerElement.appendChild(pokedex);
	}
	
	/**
	 *
	 * Saves a Pokemon to a XML Element
	 *
	 * @param pokemon	
	 * @param document
	 * @param pkElement
	 */
	public static void savePokemon (Pokemon pokemon, Document document, Element pkElement) {
		//Pokemon Data
		Element species = document.createElement(POKEMON_SPECIES_ELEMENT);
		species.setTextContent(pokemon.getSpecies().getIdentifier());
		Element name = document.createElement(POKEMON_SPECIES_NAME_ELEMENT);
		name.setTextContent(pokemon.getName());
		Element level = document.createElement(POKEMON_LEVEL_ELEMENT);
		level.setTextContent(pokemon.getLevel() + "");
		Element ep = document.createElement(POKEMON_EP_ELEMENT);
		ep.setTextContent(pokemon.getEp() + "");
		Element friendship = document.createElement(POKEMON_FRIENDSHIP_ELEMENT);
		friendship.setTextContent(pokemon.getFriendship() + "");
		//DVS
		Element dvs = document.createElement(POKEMON_DVS_ELEMENT);
		Element healthDV = document.createElement(POKEMON_HEALTH_ELEMENT);
		healthDV.setTextContent(pokemon.getHealthDV() + "");
		Element attackDV = document.createElement(POKEMON_ATTACK_ELEMENT);
		attackDV.setTextContent(pokemon.getAttackDV() + "");
		Element defenseDV = document.createElement(POKEMON_DEFENSE_ELEMENT);
		defenseDV.setTextContent(pokemon.getDefenseDV() + "");
		Element specialAttackDV = document.createElement(POKEMON_SPECIAL_ATTACK_ELEMENT);
		specialAttackDV.setTextContent(pokemon.getSpecialAttackDV() + "");
		Element specialDefenseDV = document.createElement(POKEMON_SPECIAL_DEFENSE_ELEMENT);
		specialDefenseDV.setTextContent(pokemon.getSpecialDefenseDV() + "");
		Element speedDV = document.createElement(POKEMON_SPEED_ELEMENT);
		speedDV.setTextContent(pokemon.getSpeedDV() + "");
		dvs.appendChild(healthDV);
		dvs.appendChild(attackDV);
		dvs.appendChild(defenseDV);
		dvs.appendChild(specialAttackDV);
		dvs.appendChild(specialDefenseDV);
		dvs.appendChild(speedDV);
		//FP
		Element fp = document.createElement(POKEMON_FP_ELEMENT);
		Element healthFP = document.createElement(POKEMON_HEALTH_ELEMENT);
		healthFP.setTextContent(pokemon.getHealthEV() + "");
		Element attackFP = document.createElement(POKEMON_ATTACK_ELEMENT);
		attackFP.setTextContent(pokemon.getAttackEV() + "");
		Element defenseFP = document.createElement(POKEMON_DEFENSE_ELEMENT);
		defenseFP.setTextContent(pokemon.getDefenseEV() + "");
		Element specialAttackFP = document.createElement(POKEMON_SPECIAL_ATTACK_ELEMENT);
		specialAttackFP.setTextContent(pokemon.getSpecialAttackEV() + "");
		Element specialDefenseFP = document.createElement(POKEMON_SPECIAL_DEFENSE_ELEMENT);
		specialDefenseFP.setTextContent(pokemon.getSpecialDefenseEV() + "");
		Element speedFP = document.createElement(POKEMON_SPEED_ELEMENT);
		speedFP.setTextContent(pokemon.getSpeedEV() + "");
		fp.appendChild(healthFP);
		fp.appendChild(attackFP);
		fp.appendChild(defenseFP);
		fp.appendChild(specialAttackFP);
		fp.appendChild(specialDefenseFP);
		fp.appendChild(speedFP);
		//Moves
		Element moves = document.createElement(POKEMON_MOVES_ELEMENT);
		for (MoveInstance move : pokemon.getMoves()) {
			if (move != null) {
			Element moveElement = document.createElement(MOVE_ELEMENT);
				Element identifier = document.createElement(MOVE_IDENTIFIER_ELEMENT);
				identifier.setTextContent(move.getMove().getIdentifier());
				Element pp = document.createElement(MOVE_PP_ELEMENT);
				pp.setTextContent(move.getPp() + "");
				Element maxpp = document.createElement(MOVE_MAXPP_ELEMENT);
				maxpp.setTextContent(move.getMaxPP() + "");
				moveElement.appendChild(identifier);
				moveElement.appendChild(pp);
				moveElement.appendChild(maxpp);
				moves.appendChild(moveElement);
			}
		}
		//Health
		Element health = document.createElement(POKEMON_HEALTH_ELEMENT);
		health.setTextContent(pokemon.getHealth() + "");
		//Stat Change
		Element statChange = null;
		if (pokemon.getPrimStatChange() != null) {
			statChange = document.createElement(POKEMON_PRIM_STAT_ELEMENT);
			pokemon.getPrimStatChange().saveToXML(statChange, document);
		}
		//Shiny
		Element shiny = document.createElement(POKEMON_SHINY_ELEMENT);
		shiny.setTextContent(pokemon.isShiny() + "");
		//Distance
		Element distance = document.createElement(POKEMON_WALKED_DISTANCE_ELEMENT);
		distance.setTextContent(pokemon.getWalkedDistance() + "");
		//Item
		Element item = null;
		if (pokemon.getItem() != null) {
			item = XMLUtil.itemToXML(document, pokemon.getItem());
		}
		//Selected item
		Element selectedMove = document.createElement(POKEMON_SELECTED_MOVE_ELEMENT);
		selectedMove.setTextContent(pokemon.getSelectedMoveIndex() + "");
		//Append Children
		pkElement.appendChild(species);
		pkElement.appendChild(name);
		pkElement.appendChild(level);
		pkElement.appendChild(ep);
		pkElement.appendChild(friendship);
		pkElement.appendChild(dvs);
		pkElement.appendChild(fp);
		pkElement.appendChild(moves);
		pkElement.appendChild(health);
		if (statChange != null) {
			pkElement.appendChild(statChange);
		}
		pkElement.appendChild(shiny);
		pkElement.appendChild(distance);
		if (item != null) {
			pkElement.appendChild(item);
		}
		pkElement.appendChild(selectedMove);
	}
	
}
