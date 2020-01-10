package at.jojokobi.pokemine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.plugin.java.JavaPlugin;
import org.xml.sax.SAXException;

import at.jojokobi.mcutil.ChatInputHandler;
import at.jojokobi.mcutil.FileProvider;
import at.jojokobi.mcutil.JojokobiUtilPlugin;
import at.jojokobi.mcutil.SerializationUtil;
import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.mcutil.generation.GenerationHandler;
import at.jojokobi.mcutil.gui.InventoryGUIHandler;
import at.jojokobi.mcutil.music.MusicHandler;
import at.jojokobi.pokemine.battle.BattleHandler;
import at.jojokobi.pokemine.battle.animation.AnimationHandler;
import at.jojokobi.pokemine.commands.FixedGymCommand;
import at.jojokobi.pokemine.commands.PokemineCommand;
import at.jojokobi.pokemine.generation.EliteFourGym;
import at.jojokobi.pokemine.generation.LegendaryTower;
import at.jojokobi.pokemine.generation.PokemonCenter;
import at.jojokobi.pokemine.generation.PokemonGym;
import at.jojokobi.pokemine.items.Healer;
import at.jojokobi.pokemine.items.HyperPotion;
import at.jojokobi.pokemine.items.LeafStone;
import at.jojokobi.pokemine.items.PcItem;
import at.jojokobi.pokemine.items.Pokeball;
import at.jojokobi.pokemine.items.Potion;
import at.jojokobi.pokemine.items.Revive;
import at.jojokobi.pokemine.items.SuperPotion;
import at.jojokobi.pokemine.items.TM;
import at.jojokobi.pokemine.items.ThunderStone;
import at.jojokobi.pokemine.items.TreasureBall;
import at.jojokobi.pokemine.items.UltraBall;
import at.jojokobi.pokemine.items.WritableTM;
import at.jojokobi.pokemine.items.AbstractPokeball;
import at.jojokobi.pokemine.items.ExpShare;
import at.jojokobi.pokemine.items.FullHeal;
import at.jojokobi.pokemine.items.GreatBall;
import at.jojokobi.pokemine.moves.Move;
import at.jojokobi.pokemine.moves.MoveHandler;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.MoveLearnCondition;
import at.jojokobi.pokemine.moves.MoveYAMLLoader;
import at.jojokobi.pokemine.moves.procedures.AddBattleEffectProcedure;
import at.jojokobi.pokemine.moves.procedures.AddPrimaryStatusProcedure;
import at.jojokobi.pokemine.moves.procedures.AddSecondaryStatusProcedure;
import at.jojokobi.pokemine.moves.procedures.AddValueProcedure;
import at.jojokobi.pokemine.moves.procedures.AnimationProcedure;
import at.jojokobi.pokemine.moves.procedures.ChanceProcedure;
import at.jojokobi.pokemine.moves.procedures.ConditionalProcedure;
import at.jojokobi.pokemine.moves.procedures.CounterProcedure;
import at.jojokobi.pokemine.moves.procedures.DamageHPProcedure;
import at.jojokobi.pokemine.moves.procedures.DamagePercentProcedure;
import at.jojokobi.pokemine.moves.procedures.DamageProcedure;
import at.jojokobi.pokemine.moves.procedures.DamageWholePercentProcedure;
import at.jojokobi.pokemine.moves.procedures.DoNothingProcedure;
import at.jojokobi.pokemine.moves.procedures.FailProcedure;
import at.jojokobi.pokemine.moves.procedures.FuryAttackProcedure;
import at.jojokobi.pokemine.moves.procedures.HealHPPercentProcedure;
import at.jojokobi.pokemine.moves.procedures.HealWholePercentProcedure;
import at.jojokobi.pokemine.moves.procedures.LegacyMoveScriptProcedure;
import at.jojokobi.pokemine.moves.procedures.ListProcedure;
import at.jojokobi.pokemine.moves.procedures.LoadBeforeProcedure;
import at.jojokobi.pokemine.moves.procedures.MagnitudeProcedure;
import at.jojokobi.pokemine.moves.procedures.PriorityProcedure;
import at.jojokobi.pokemine.moves.procedures.RemovePrimaryStatusProcedure;
import at.jojokobi.pokemine.moves.procedures.RestAfterProcedure;
import at.jojokobi.pokemine.moves.procedures.SetValueProcedure;
import at.jojokobi.pokemine.moves.procedures.StealItemProcedure;
import at.jojokobi.pokemine.moves.procedures.StoredPowerProcedure;
import at.jojokobi.pokemine.moves.procedures.SwapStatusValueProcedure;
import at.jojokobi.pokemine.moves.procedures.SwitchProcedure;
import at.jojokobi.pokemine.moves.procedures.ThrashProcedure;
import at.jojokobi.pokemine.moves.procedures.conditions.DamageTakenCondition;
import at.jojokobi.pokemine.moves.procedures.conditions.HasPrimaryStatusCondition;
import at.jojokobi.pokemine.moves.procedures.conditions.HasTypeCondition;
import at.jojokobi.pokemine.moves.procedures.conditions.NumberSmallerThanCondition;
import at.jojokobi.pokemine.moves.procedures.conditions.TrueCondition;
import at.jojokobi.pokemine.moves.procedures.conditions.WillDamageCondition;
import at.jojokobi.pokemine.moves.procedures.conditions.WillSwitchCondition;
import at.jojokobi.pokemine.moves.procedures.suppliers.ConditionalNumberSupplier;
import at.jojokobi.pokemine.moves.procedures.suppliers.FixedNumberSupplier;
import at.jojokobi.pokemine.moves.procedures.suppliers.FlialDamageSupplier;
import at.jojokobi.pokemine.moves.procedures.suppliers.HealthPercentSupplier;
import at.jojokobi.pokemine.moves.procedures.suppliers.HealthSupplier;
import at.jojokobi.pokemine.moves.procedures.suppliers.LevelSupplier;
import at.jojokobi.pokemine.moves.procedures.suppliers.MovePowerSupplier;
import at.jojokobi.pokemine.moves.procedures.suppliers.MultiplierSupplier;
import at.jojokobi.pokemine.moves.procedures.suppliers.RandomMultiplierSupplier;
import at.jojokobi.pokemine.pokemon.PlacedPokemon;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.PokemonLoader;
import at.jojokobi.pokemine.pokemon.PokemonSpecies;
import at.jojokobi.pokemine.pokemon.PokemonValueSet;
import at.jojokobi.pokemine.pokemon.PokemonYAMLLoader;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntityType;
import at.jojokobi.pokemine.pokemon.evolution.CustomItemCondition;
import at.jojokobi.pokemine.pokemon.evolution.EvolutionCause;
import at.jojokobi.pokemine.pokemon.evolution.FriendshipCondition;
import at.jojokobi.pokemine.pokemon.evolution.HeldCustomItemCondition;
import at.jojokobi.pokemine.pokemon.evolution.HeldItemCondition;
import at.jojokobi.pokemine.pokemon.evolution.LevelCondition;
import at.jojokobi.pokemine.pokemon.evolution.TradeCondition;
import at.jojokobi.pokemine.pokemon.status.Burn;
import at.jojokobi.pokemine.pokemon.status.Freeze;
import at.jojokobi.pokemine.pokemon.status.HeavyPoison;
import at.jojokobi.pokemine.pokemon.status.Paralysis;
import at.jojokobi.pokemine.pokemon.status.Poison;
import at.jojokobi.pokemine.pokemon.status.Sleep;
import at.jojokobi.pokemine.spawning.BiomeSpawnCondition;
import at.jojokobi.pokemine.spawning.DaySpawnCondition;
import at.jojokobi.pokemine.spawning.PokemonSpawner;
import at.jojokobi.pokemine.spawning.RainSpawnCondition;
import at.jojokobi.pokemine.spawning.SpawnChance;
import at.jojokobi.pokemine.trainer.NPCTrainer;
import at.jojokobi.pokemine.trainer.PlacedPokemonHandler;
import at.jojokobi.pokemine.trainer.PlayerTrainer;
import at.jojokobi.pokemine.trainer.PlayerTrainerHandler;
import at.jojokobi.pokemine.trainer.TrainerRank;
import at.jojokobi.pokemine.trainer.TrainerRankHandler;
import at.jojokobi.pokemine.trainer.TrainerRankLoader;
import at.jojokobi.pokemine.trainer.entity.NPCTrainerEntity;
import at.jojokobi.pokemine.trainer.entity.TrainerEntityType;

