package at.jojokobi.pokemine.generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;

import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.mcutil.generation.FurnitureGenUtil;
import at.jojokobi.mcutil.generation.population.Structure;
import at.jojokobi.mcutil.generation.population.StructureInstance;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.PokemonType;
import at.jojokobi.pokemine.trainer.NPCTrainer;
import at.jojokobi.pokemine.trainer.SimpleTeamGenerator;
import at.jojokobi.pokemine.trainer.Trainer;
import at.jojokobi.pokemine.trainer.TrainerRank;
import at.jojokobi.pokemine.trainer.TrainerRankHandler;
import at.jojokobi.pokemine.trainer.entity.NPCTrainerEntity;
import at.jojokobi.pokemine.trainer.entity.TrainerBehaviorType;

public class EliteFourGym extends Structure{
	
	private EntityHandler entityHandler;

	public EliteFourGym(EntityHandler entityHandler) {
		super(32, 32, 10, 0, 1);
		this.entityHandler = entityHandler;
	}
	
	@Override
	public boolean canGenerate(Chunk chunk, long seed) {
		return chunk.getX() == 0 && chunk.getZ() == 0;
	}
	
	@Override
	public int calculatePlacementY(int width, int length, Location place) {
		return super.calculatePlacementY(width, length, place) - 1;
	}

	@Override
	public List<StructureInstance<? extends Structure>> generate(Location loc, long seed) {
		loc.setY(calculatePlacementY(getWidth(), getLength(), loc));
		Location place = loc.clone();
		byte level = 59;
		//Type
		Random random = new Random(generateValueBeasedSeed(loc, seed));
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
						else if (y == getHeight() - 3 && x > 0 && x < getWidth() - 1 && z > 0 && z < getLength() - 1 && ((x % 4 == 0 && z % 4 == 0) || ((x + 2) % 4 == 0 && (z + 2) % 4 == 0))) {
							material = Material.SEA_LANTERN;
						}
						else {
							material = Material.LIGHT_BLUE_CONCRETE;
						}
					}
					//Windows
					else if (y >= 2 && y <= getHeight() - 2 && z >= 2 && z <= getLength() - 3 && (x == 0 || x == getWidth() - 1)) {
						material = Material.GLASS;
					}
					//Floor
					else if ((z > 0 && z < getLength() -1) && (x > 3 && x < getWidth() - 4) && y == 0) {
						material = Material.PRISMARINE;
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
		
		//Sign
		place.setX(loc.getX() + (getWidth()/2)-2);
		place.setY(loc.getY() + 2);
		place.setZ(loc.getZ() - 1);
		place.getBlock().setType(Material.OAK_WALL_SIGN);
		Sign sign = (Sign) place.getBlock().getState();
		sign.setLine(0, "----------------");
		sign.setLine(1, "Elite Four");
		sign.setLine(2, loc.getWorld().getName());
		sign.setLine(3, "----------------");
		sign.update();
		//Trainers
		//Elite Four
		place.setY(loc.getY() + 1);
		Location[] places = {new Location(loc.getWorld(), loc.getX() + 3, loc.getY() + 1, loc.getZ() + 2, 0, 270),
							new Location(loc.getWorld(), loc.getX() + 3, loc.getY() + 1, loc.getZ() + getLength() - 2, 0, 270),
							new Location(loc.getWorld(), loc.getX() + getWidth() - 3, loc.getY() + 1, loc.getZ() + 2, 0, 270),
							new Location(loc.getWorld(), loc.getX() + getWidth() - 3, loc.getY() + 1, loc.getZ() + getLength() - 2, 0, 270)};
		List<PokemonType> types = new ArrayList<>(Arrays.asList(PokemonType.values()));
		for (int i = 0; i < places.length; i++) {
			int index = random.nextInt(types.size());
			PokemonType type = types.get(index);
			types.remove(index);
			NPCTrainer trainer = new NPCTrainer(TrainerRankHandler.getInstance().getRanksWithBadge(type).get(0), new SimpleTeamGenerator((byte) (level - 3),  5, random.nextLong()));
			trainer.setEliteFourLevel(i + 1);
			//Evolve team
			for (Pokemon pokemon : trainer.getParty()) {
				if (pokemon != null) {
					while (pokemon.evolve(PokemonHandler.getInstance(), null, true)) {}
				}
			}
			NPCTrainerEntity entity = new NPCTrainerEntity(trainer, places[i], entityHandler);
			entity.setSave(true);
			entity.setBehaviorType(TrainerBehaviorType.STATIONARY_TRAINER);
			entity.setDespawnTicks(-1);
			entityHandler.addEntity(entity);
		}
		//Champ
		TrainerRank champRank = TrainerRankHandler.getInstance().getItemList().stream().filter(r -> r.getTypes().length == 0).findAny().orElseGet(() -> null);
		if (champRank != null) {
			place.setX(loc.getX() + (getWidth()/2)-1);
			place.setY(loc.getY() + 2);
			place.setZ(loc.getZ() + getLength() - 2);
			place.setPitch(270);
			NPCTrainer trainer = new NPCTrainer(champRank, new SimpleTeamGenerator(level,  Trainer.PARTY_SIZE, random.nextLong()));
			trainer.setEliteFourLevel(Trainer.CHAMP_LEVEL);
			//Evolve team
			for (Pokemon pokemon : trainer.getParty()) {
				if (pokemon != null) {
					while (pokemon.evolve(PokemonHandler.getInstance(), null, true)) {}
				}
			}
			NPCTrainerEntity entity = new NPCTrainerEntity(trainer, place, entityHandler);
			entity.setSave(true);
			entity.setDespawnTicks(-1);
			entity.setBehaviorType(TrainerBehaviorType.STATIONARY_TRAINER);
			entityHandler.addEntity(entity);
		}

		return Arrays.asList(getStandardInstance(loc));
	}

	@Override
	public String getIdentifier() {
		return "elite_four_gym";
	}

	@Override
	public StructureInstance<? extends Structure> getStandardInstance(Location location) {
		return new StructureInstance<Structure>(this, location, getWidth(), getHeight(), getLength());
	}

}
