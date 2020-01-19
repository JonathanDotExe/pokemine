package at.jojokobi.pokemine.generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import at.jojokobi.mcutil.generation.FurnitureGenUtil;
import at.jojokobi.mcutil.generation.TerrainGenUtil;
import at.jojokobi.mcutil.generation.population.Structure;
import at.jojokobi.mcutil.generation.population.StructureInstance;
import at.jojokobi.mcutil.music.Music;
import at.jojokobi.mcutil.music.MusicPlayer;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.pokemon.PokemonType;
import at.jojokobi.pokemine.trainer.NPCTrainer;
import at.jojokobi.pokemine.trainer.SimpleTeamGenerator;
import at.jojokobi.pokemine.trainer.Trainer;
import at.jojokobi.pokemine.trainer.TrainerRank;
import at.jojokobi.pokemine.trainer.TrainerRankHandler;
import at.jojokobi.pokemine.trainer.entity.NPCTrainerEntity;
import at.jojokobi.pokemine.trainer.entity.TrainerBehaviorType;

public class PokemonGym extends Structure implements Listener{

	
	public static final String POKEMON_GYM_THEME_NAME = "gym.ambient";
	public static final Music POKEMON_GYM_THEME= new Music(POKEMON_GYM_THEME_NAME, 90);
	
	private static final int FIXED_GYM_RADIUS = 32;
	
	private PokeminePlugin plugin;
	
	public PokemonGym(PokeminePlugin plugin) {
		super(16, 16, 10, 350, 1);
		this.plugin = plugin;
		setxModifier(9848);
		setzModifier(8456);
	}