public class PokeminePlugin extends JavaPlugin {

	public static final String POKEMON_FOLDER = "/pokemon";
	public static final String URL = "https://jojokobi.lima-city.de/pokemine/";
	public static final String MOVE_URL = URL + "moves/";
	public static final String POKEMON_URL = URL + "pokemon_species/";
	public static final String MOVE_FOLDER = "/moves";
	public static final String TRAINER_FOLDER = "/trainer";
	public static final String OLD_RESSOURCE_FOLDER = "/ressources/xml";
	public static final String RESSOURCE_FOLDER = "ressources/yaml";

	public static final String POKEMINE_NAMESPACE = "pokemine";
	
	//Configuration
	private static int battleExperienceMultiplier = 1;
	
	private JojokobiUtilPlugin util;

//	private MoveHandler moveHandler = new MoveHandler();
//	private PokemonHandler pokemonHandler = new PokemonHandler(moveHandler);
//	private TrainerRankHandler trainerRankHandler = new TrainerRankHandler(PokemonHandler.getInstance());
//	private PokemonEntityHandler entityHandler;
	private PlayerTrainerHandler trainerHandler;
//	private NPCTrainerHandler npcTrainerHandler;
//	private InventoryGUIHandler guiHandler;
	private BattleHandler battleHandler;
	private PokemonSpawner spawner;
//	private GenerationHandler genHandler;
//	private MusicHandler musicHandler;
	private AnimationHandler animationHandler;
	private ChatInputHandler inputHandler;
	// Items
	private Pokeball pokeball;
	private GreatBall greatball;
	private UltraBall ultraball;
	private Healer healer;
	private PcItem pc;
	private TM tm;
	private WritableTM writableTM;
	private TreasureBall treasureball;
	private ThunderStone thunderstone;
	private Revive revive;
	private Potion potion;
	private SuperPotion superPotion;
	private HyperPotion hyperPotion;
	private FullHeal fullHeal;
	private ExpShare expShare;
	private LeafStone leafStone;
	
