package at.jojokobi.pokemine.spawning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import at.jojokobi.mcutil.generation.TerrainGenUtil;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.items.TreasureBall;
import at.jojokobi.pokemine.moves.MoveUtil;
import at.jojokobi.pokemine.pokemon.MathUtil;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.PokemonSpecies;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;
import at.jojokobi.pokemine.trainer.NPCTrainer;
import at.jojokobi.pokemine.trainer.SimpleTeamGenerator;
import at.jojokobi.pokemine.trainer.Trainer;
import at.jojokobi.pokemine.trainer.TrainerRank;
import at.jojokobi.pokemine.trainer.TrainerRankHandler;
import at.jojokobi.pokemine.trainer.TrainerUtil;
import at.jojokobi.pokemine.trainer.entity.NPCTrainerEntity;

public class PokemonSpawner {
	
//	public static final int SPAWN_AREA_SIZE = 17;
//	
//	public static final int MAX_POKEMON_COUNT = 20;
//	public static final int MAX_TRAINER_COUNT = 5;
	
	public static final double SPAWN_CHANCE = 0.001;
	public static final double TRAINER_CHANCE = 0.015;
	public static final double ITEM_CHANCE = 0.0025;
	
//	private Map<EnvironmentSituation, List<PokemonSpecies>> spawns = new HashMap<>();
	
	public PokemonSpawner(PokeminePlugin plugin) {
		//Load spawns
//		for (PokemonSpecies species : PokemonHandler.getInstance().getItemList()) {
//			for (EnvironmentSituation env : species.getSpawns().keySet()) {
//				List<PokemonSpecies> pokes = spawns.get(env) == null ? new ArrayList<>() : spawns.get(env);
//				int chance = species.getSpawnChance(env);
//				for (int i = 0; i < chance; i++) {
//					pokes.add(species);
//				}
//				spawns.put(env, pokes);
//			}
//		}
		//Start spawn loop
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				for (World world : Bukkit.getServer().getWorlds()) {
					for (Chunk chunk : world.getLoadedChunks()) {
//						//Spawn Area Size
//						int x = chunk.getX() - SPAWN_AREA_SIZE/2;
//						int z = chunk.getZ() - SPAWN_AREA_SIZE/2;
						//Location
						Location place = new Location(world, chunk.getX() * TerrainGenUtil.CHUNK_WIDTH, 0, chunk.getZ() * TerrainGenUtil.CHUNK_LENGTH);
						place.setY(world.getHighestBlockYAt(place) + 1);
						//Environment
						EnvironmentSituation env = new EnvironmentSituation(place.getBlock().getBiome(), world.hasStorm(), world.getTime() < 12575);
						List<PokemonSpecies> canSpawn = createSpawnList(env);
						if (Math.random() < SPAWN_CHANCE * canSpawn.size()) {
							Random random = new Random();
							//Pokemon
							Pokemon pokemon = new Pokemon(canSpawn.get(random.nextInt(canSpawn.size())));
							PokemonEntity entity = new PokemonEntity(place, pokemon, plugin.getEntityHandler());
							plugin.getEntityHandler().addEntity(entity);
							pokemon.setLevel(MathUtil.calcPokemonLevel(entity, plugin.getPlayerTrainerHandler()));
							pokemon.setMoves(MoveUtil.generateRandomMoveset(pokemon));
							if (pokemon.isShiny()) {
								Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "A shiny " + pokemon.getName() + " spawned at " + place.getBlockX() + "/" + place.getBlockY() + "/" + place.getBlockZ());
							}
						}
						//Trainer
						if (Math.random() < TRAINER_CHANCE) {
							List<TrainerRank> ranks = new ArrayList<>();
							for (TrainerRank rank : TrainerRankHandler.getInstance().getItemList()) {
								if (rank.isSpawnable()) {
									ranks.add(rank);
								}
							}
							if (ranks.size() > 0) {
								Random random = new Random();
								NPCTrainer trainer = new NPCTrainer(ranks.get(random.nextInt(ranks.size())), new SimpleTeamGenerator((byte) 50, random.nextInt(Trainer.PARTY_SIZE) + 1, random.nextLong()));
								trainer.createTeam();
								NPCTrainerEntity entity = new NPCTrainerEntity(trainer, place, plugin.getEntityHandler());
								plugin.getEntityHandler().addEntity(entity);
								byte level = (byte) (TrainerUtil.averageTrainerLevel(entity.getEntity(), plugin.getPlayerTrainerHandler()) + 3);
								for (Pokemon pokemon : trainer.getParty()) {
									if (pokemon != null) {
										pokemon.setLevel((byte) (level - random.nextInt(6)));
										pokemon.setMoves(MoveUtil.generateRandomMoveset(pokemon));
									}
								}
							}
						}
						//Treasure
						if (Math.random() < ITEM_CHANCE) {
							TreasureBall ball = plugin.getTreasureBall();
							ball.getItemEntity(place, new Vector());
						}
					}
				}
			}
		}, 1000L, 1000L);
	}
	
	private List<PokemonSpecies> createSpawnList (EnvironmentSituation env) {
		List<PokemonSpecies> pokemon = new ArrayList<>();
		for (PokemonSpecies species : PokemonHandler.getInstance().getItemList()) {
			int chance = species.getSpawnChance(env);
			for (int i = 0; i < chance; i++) {
				pokemon.add(species);
			}
		}
		return pokemon;
	}
	
}
