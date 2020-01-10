package at.jojokobi.pokemine.pokemon;

import static at.jojokobi.pokemine.moves.MoveLoader.MOVE_IDENTIFIER_ELEMENT;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import at.jojokobi.mcutil.FileLoader;
import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.loot.LootInventory;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.moves.MoveLearnCondition;
import at.jojokobi.pokemine.pokemon.evolution.CustomItemCondition;
import at.jojokobi.pokemine.pokemon.evolution.EvolutionCause;
import at.jojokobi.pokemine.pokemon.evolution.EvolutionCondition;
import at.jojokobi.pokemine.pokemon.evolution.FriendshipCondition;
import at.jojokobi.pokemine.pokemon.evolution.HeldItemCondition;
import at.jojokobi.pokemine.pokemon.evolution.LevelCondition;
import at.jojokobi.pokemine.pokemon.evolution.TradeCondition;
import at.jojokobi.pokemine.spawning.BiomeSpawnCondition;
import at.jojokobi.pokemine.spawning.DaySpawnCondition;
import at.jojokobi.pokemine.spawning.RainSpawnCondition;
import at.jojokobi.pokemine.spawning.SpawnChance;
import at.jojokobi.pokemine.spawning.SpawnCondition;

import static at.jojokobi.pokemine.pokemon.PokemonFileProvider.*;

public class PokemonLoader implements FileLoader<PokemonSpecies> {
	
	private static PokemonLoader instance = null;
	
	public static PokemonLoader getInstance () {
		if (instance == null) {
			instance = new PokemonLoader();
		}
		return instance;
	}

	private PokemonLoader() {
		
	}
	