	public static void registerSerializables () {
//		ConfigurationSerialization.registerClass(MoveInstance.class, "PokemineMoveInstance");
//		ConfigurationSerialization.registerClass(Pokemon.class, "PokeminePokemon");
//		ConfigurationSerialization.registerClass(PokemonValueSet.class, "PokeminePokemonValueSet");
//		ConfigurationSerialization.registerClass(Burn.class, "PokemineBurn");
//		ConfigurationSerialization.registerClass(Freeze.class, "PokemineFreeze");
//		ConfigurationSerialization.registerClass(HeavyPoison.class, "PokemineHeavyPoison");
//		ConfigurationSerialization.registerClass(Paralysis.class, "PokemineParalysis");
//		ConfigurationSerialization.registerClass(Poison.class, "PokeminePoison");
//		ConfigurationSerialization.registerClass(Sleep.class, "PokemineSleep");
//		ConfigurationSerialization.registerClass(NPCTrainer.class, "PokemineNPCTrainer");
//		
//		ConfigurationSerialization.registerClass(PokemonEntity.class, "PokeminePokemonEntity");
//		ConfigurationSerialization.registerClass(NPCTrainerEntity.class, "PokemineNPCTrainerEntity");
//		
//		ConfigurationSerialization.registerClass(Move.class, "PokemineMove");
//		ConfigurationSerialization.registerClass(MoveLearnCondition.class, "PokemineMoveLearnCondition");
//		ConfigurationSerialization.registerClass(AddPrimaryStatusProcedure.class, "PokemineAddPrimaryStatusProcedure");
//		ConfigurationSerialization.registerClass(AddSecondaryStatusProcedure.class, "PokemineAddSecondaryStatusProcedure");
//		ConfigurationSerialization.registerClass(AddValueProcedure.class, "PokemineAddValueProcedure");
		
		SerializationUtil.registerSerializables("Pokemine", getConfigurationSerializables());
		
	}
	