	@Override
	public List<StructureInstance<? extends Structure>> generate(Location loc, long seed) {
		loc.setY(calculatePlacementY(getWidth(), getLength(), loc));
		Location place = loc.clone();
		//Type
		Random random = new Random(generateValueBeasedSeed(loc, seed));
		FixedGym gym = getFixedGymAt(seed, loc.getChunk().getX(), loc.getChunk().getZ());
		PokemonType type = gym != null ? gym.getPos().getType() : getBiomeType(loc.getBlock().getBiome(), seed, loc);
		//Structure
		for (int x = 0; x < getWidth(); x++) {
			place.setX(loc.getX() + x);
			for (int z = 0; z < getLength(); z++) {
				place.setZ(loc.getZ() + z);
				for (int y = 0; y < getHeight(); y++) {
					place.setY(loc.getY() + y);
					Material material = Material.AIR;
					//Roof
					if (y >= getHeight() - 3) {
						if (y == getHeight() - 1 && (x == 0 || x == getWidth() - 1 || z == 0 || z == getLength() - 1)) {
							material = Material.AIR;
						}
						else if (y == getHeight() - 3 && ((x == 1 && z == 1) || (x == getWidth() - 2 && z == 1) || (x == 1 && z == getLength()-2) || (x == getWidth() - 2 && z == getLength()-2))) {
							material = Material.SEA_LANTERN;
						}
						else {
							material = gym != null ? Material.YELLOW_CONCRETE : Material.YELLOW_GLAZED_TERRACOTTA;
						}
					}
					//Windows
					else if (y >= 2 && y <= getHeight() - 2 && z >= 2 && z <= getLength() - 3 && (x == 0 || x == getWidth() - 1)) {
						material = Material.GLASS;
					}
					//Floor
					else if ((z > 0 && z < getLength() -1) && (x > 3 && x < getWidth() - 4) && y == 0) {
						material = getFloor(type);
					}
					//Podium
					else if ((z > getLength() - 5 && z < getLength() -1) && (x > 3 && x < getWidth() - 4) && y == 1) {
						material = Material.SMOOTH_STONE;
					}
					//Wall
					else if (x == 0 || x == getWidth() - 1 || y == 0 || z == 0 || z == getLength() - 1) {
						material = Material.WHITE_CONCRETE;
					}
					place.getBlock().setType(material, false);
				}
			}
		}
		//Furniture
		//Door
		place.setX(loc.getX() + (getWidth()/2)-1);
		place.setZ(loc.getZ());
		place.setY(loc.getY() + 1);
		FurnitureGenUtil.generateDoor(place, Material.OAK_DOOR, BlockFace.SOUTH, false, false);
		place.add(1, 0, 0);
		FurnitureGenUtil.generateDoor(place, Material.OAK_DOOR, BlockFace.SOUTH, false, true);
		//Trainers
		//Gym Leader
		List<TrainerRank> ranks = TrainerRankHandler.getInstance().getRanksWithBadge(type);
		byte level = gym != null ? (byte) gym.getLevel() : (byte) (random.nextInt(60) + 10);
		if (ranks.size() > 0) {
			place.setY(loc.getY() + 2);
			place.setZ(loc.getZ() + getLength() - 2);
			place.setPitch(270);
			NPCTrainer trainer = new NPCTrainer(ranks.get(random.nextInt(ranks.size())), new SimpleTeamGenerator(level,  5, random.nextLong()));
			NPCTrainerEntity entity = new NPCTrainerEntity(trainer, place, plugin.getEntityHandler());
			entity.setSave(true);
			entity.setDespawnTicks(-1);
			entity.setBehaviorType(TrainerBehaviorType.STATIONARY_TRAINER);
			plugin.getEntityHandler().addEntity(entity);
			//Sign
			place.setX(loc.getX() + (getWidth()/2)-2);
			place.setZ(loc.getZ() - 1);
			place.getBlock().setType(Material.OAK_WALL_SIGN);
			Sign sign = (Sign) place.getBlock().getState();
			sign.setLine(0, "----------------");
			sign.setLine(1, trainer.getName() + "'s");
			sign.setLine(2, type + " Gym");
			sign.setLine(3, "----------------");
			sign.update();
		}
		//Regular Trainers
		List<TrainerRank> trainerRanks = TrainerRankHandler.getInstance().getRanksWithType(type, true);
		if (trainerRanks.size() > 0) {
			place.setY(loc.getY() + 1);
			Location[] places = {new Location(loc.getWorld(), loc.getX() + 3, loc.getY() + 1, loc.getZ() + 2, 0, 270),
								new Location(loc.getWorld(), loc.getX() + 3, loc.getY() + 1, loc.getZ() + getLength() - 2, 0, 270),
								new Location(loc.getWorld(), loc.getX() + getWidth() - 3, loc.getY() + 1, loc.getZ() + 2, 0, 270),
								new Location(loc.getWorld(), loc.getX() + getWidth() - 3, loc.getY() + 1, loc.getZ() + getLength() - 2, 0, 270)};
			for (int i = 0; i < places.length; i++) {
				NPCTrainer trainer = new NPCTrainer(trainerRanks.get(random.nextInt(trainerRanks.size())), new SimpleTeamGenerator((byte) (level - 3),  random.nextInt(Trainer.PARTY_SIZE) + 1, random.nextLong()));
				NPCTrainerEntity entity = new NPCTrainerEntity(trainer, places[i], plugin.getEntityHandler());
				entity.setSave(true);
				entity.setBehaviorType(TrainerBehaviorType.STATIONARY_TRAINER);
				entity.setDespawnTicks(-1);
				plugin.getEntityHandler().addEntity(entity);
			}
		}
		return Arrays.asList(new StructureInstance<PokemonGym>(this, loc, getWidth(), getHeight(), getLength()));
	}
	
	@Override
	public boolean canGenerate(Chunk chunk, long seed) {
		return super.canGenerate(chunk, seed) || getFixedGymAt(seed, chunk.getX(), chunk.getZ()) != null;
	}
	
/*	@Override
	public boolean canGenerate(Chunk chunk, long seed) {
		return super.canGenerate(chunk, seed) && getBiomeType(chunk.getBlock(0, 0, 0).getBiome(), seed) != null;
	}*/
	
