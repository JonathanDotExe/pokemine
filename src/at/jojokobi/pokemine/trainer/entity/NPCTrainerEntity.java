package at.jojokobi.pokemine.trainer.entity;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.mcutil.entity.EntityMapData;
import at.jojokobi.mcutil.entity.NMSEntityUtil;
import at.jojokobi.mcutil.entity.ai.RandomTask;
import at.jojokobi.mcutil.entity.ai.ReturnToSpawnTask;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.BattleHandler;
import at.jojokobi.pokemine.moves.MoveHandler;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;
import at.jojokobi.pokemine.trainer.NPCTrainer;
import at.jojokobi.pokemine.trainer.PlayerTrainerHandler;
import at.jojokobi.pokemine.trainer.Trainer;
import at.jojokobi.pokemine.trainer.TrainerRankHandler;
import at.jojokobi.pokemine.trainer.TrainerUtil;

public class NPCTrainerEntity extends NPCEntity {

	public static final String TRAINER_KEY = "trainer";
	public static final String BEHAVIOR_TYPE_KEY = "behaviorType";
	
	private NPCTrainer trainer;
	private TrainerBehaviorType behaviorType = TrainerBehaviorType.WALKING_TRAINER;
	
	public NPCTrainerEntity(NPCTrainer trainer, Location place, EntityHandler handler) {
		super(place, handler, TrainerEntityType.getInstance());
		this.trainer = trainer;
		setDespawnTicks(10000);
//		setAi(RandomTrainerAI.INSTANCE);
//		spawn();
	}
	
	@Override
	protected void spawn() {
		super.spawn();
//		if (getAi() == null) {
//			setAi(StationaryTrainerAI.INSTANCE);
//		}
		switch (behaviorType) {
		case STATIONARY_TRAINER:
			addEntityTask(new ReturnToSpawnTask());
			break;
		case WALKING_TRAINER:
			addEntityTask(new RandomTask());
			break;
		}
	}
	
	@Override
	public void loop() {
		super.loop();
		if (JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().getTrainersBattle(trainer) == null  && isSave()) {
			for (Pokemon poke : trainer.getParty()) {
				poke.heal();
			}
		}
	}
	
	@Override
	protected void onInteract(PlayerInteractEntityEvent event) {
		super.onInteract(event);
		Trainer player = JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getPlayerTrainerHandler().getTrainer(event.getPlayer());
		BattleHandler battleHandler = JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler();
		if (player.getEliteFourDefeatLevel() == Math.max(0, trainer.getEliteFourLevel() - 1) && battleHandler.getTrainersBattle(player) == null && player.getNextUsablePokemon() != null && battleHandler.getTrainersBattle(getTrainer()) == null && getTrainer().getNextUsablePokemon() != null) {
			//Look at player
			NMSEntityUtil.rotateVehicle(getEntity(), event.getPlayer().getLocation().toVector().subtract(getEntity().getLocation().toVector()));
			trainer.prepareForBattle(null);
			//Start battle
			Battle battle = new Battle(battleHandler, player.getNextUsablePokemon(), trainer.getNextUsablePokemon());
			//Spawn Pokemon
			getHandler().addEntity(new PokemonEntity(event.getPlayer().getLocation(), player.getNextUsablePokemon(), getHandler()));
			PokemonEntity pokeEntity = new PokemonEntity(getEntity().getLocation().add(getEntity().getLocation().getDirection()), getTrainer().getNextUsablePokemon(), getHandler());
			getHandler().addEntity(pokeEntity);
//			trainer.getEntity().setVelocity(new Vector(0, 0, 1));
			battle.start();
		}
		event.setCancelled(true);
	}

//	@Override
//	protected ArmorStand createEntity(Location place) {
//		ArmorStand stand = (ArmorStand) place.getWorld().spawnEntity(place, EntityType.ARMOR_STAND);
//		stand.setVisible(false);
//		stand.setHelmet(TrainerUtil.itemFromTrainer(trainer));
//		stand.setCustomName(getTrainerName());
//		stand.setCustomNameVisible(true);
//		stand.setCanPickupItems(false);
//		place.getChunk().load();
//		return stand;
//	}