	public PokemonSpecies load (InputStream input) {
		PokemonSpecies species = null;
		try {
			//Initialization
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(input);
			document.getDocumentElement().normalize();
			
			//Load
			
			//Pokedex
			Element pokedex = (Element) document.getElementsByTagName(POKEDEX_ELEMENT).item(0);
			//Identifier
			species = new PokemonSpecies(PokeminePlugin.POKEMINE_NAMESPACE, document.getElementsByTagName(POKEMON_IDENTIFIER_ELEMENT).item(0).getTextContent());
			//Name
			species.setName(pokedex.getElementsByTagName(POKEMON_SPECIES_NAME_ELEMENT).item(0).getTextContent());
			//Identifier
			//Index
			species.setIndex(Integer.parseInt(pokedex.getElementsByTagName(POKEMON_INDEX_ELEMENT).item(0).getTextContent()));
			//Types
			Element typesNode = (Element) document.getElementsByTagName(POKEMON_TYPES_ELEMENT).item(0);
			NodeList typeNodes = typesNode.getElementsByTagName(POKEMON_TYPE_ELEMENT);
			if (typeNodes.getLength() > 0) {
				PokemonType[] types = new PokemonType[typeNodes.getLength()];
				for (int i = 0; i < types.length; i++) {
					types[i] = PokemonType.stringToType(typeNodes.item(i).getTextContent());
				}
				species.setTypes(new ArrayList<>(Arrays.asList(types)));
			}
			//Breeding
			species.setMaleChance(Float.parseFloat(pokedex.getElementsByTagName(POKEMON_MALE_CHANCE_ELEMENT).item(0).getTextContent()));
			species.setEggGroups(new ArrayList<>(Arrays.asList(nodeListToArray(pokedex.getElementsByTagName(POKEMON_EGG_GROUP_ELEMENT)))));
			species.setEggCycles(Integer.parseInt(pokedex.getElementsByTagName(POKEMON_EGG_STEPS_ELEMENT).item(0).getTextContent())/256);
			//Apperance
			species.setCategory(pokedex.getElementsByTagName(POKEMON_CATEGORY_ELEMENT).item(0).getTextContent());
			species.setSize(Float.parseFloat(pokedex.getElementsByTagName(POKEMON_SIZE_ELEMENT).item(0).getTextContent()));
			species.setWeight(Float.parseFloat(pokedex.getElementsByTagName(POKEMON_WEIGHT_ELEMENT).item(0).getTextContent()));
			species.setColor(pokedex.getElementsByTagName(POKEMON_COLOR_ELEMENT).item(0).getTextContent());
			species.setDescription(pokedex.getElementsByTagName(POKEMON_DESCRIPTION_ELEMENT).item(0).getTextContent());
			Node legendary = pokedex.getElementsByTagName(POKEMON_LEGENDARY_ELEMENT).item(0);
			if (legendary != null) {
				species.setLegendary(Boolean.parseBoolean(legendary.getTextContent()));
			}
			Node form = pokedex.getElementsByTagName(POKEMON_ALTERNATIVE_FORM_ELEMENT).item(0);
			if (form != null) {
				species.setFormOf(new NamespacedEntry(PokeminePlugin.POKEMINE_NAMESPACE, form.getTextContent()));
			}
			//Base Values
			Element baseValues = (Element) document.getElementsByTagName(BASE_VALUES_ELEMENT).item(0);
			//Stat Values
			PokemonValueSet baseValueSet = new PokemonValueSet();
			baseValueSet.setHealth(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_HEALTH_ELEMENT).item(0).getTextContent()));
			baseValueSet.setAttack(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_ATTACK_ELEMENT).item(0).getTextContent()));
			baseValueSet.setDefense(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_DEFENSE_ELEMENT).item(0).getTextContent()));
			baseValueSet.setSpecialAttack(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_SPECIAL_ATTACK_ELEMENT).item(0).getTextContent()));
			baseValueSet.setSpecialDefense(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_SPECIAL_DEFENSE_ELEMENT).item(0).getTextContent()));
			baseValueSet.setSpeed(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_SPEED_ELEMENT).item(0).getTextContent()));
			species.setBaseValues(baseValueSet);
			species.setFriendship(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_FRIENDSHIP_ELEMENT).item(0).getTextContent()));
			species.setCatchRate(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_CATCH_RATE_ELEMENT).item(0).getTextContent()));
			species.setLevelEP(LevelSpeed.stringToLevelSpeed(baseValues.getElementsByTagName(POKEMON_LEVEL_EP_ELEMENT).item(0).getTextContent()));
			species.setWildLevel(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_WILD_LEVEL_ELEMENT).item(0).getTextContent()));
			//Moves
			NodeList moves = document.getElementsByTagName(MOVE_ELEMENT);
			species.clearMoves();
			for (int i = 0; i < moves.getLength(); i++) {
				if (moves.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element moveElement = (Element) moves.item(i);
					String identifier = moveElement.getElementsByTagName(MOVE_IDENTIFIER_ELEMENT).item(0).getTextContent();
					int level = Integer.parseInt(moveElement.getElementsByTagName(MOVE_LEVEL_ELEMENT).item(0).getTextContent());
					species.addMove(new MoveLearnCondition(new NamespacedEntry(PokeminePlugin.POKEMINE_NAMESPACE, identifier), level));
				}
			}
			//Evolutions
			NodeList evolutions = document.getElementsByTagName(EVOLUTION_ELEMENT);
			for (int i = 0; i < evolutions.getLength(); i++) {
				if (evolutions.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element evoElement = (Element) evolutions.item(i);
					List<EvolutionCondition> conditions = new ArrayList<>();
					if (evoElement.getElementsByTagName(EVOLUTION_ITEM_ELEMENT).getLength() > 0) {
						conditions.add(new CustomItemCondition(new NamespacedEntry(PokeminePlugin.POKEMINE_NAMESPACE, evoElement.getElementsByTagName(EVOLUTION_ITEM_ELEMENT).item(0).getTextContent())));
					}
					if (evoElement.getElementsByTagName(EVOLUTION_TRADE_ELEMENT).getLength() > 0 && Boolean.parseBoolean(evoElement.getElementsByTagName(EVOLUTION_TRADE_ELEMENT).item(0).getTextContent())) {
						conditions.add(new TradeCondition());
					}
					if (evoElement.getElementsByTagName(EVOLUTION_FRIENDSHIP_ELEMENT).getLength() > 0) {
						conditions.add(new FriendshipCondition(Integer.parseInt(evoElement.getElementsByTagName(EVOLUTION_FRIENDSHIP_ELEMENT).item(0).getTextContent())));
					}
					if (evoElement.getElementsByTagName(EVOLUTION_HELD_ITEM_ELEMENT).getLength() > 0) {
						conditions.add(new HeldItemCondition(Material.valueOf(evoElement.getElementsByTagName(EVOLUTION_HELD_ITEM_ELEMENT).item(0).getTextContent())));
					}
					if (evoElement.getElementsByTagName(MOVE_LEVEL_ELEMENT).getLength() > 0) {
						conditions.add(new LevelCondition(Integer.parseInt(evoElement.getElementsByTagName(MOVE_LEVEL_ELEMENT).item(0).getTextContent())));
					}
					EvolutionCause evolution = new EvolutionCause(new NamespacedEntry(PokeminePlugin.POKEMINE_NAMESPACE, evoElement.getElementsByTagName(EVOLUTION_SPECIES_ELEMENT).item(0).getTextContent()), conditions);
					species.addEvolution(evolution);
				}
			}
			//Field Moves
			NodeList fieldMoveResults = document.getElementsByTagName(POKEMON_FIELDMOVES_ELEMENT);
			if (fieldMoveResults.getLength() > 0 && fieldMoveResults.item(0).getNodeType() == Node.ELEMENT_NODE) {
				Element fieldMovesElement = (Element) fieldMoveResults.item(0);
				species.setWalkSpeed(Float.parseFloat(fieldMovesElement.getElementsByTagName(POKEMON_WALK_ELEMENT).item(0).getTextContent()));
				species.setSurfSpeed(Float.parseFloat(fieldMovesElement.getElementsByTagName(POKEMON_SURF_ELEMENT).item(0).getTextContent()));
				species.setFlySpeed(Float.parseFloat(fieldMovesElement.getElementsByTagName(POKEMON_FLY_ELEMENT).item(0).getTextContent()));
				species.setLavaSurfSpeed(Float.parseFloat(fieldMovesElement.getElementsByTagName(POKEMON_LAVA_SURF_ELEMENT).item(0).getTextContent()));
				species.setJumpSpeed(Float.parseFloat(fieldMovesElement.getElementsByTagName(POKEMON_JUMP_ELEMENT).item(0).getTextContent()));
				species.setClimbSpeed(Float.parseFloat(fieldMovesElement.getElementsByTagName(POKEMON_CLIMB_ELEMENT).item(0).getTextContent()));
			}
			//Loot
			Node loot = document.getElementsByTagName(POKEMON_LOOT_ELEMENT).item(0);
			if (loot != null && loot.getNodeType() == Node.ELEMENT_NODE) {
				species.setLoot(LootInventory.fromXML((Element) loot));
			}
			//Win
			NodeList winResults = document.getElementsByTagName(POKEMON_WIN_ELEMENT);
			if (winResults.getLength() > 0 && winResults.item(0).getNodeType() == Node.ELEMENT_NODE) {
				Element winElement = (Element) winResults.item(0);
				species.setWinXp(Integer.parseInt(winElement.getElementsByTagName(POKEMON_WIN_EP_ELEMENT).item(0).getTextContent()));
				PokemonValueSet evSet = new PokemonValueSet();
				evSet.setHealth(Integer.parseInt(winElement.getElementsByTagName(POKEMON_HEALTH_ELEMENT).item(0).getTextContent()));
				evSet.setAttack(Integer.parseInt(winElement.getElementsByTagName(POKEMON_ATTACK_ELEMENT).item(0).getTextContent()));
				evSet.setDefense(Integer.parseInt(winElement.getElementsByTagName(POKEMON_DEFENSE_ELEMENT).item(0).getTextContent()));
				evSet.setSpecialAttack(Integer.parseInt(winElement.getElementsByTagName(POKEMON_SPECIAL_ATTACK_ELEMENT).item(0).getTextContent()));
				evSet.setSpecialDefense(Integer.parseInt(winElement.getElementsByTagName(POKEMON_SPECIAL_DEFENSE_ELEMENT).item(0).getTextContent()));
				evSet.setSpeed(Integer.parseInt(winElement.getElementsByTagName(POKEMON_SPEED_ELEMENT).item(0).getTextContent()));
				species.setWinEvs(evSet);
			}
			//Spawns
			NodeList spawnResults = document.getElementsByTagName(POKEMON_SPAWNS_ELEMENT);
			if (spawnResults.getLength() > 0 && spawnResults.item(0).getNodeType() == Node.ELEMENT_NODE) {
				Element spawnsElement = (Element) spawnResults.item(0);
				NodeList spawns = spawnsElement.getElementsByTagName(POKEMON_SPAWN_ELEMENT);
				for (int i = 0; i < spawns.getLength(); i++) {
					if (spawns.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element spawn = (Element) spawns.item(i);
						String biomeText = spawn.getElementsByTagName(POKEMON_BIOME_ELEMENT).item(0).getTextContent().toUpperCase();
						Biome biome = null;
						try {
							biome = Biome.valueOf(biomeText);
						}
						catch (IllegalArgumentException e) {
							System.err.println("Error while loading pokemon species " + species.getName() + "!");
							System.err.println("The biome " + biomeText + " does not exist!");
							e.printStackTrace();
						}
						int chance = Integer.parseInt(spawn.getElementsByTagName(POKEMON_CHANCE_ELEMENT).item(0).getTextContent());
						List<SpawnCondition> conditions = new ArrayList<>();
						if (biome != null) {
							conditions.add(new BiomeSpawnCondition(Arrays.asList(biome)));
//							//Add
//							if (day == null && rain == null) {
//								species.addSpawn(new EnvironmentSituation(biome, false, false), chance);
//								species.addSpawn(new EnvironmentSituation(biome, true, false), chance);
//								species.addSpawn(new EnvironmentSituation(biome, false, true), chance);
//								species.addSpawn(new EnvironmentSituation(biome, true, true), chance);
//							}
//							else if (day == null) {
//								species.addSpawn(new EnvironmentSituation(biome, rain, true), chance);
//								species.addSpawn(new EnvironmentSituation(biome, rain, true), chance);
//							}
//							else if (rain == null) {
//								species.addSpawn(new EnvironmentSituation(biome, false, day), chance);
//								species.addSpawn(new EnvironmentSituation(biome, true, day), chance);
//							}
//							else {
//								species.addSpawn(new EnvironmentSituation(biome, rain, day), chance);
//							}
						}
						//Check if rain and day are defined
						if (spawn.getElementsByTagName(POKEMON_DAY_ELEMENT).getLength() > 0) {
							conditions.add(new DaySpawnCondition(Boolean.valueOf(spawn.getElementsByTagName(POKEMON_DAY_ELEMENT).item(0).getTextContent())));
						}
						if (spawn.getElementsByTagName(POKEMON_RAIN_ELEMENT).getLength() > 0) {
							conditions.add(new RainSpawnCondition(Boolean.valueOf(spawn.getElementsByTagName(POKEMON_RAIN_ELEMENT).item(0).getTextContent())));
						}
						species.addSpawn(new SpawnChance(chance, conditions));
					}
				}
			}
		}
		catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
		if (species == null) {
			species = new PokemonSpecies(PokeminePlugin.POKEMINE_NAMESPACE, "missingno");
		}
		return species;
	}
	
	
	private static String[] nodeListToArray (NodeList list) {
		String[] array = new String[list.getLength()];
		for (int i = 0; i < list.getLength(); i++) {
			array[i] = list.item(i).getTextContent();
		}
		return array;
	}

}