	public static PokemonType getBiomeType (Biome biome, long seed, Location place) {
		PokemonType type = PokemonType.values()[new Random(TerrainGenUtil.generateValueBasedSeed(seed, place.getBlockX(), place.getBlockY(), place.getBlockZ(), 1)).nextInt(PokemonType.values().length)];
		/*		switch (biome) {
		case SMALLER_EXTREME_HILLS:
		case JUNGLE_HILLS:
		case MUTATED_EXTREME_HILLS_WITH_TREES:
		case MESA_ROCK:
		case MUTATED_MESA_ROCK:
			type = PokemonType.FAIRY;
			break;
			
		case MUSHROOM_ISLAND:
		case MUSHROOM_ISLAND_SHORE:
		case MUTATED_BIRCH_FOREST:
		case MUTATED_BIRCH_FOREST_HILLS:
		case BIRCH_FOREST:
		case BIRCH_FOREST_HILLS:
			type = PokemonType.BUG;
			break;
		
		case JUNGLE:
		case JUNGLE_EDGE:
		case MUTATED_JUNGLE:
		case MUTATED_JUNGLE_EDGE:
			type = PokemonType.GRASS;
		
		case PLAINS:
			type = PokemonType.FLYING;
			break;
			
		case REDWOOD_TAIGA:
		case MUTATED_REDWOOD_TAIGA:
			type = PokemonType.DARK;
			break;
			
		case OCEAN:
		case RIVER:
		case DEEP_OCEAN:
		case BEACHES:
			type = PokemonType.WATER;
			break;
			
		case ROOFED_FOREST:
		case MUTATED_ROOFED_FOREST:
			type = PokemonType.GHOST;
			break;
			
		case SAVANNA:
		case SAVANNA_ROCK:
		case MUTATED_SAVANNA:
		case MUTATED_SAVANNA_ROCK:
		case HELL:
			type = PokemonType.FIRE;
			break;
		
		case SKY:
		case MUTATED_PLAINS:
		case REDWOOD_TAIGA_HILLS:
		case MUTATED_REDWOOD_TAIGA_HILLS:
		case MESA_CLEAR_ROCK:
		case MUTATED_MESA_CLEAR_ROCK:
			type = PokemonType.DRAGON;
			break;
			
		case DESERT:
		case DESERT_HILLS:
		case MUTATED_DESERT:
			type = PokemonType.GROUND;
			break;
			
		case MUTATED_MESA:
		case MESA:
			type = PokemonType.FIGHTING;
			break;
			
		case STONE_BEACH:
		case EXTREME_HILLS:
		case MUTATED_EXTREME_HILLS:
			type = PokemonType.ROCK;
			break;
			
		case TAIGA:
		case MUTATED_TAIGA:
			type = PokemonType.PSYCHIC;
			break;
			
		case SWAMPLAND:
		case MUTATED_SWAMPLAND:
			type = PokemonType.POISON;
			break;
			
		case ICE_FLATS:
		case ICE_MOUNTAINS:
		case FROZEN_OCEAN:
		case FROZEN_RIVER:
		case COLD_BEACH:
			type = PokemonType.ICE;
			break;
		
		case TAIGA_COLD:
		case TAIGA_COLD_HILLS:
		case MUTATED_TAIGA_COLD:
		case MUTATED_ICE_FLATS:
			type = PokemonType.STEEL;
			break;
			
		case FOREST_HILLS:
		case EXTREME_HILLS_WITH_TREES:
		case TAIGA_HILLS:
			type = PokemonType.ELECRTIC;
			break;
			
		case FOREST:
		case MUTATED_FOREST:
		case VOID:
			type = PokemonType.NORMAL;
			break;
			
		default:
			break;
		}*/
		return type;
	}
	
	public List<FixedGymPosition> generateFixedGymPositions (long seed) {
		Random random = new Random(seed);
		List<FixedGymPosition> gyms = new ArrayList<>(18);
		for (PokemonType type : PokemonType.values()) {
			gyms.add(new FixedGymPosition(type, random.nextInt(FIXED_GYM_RADIUS * 2) - FIXED_GYM_RADIUS,  random.nextInt(FIXED_GYM_RADIUS * 2) - FIXED_GYM_RADIUS));
		}
		Collections.sort(gyms);
		return gyms;
	}
	
	
	
	public FixedGym getFixedGymAt (long seed, int x, int z) {
		int index = 0;
		for (FixedGymPosition pos : generateFixedGymPositions(seed)) {
			if (pos.getChunkX() == x && pos.getChunkZ() == z) {
				return new FixedGym(calcLevel(index), pos);
			}
			index++;
		}
		return null;
	}
	