	public static List<Class<? extends ConfigurationSerializable>> getConfigurationSerializables () {
		return Arrays.asList(
				MoveInstance.class,
				Pokemon.class,
				PokemonValueSet.class,
				Burn.class,
				Freeze.class,
				HeavyPoison.class,
				Paralysis.class,
				Poison.class,
				Sleep.class,
				NPCTrainer.class,
				PlayerTrainer.class,
				PlacedPokemon.class,
				
				PokemonEntity.class,
				NPCTrainerEntity.class,
				
				Move.class,
				MoveLearnCondition.class,
				
				AddBattleEffectProcedure.class,
				AddPrimaryStatusProcedure.class,
				AddSecondaryStatusProcedure.class,
				AddValueProcedure.class,
				AnimationProcedure.class,
				ChanceProcedure.class,
				ConditionalProcedure.class,
				CounterProcedure.class,
				DamageHPProcedure.class,
				DamagePercentProcedure.class,
				DamageProcedure.class,
				DamageWholePercentProcedure.class,
				DoNothingProcedure.class,
				FailProcedure.class,
				FuryAttackProcedure.class,
				HealHPPercentProcedure.class,
				HealWholePercentProcedure.class,
				LegacyMoveScriptProcedure.class,
				ListProcedure.class,
				LoadBeforeProcedure.class,
				MagnitudeProcedure.class,
				PriorityProcedure.class,
				RemovePrimaryStatusProcedure.class,
				RestAfterProcedure.class,
				SetValueProcedure.class,
				StealItemProcedure.class,
				StoredPowerProcedure.class,
				SwapStatusValueProcedure.class,
				SwitchProcedure.class,
				ThrashProcedure.class,
				
				DamageTakenCondition.class,
				HasPrimaryStatusCondition.class,
				HasTypeCondition.class,
				NumberSmallerThanCondition.class,
				TrueCondition.class,
				WillDamageCondition.class,
				WillSwitchCondition.class,
				
				ConditionalNumberSupplier.class,
				FixedNumberSupplier.class,
				FlialDamageSupplier.class,
				HealthPercentSupplier.class,
				HealthSupplier.class,
				LevelSupplier.class,
				MovePowerSupplier.class,
				MultiplierSupplier.class,
				RandomMultiplierSupplier.class,
				
				PokemonSpecies.class,
				EvolutionCause.class,
				CustomItemCondition.class,
				FriendshipCondition.class,
				HeldCustomItemCondition.class,
				HeldItemCondition.class,
				LevelCondition.class,
				TradeCondition.class,
				
				SpawnChance.class,
				BiomeSpawnCondition.class,
				DaySpawnCondition.class,
				RainSpawnCondition.class
				);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		
		registerSerializables();
	}
	