	public NPCTrainer getTrainer() {
		return trainer;
	}
	
	@Override
	protected boolean canMove() {
		return JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().getTrainersBattle(trainer) == null;
	}
	
	@Override
	protected boolean canDespawn() {
		return JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().getTrainersBattle(trainer) == null && !isSave();
	}

	@Override
	protected double getSwimSpeed() {
		return 0.2;
	}
	
	@Override
	protected double getSprintSpeed() {
		return 0.4;
	}
	
	@Override
	public void legacySaveData(Element element, Document document) {
		super.legacySaveData(element, document);
		Element trainerElement = document.createElement(PlayerTrainerHandler.TRAINER_ELEMENT);
		PlayerTrainerHandler.saveTrainer(getTrainer(), document, trainerElement, PokemonHandler.getInstance());
		element.appendChild(trainerElement);
	}
	
	@Override
	public void legacyParseData(Element element) {
		super.legacyParseData(element);
//		System.out.println(element);
		NodeList trainer = element.getElementsByTagName(PlayerTrainerHandler.TRAINER_ELEMENT);
		if (trainer.getLength() > 0 && trainer.item(0).getNodeType() == Node.ELEMENT_NODE) {
			PlayerTrainerHandler.loadTrainer(getTrainer(), element, PokemonHandler.getInstance(), MoveHandler.getInstance(), TrainerRankHandler.getInstance());
		}
		
		//Legacy AI to Behavior type
		Node ai = element.getElementsByTagName(AI_TAG).item(0);
		if (ai != null && ai instanceof Element) {
			Node key = ((Element) ai).getElementsByTagName(KEY_TAG).item(0);
			if ("stationary_trainer".equals(key.getTextContent())) {
				behaviorType = TrainerBehaviorType.STATIONARY_TRAINER;
			}
		}
	}

	@Override
	public ItemStack getItem() {
		return TrainerUtil.itemFromTrainer(getTrainer());
	}

	@Override
	public String getName() {
		return trainer.getRank().getName() + " " + trainer.getName() + " Lvl. " + trainer.getLevel();
	}

	@Override
	protected void loadData(EntityMapData data) {
		if (data.get(TRAINER_KEY) instanceof NPCTrainer) {
			trainer = (NPCTrainer) data.get(TRAINER_KEY);
		}
		if (data.get(BEHAVIOR_TYPE_KEY) != null) {
			try {
				behaviorType = TrainerBehaviorType.valueOf(data.get(BEHAVIOR_TYPE_KEY) + "");
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		
		//Legacy AI to Behavior type
		if (data.get(AI_TAG) instanceof NamespacedEntry) {
			String key = ((NamespacedEntry) data.get(AI_TAG)).getIdentifier();
			if (key.equals("stationary_trainer")) {
				behaviorType = TrainerBehaviorType.STATIONARY_TRAINER;
			}
		}
	}

	@Override
	protected EntityMapData saveData() {
		HashMap<String, Object> map = new HashMap<>();
		map.put(TRAINER_KEY, trainer);
		map.put(BEHAVIOR_TYPE_KEY, behaviorType + "");
		
		return new EntityMapData(map);
	}
	
	public static NPCTrainerEntity deserialize (Map<String, Object> map) {
		NPCTrainerEntity entity = new NPCTrainerEntity(null, null, null);
		entity.load(map);
//		System.out.println("Loading NPCTrainer at " + entity.getSavePoint());
		return entity;
	}

	public TrainerBehaviorType getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(TrainerBehaviorType behaviorType) {
		this.behaviorType = behaviorType;
	}

	@Override
	public Class<? extends JavaPlugin> getPlugin() {
		return PokeminePlugin.class;
	}
}