	public int calcLevel (int index) {
		return 1 + index * 3;
	}
	
	public static Material getFloor (PokemonType type) {
		Material floor = Material.WHITE_CONCRETE;
		switch (type) {
		case BUG:
			floor = Material.OAK_LEAVES;
			break;
		case DARK:
			floor = Material.OBSIDIAN;
			break;
		case DRAGON:
			floor = Material.PURPLE_GLAZED_TERRACOTTA;
			break;
		case ELECTRIC:
			floor = Material.GLOWSTONE;
			break;
		case FAIRY:
			floor = Material.PINK_GLAZED_TERRACOTTA;
			break;
		case FIGHTING:
			floor = Material.OAK_PLANKS;
			break;
		case FIRE:
			floor = Material.LAVA;
			break;
		case FLYING:
			floor = Material.GLASS;
			break;
		case GHOST:
			floor = Material.BONE_BLOCK;
			break;
		case GRASS:
			floor = Material.GRASS_BLOCK;
			break;
		case GROUND:
			floor = Material.SAND;
			break;
		case ICE:
			floor = Material.PACKED_ICE;
			break;
		case NORMAL:
			floor = Material.WHITE_WOOL;
			break;
		case POISON:
			floor = Material.MOSSY_COBBLESTONE;
			break;
		case PSYCHIC:
			floor = Material.PURPUR_BLOCK;
			break;
		case ROCK:
			floor = Material.COBBLESTONE;
			break;
		case STEEL:
			floor = Material.IRON_BARS;
			break;
		case WATER:
			floor = Material.WATER;
			break;
		default:
			break;
		}
		return floor;
	}
	
	@EventHandler
	public void onPlayerMove (PlayerMoveEvent event) {
		StructureInstance<? extends Structure> structure = plugin.getGenerationHandler().getInstanceAt(event.getTo());
		if (structure != null && structure.getStructure() == this && plugin.getMusicHandler().getMusic(event.getPlayer()) == null) {
			plugin.getMusicHandler().playMusic(POKEMON_GYM_THEME, event.getPlayer(), true);
		}
		else if ((structure == null || !(structure.getStructure() instanceof PokemonGym))) {
			MusicPlayer music = plugin.getMusicHandler().getMusic(event.getPlayer());
			if (music != null && music.getMusic() == POKEMON_GYM_THEME) {
				music.stop();
			}
		}
	}

	@Override
	public String getIdentifier() {
		return "pokemon_gym";
	}

	@Override
	public StructureInstance<? extends Structure> getStandardInstance(Location location) {
		return new StructureInstance<PokemonGym>(this, location, getWidth(), getHeight(),getLength());
	}
	
	public static class FixedGym {
		private int level;
		private FixedGymPosition pos;
		public FixedGym(int level, FixedGymPosition pos) {
			super();
			this.level = level;
			this.pos = pos;
		}
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
		public FixedGymPosition getPos() {
			return pos;
		}
		public void setPos(FixedGymPosition pos) {
			this.pos = pos;
		}

	}

	public static class FixedGymPosition implements Comparable<FixedGymPosition>{
		private PokemonType type;
		private int chunkX;
		private int chunkZ;
		public FixedGymPosition(PokemonType type, int chunkX, int chunkZ) {
			super();
			this.type = type;
			this.chunkX = chunkX;
			this.chunkZ = chunkZ;
		}
		public PokemonType getType() {
			return type;
		}
		public void setType(PokemonType type) {
			this.type = type;
		}
		public int getChunkX() {
			return chunkX;
		}
		public void setChunkX(int chunkX) {
			this.chunkX = chunkX;
		}
		public int getChunkZ() {
			return chunkZ;
		}
		public void setChunkZ(int chunkZ) {
			this.chunkZ = chunkZ;
		}
		
		public double getSpawnDistance () {
			return Math.sqrt(chunkX*chunkX + chunkZ*chunkZ);
		}
		@Override
		public int compareTo(FixedGymPosition o) {
			return Double.compare(getSpawnDistance(), o.getSpawnDistance());
		}
		
		public Chunk getChunk (World world) {
			return world.getChunkAt(chunkX, chunkZ);
		}
		
	}

}