	@Override
	public void onEnable() {
		util = JavaPlugin.getPlugin(JojokobiUtilPlugin.class);
		
		saveDefaultConfig();
		battleExperienceMultiplier = getConfig().getInt("battleExperienceMultiplier", 1);
		if (!getConfig().contains("battleExperienceMultiplier")) {
			getConfig().set("battleExperienceMultiplier", 1);
		}
		
		// Moves
		// From inside Jar
		System.out.println(RESSOURCE_FOLDER + MOVE_FOLDER);
		try {
			MoveHandler.getInstance().loadItems(FileProvider.fromJar(MoveYAMLLoader.getInstance(), getFile(), RESSOURCE_FOLDER + MOVE_FOLDER));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// From data Folder
		File moveFolder = new File(getDataFolder(), MOVE_FOLDER);
		moveFolder.mkdirs();
//		MoveHandler.getInstance().loadAll(moveFolder);

		// Pokemon
		// From inside Jar
		System.out.println(RESSOURCE_FOLDER + POKEMON_FOLDER);
		try {
			PokemonHandler.getInstance().loadItems(FileProvider.fromJar(PokemonYAMLLoader.getInstance(), getFile(), RESSOURCE_FOLDER + POKEMON_FOLDER));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// From data Folder
		File pokemonFolder = new File(getDataFolder(), POKEMON_FOLDER);
		pokemonFolder.mkdirs();
		try {
			PokemonHandler.getInstance().loadItems(FileProvider.fromFolder(PokemonLoader.getInstance(), pokemonFolder));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Trainer Ranks
		// From inside Jar
		try {
			TrainerRankHandler.getInstance().loadItems(FileProvider.fromFolder(TrainerRankLoader.getInstance(), OLD_RESSOURCE_FOLDER + TRAINER_FOLDER));
		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
		// From data Folder
		File trainerFolder = new File(getDataFolder(), TRAINER_FOLDER);
		trainerFolder.mkdirs();
		try {
			TrainerRankHandler.getInstance().loadItems(FileProvider.fromFolder(TrainerRankLoader.getInstance(), trainerFolder));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//AIs
//		AIHandler.addAIController(WildPokemonAI.INSTANCE);
//		AIHandler.addAIController(TrainerPokemonAI.INSTANCE);
//		AIHandler.addAIController(AggressiveStationaryAI.INSTANCE);
//		AIHandler.addAIController(RandomTrainerAI.INSTANCE);
//		AIHandler.addAIController(StationaryTrainerAI.INSTANCE);
		// Command
		this.getCommand(PokemineCommand.COMMAND_NAME).setExecutor(new PokemineCommand(this));
		// Players
		trainerHandler = new PlayerTrainerHandler(this);
		getServer().getPluginManager().registerEvents(trainerHandler, this);
		// Entities
		EntityHandler entityHandler = util.getEntityHandler();
		entityHandler.getHandler().addItem(PokemonEntityType.getInstance());
		entityHandler.getHandler().addItem(TrainerEntityType.getInstance());
		entityHandler.addLegacySaveFolder(new EntityHandler.LegacySaveFolder("pokemine" + File.separator + "pokemon") {
			@Override
			public CustomEntity<?> getStandardInstance(World world) {
				return new PokemonEntity(new Location(world, 0, 255, 0), new Pokemon(new PokemonSpecies(PokeminePlugin.POKEMINE_NAMESPACE, "missingno")), entityHandler);
			}
		});
		entityHandler.addLegacySaveFolder(new EntityHandler.LegacySaveFolder("pokemine" + File.separator + "trainers") {
			@Override
			public CustomEntity<?> getStandardInstance(World world) {
				return new NPCTrainerEntity(new NPCTrainer(new TrainerRank(PokeminePlugin.POKEMINE_NAMESPACE, "trainer"),
						(byte) 50,
						PokemonHandler.getInstance()),
						new Location(world, 0, 255, 0),
						entityHandler);
			}
		});
		
//		entityHandler = new PokemonEntityHandler(this);
//		getServer().getPluginManager().registerEvents(entityHandler, this);
//		// Trainer NPCs
//		npcTrainerHandler = new NPCTrainerHandler(this);
//		getServer().getPluginManager().registerEvents(npcTrainerHandler, this);
		
		// GUI
//		InventoryGUIHandler guiHandler = util.getGuiHandler();
//		getServer().getPluginManager().registerEvents(guiHandler, this);
		// Battles
		battleHandler = new BattleHandler(this);
		getServer().getPluginManager().registerEvents(battleHandler, this);
		// Spawner
		spawner = new PokemonSpawner(this);
		// Music Handler
//		musicHandler = util.getMusicHandler();
		// Animation Handler
		animationHandler = new AnimationHandler(this);
		// Input Handler
		inputHandler = new ChatInputHandler(this);
		getServer().getPluginManager().registerEvents(inputHandler, this);
		
		// Items
		pokeball = new Pokeball(this);
		getServer().getPluginManager().registerEvents(pokeball, this);
		greatball = new GreatBall(this);
		getServer().getPluginManager().registerEvents(greatball, this);
		ultraball = new UltraBall(this);
		getServer().getPluginManager().registerEvents(ultraball, this);
		healer = new Healer(this);
		getServer().getPluginManager().registerEvents(healer, this);
		pc = new PcItem(this);
		getServer().getPluginManager().registerEvents(pc, this);
		tm = new TM(this);
		getServer().getPluginManager().registerEvents(tm, this);
		writableTM = new WritableTM(this);
		getServer().getPluginManager().registerEvents(writableTM, this);
		treasureball = new TreasureBall(this);
		getServer().getPluginManager().registerEvents(treasureball, this);
		thunderstone = new ThunderStone(this);
		getServer().getPluginManager().registerEvents(thunderstone, this);
		revive = new Revive(this);
		getServer().getPluginManager().registerEvents(revive, this);
		potion = new Potion(this);
		getServer().getPluginManager().registerEvents(potion, this);
		superPotion = new SuperPotion(this);
		getServer().getPluginManager().registerEvents(superPotion, this);
		hyperPotion = new HyperPotion(this);
		getServer().getPluginManager().registerEvents(hyperPotion, this);
		fullHeal = new FullHeal(this);
		getServer().getPluginManager().registerEvents(fullHeal, this);
		expShare = new ExpShare(this);
		getServer().getPluginManager().registerEvents(expShare, this);
		leafStone = new LeafStone(this);
		getServer().getPluginManager().registerEvents(leafStone, this);
		
		// Generation
		GenerationHandler genHandler = util.getGenerationHandler();
		genHandler.addLegacySaveFolder("pokemine" + File.separator + "structures");
//		genHandler = new GenerationHandler("pokemine" + File.separator + "structures");
		PokemonCenter pokeCenter = new PokemonCenter(this);
		PokemonGym pokeGym = new PokemonGym(this);
		
		genHandler.addStructure(pokeCenter);
		getServer().getPluginManager().registerEvents(pokeCenter, this);
		
		genHandler.addStructure(pokeGym);
		getServer().getPluginManager().registerEvents(pokeGym, this);
		
		genHandler.addStructure(new LegendaryTower(entityHandler));
		genHandler.addStructure(new EliteFourGym(entityHandler));
		
		this.getCommand(FixedGymCommand.COMMAND_NAME).setExecutor(new FixedGymCommand(genHandler, pokeGym));
		
		getServer().getPluginManager().registerEvents(new PlacedPokemonHandler(trainerHandler, entityHandler), this);
	}

//	@EventHandler
//	public void onPlayerMove (PlayerMoveEvent event) {
//		StructureInstance<? extends Structure> struc = genHandler.getInstanceAt(event.getTo());
//		if (struc != null && struc != genHandler.getInstanceAt(event.getFrom())) {
//			event.getPlayer().sendMessage("You entered " + struc.getStructure().getClass().getSimpleName() + "!");
//		}
//	}

	public PlayerTrainerHandler getPlayerTrainerHandler() {
		return trainerHandler;
	}

	public InventoryGUIHandler getGUIHandler() {
		return util.getGuiHandler();
	}

	public AbstractPokeball getPokeball() {
		return pokeball;
	}

	public TreasureBall getTreasureBall() {
		return treasureball;
	}

	public BattleHandler getBattleHandler() {
		return battleHandler;
	}

	public PokemonSpawner getPokemonSpawner() {
		return spawner;
	}

	public MusicHandler getMusicHandler() {
		return util.getMusicHandler();
	}

	public AnimationHandler getAnimationHandler() {
		return animationHandler;
	}

	public GenerationHandler getGenerationHandler() {
		return util.getGenerationHandler();
	}

	public ChatInputHandler getInputHandler() {
		return inputHandler;
	}

	public EntityHandler getEntityHandler() {
		return util.getEntityHandler();
	}

	public static int getBattleExperienceMultiplier() {
		return battleExperienceMultiplier;
	}

}
