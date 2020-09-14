package at.jojokobi.pokemine.generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.mcutil.generation.FurnitureGenUtil;
import at.jojokobi.mcutil.generation.population.Structure;
import at.jojokobi.mcutil.generation.population.StructureInstance;
import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.mcutil.loot.LootInventory;
import at.jojokobi.mcutil.loot.LootItem;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.items.FullHeal;
import at.jojokobi.pokemine.items.GreatBall;
import at.jojokobi.pokemine.items.Pokeball;
import at.jojokobi.pokemine.items.Potion;
import at.jojokobi.pokemine.items.Revive;
import at.jojokobi.pokemine.items.TM;
import at.jojokobi.pokemine.items.UltraBall;
import at.jojokobi.pokemine.items.WritableTM;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.PokemonSpecies;
import at.jojokobi.pokemine.pokemon.entity.PokemonBehaviorType;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;
import at.jojokobi.pokemine.trainer.WildPokemonTrainer;

public class LegendaryTower extends Structure {

	private static final int STAGE_COUNT = 5;
	private static final int STAGE_HEIGHT = 10;
	private static final Material[] WALL_MATERIALS = { Material.STONE_BRICKS, Material.MOSSY_STONE_BRICKS,
			Material.CRACKED_STONE_BRICKS };

	private LootInventory loot;
//	private PokemonHandler handler;
	private EntityHandler entityHandler;

	public LegendaryTower(EntityHandler entityHandler) {
		super(16, 16, STAGE_HEIGHT * STAGE_COUNT, 1500, 1);
//		this.handler = handler;
		this.entityHandler = entityHandler;
		
		setxModifier(6541);
		setzModifier(-876);
		
		loot = new LootInventory();
		loot.addItem(new LootItem(0.7, ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, Pokeball.IDENTIFIER),
				1, 5));
		loot.addItem(new LootItem(0.3,
				ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, GreatBall.IDENTIFIER), 1, 3));
		loot.addItem(new LootItem(0.2,
				ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, UltraBall.IDENTIFIER), 1, 1));
		loot.addItem(new LootItem(0.5,
				ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, WritableTM.IDENTIFIER), 1, 3));
		loot.addItem(
				new LootItem(0.1, ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, TM.IDENTIFIER), 1, 1));
		loot.addItem(new LootItem(1.0, ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, Potion.IDENTIFIER),
				1, 5));
		loot.addItem(new LootItem(0.2, ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, Revive.IDENTIFIER),
				1, 2));
		loot.addItem(new LootItem(0.3, ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, FullHeal.IDENTIFIER),
				1, 2));

		loot.addItem(new LootItem(0.4, new ItemStack(Material.IRON_INGOT), 1, 5));
		loot.addItem(new LootItem(0.2, new ItemStack(Material.GOLD_INGOT), 1, 3));
		loot.addItem(new LootItem(0.1, new ItemStack(Material.ENDER_PEARL), 1, 1));
		loot.addItem(new LootItem(0.7, new ItemStack(Material.ROTTEN_FLESH), 1, 7));
		loot.addItem(new LootItem(0.2, new ItemStack(Material.PAPER), 1, 3));
	}
	
	@Override
	public int calculatePlacementY(int width, int length, Location place) {
		return super.calculatePlacementY(width, length, place) - 1;
	}

	@Override
	public List<StructureInstance<? extends Structure>> generate(Location loc, long seed) {
		Random random = new Random(generateValueBeasedSeed(loc, seed));
		loc.setY(calculatePlacementY(getWidth(), getLength(), loc));
		Location place = loc.clone();
		
		//Get spawnable pokemon
		List<PokemonSpecies> spawnable = new ArrayList<>();
		for (PokemonSpecies species : PokemonHandler.getInstance().getItemList()) {
			if (!species.isLegendary() && species.getWildLevel() > 20) {
				spawnable.add(species);
			}
		}

		for (int floor = 0; floor < STAGE_COUNT; floor++) {
			// Basic Building
			for (int x = 0; x < getWidth(); x++) {
				for (int y = 0; y < STAGE_HEIGHT; y++) {
					for (int z = 0; z < getLength(); z++) {
						Material material = Material.AIR;
						// Walls
						if ((x == 0 || x == getWidth() - 1 || z == 0 || z == getLength() - 1)
								&& (floor < STAGE_COUNT - 1 || y == 1)) {
							material = WALL_MATERIALS[random.nextInt(WALL_MATERIALS.length)];
						}
						//Ladder
						if (x == getWidth() - 2 && z == getLength() - 2 && floor < STAGE_COUNT - 1 && (y > 0 || floor > 0)) {
							material = Material.LADDER;
						}
						// Floor
						else if (y == 0) {
							material = Material.POLISHED_ANDESITE;
						}
						
						place.setX(loc.getX() + x);
						place.setY(loc.getY() + STAGE_HEIGHT * floor + y);
						place.setZ(loc.getZ() + z);
						place.getBlock().setType(material);
					}
				}
			}
			// Decoration
			// Chest
			place.setX(loc.getX() + 1);
			place.setY(loc.getY() + STAGE_HEIGHT * floor + 1);
			place.setZ(loc.getZ() + 1);
			place.getBlock().setType(Material.CHEST);

			Chest chest = (Chest) place.getBlock().getState();
			loot.fillInventory(chest.getBlockInventory(), random, null);
			
			if (floor < STAGE_COUNT - 1) {
				//Pokemon
				//Set location
				place.setX(loc.getX() + getWidth()/2);
				place.setY(loc.getY() + floor * STAGE_HEIGHT + 1);
				place.setZ(loc.getZ() + getLength()/2);
				//Spawn pokemon
				PokemonSpecies species = spawnable.get(random.nextInt(spawnable.size()));
				Pokemon pokemon = new Pokemon(species, (byte) (20 + floor * 10 + random.nextInt(20)), new WildPokemonTrainer(), random);
				pokemon.selectStrongestMove();
				PokemonEntity entity = new PokemonEntity(place, pokemon, entityHandler);
				entity.setBehaviorType(PokemonBehaviorType.STATIONARY_AGGRESSIVE_POKEMON);
				entity.setSave(true);
				entity.setDespawnTicks(-1);
				entityHandler.addEntity(entity);
			}
		}
		
		//Doors
		place.setX(loc.getX() + (getWidth()/2)-1);
		place.setZ(loc.getZ());
		place.setY(loc.getY() + 1);
		FurnitureGenUtil.generateDoor(place, Material.OAK_DOOR, BlockFace.SOUTH, false, true);
		place.add(1, 0, 0);
		FurnitureGenUtil.generateDoor(place, Material.OAK_DOOR, BlockFace.SOUTH, false, false);
		
		//Legendary Pokemon
		//Set location
		place.setX(loc.getX() + getWidth()/2);
		place.setY(loc.getY() + getHeight());
		place.setZ(loc.getZ() + getLength()/2);
		//Find legendaries
		List<PokemonSpecies> legendaries = new ArrayList<PokemonSpecies> ();
		for (PokemonSpecies species : PokemonHandler.getInstance().getItemList()) {
			if (species.isLegendary()) {
				legendaries.add(species);
			}
		}
		//Spawn legendary
		PokemonSpecies species = legendaries.get(random.nextInt(legendaries.size()));
		Pokemon pokemon = new Pokemon(species, (byte) species.getWildLevel(), new WildPokemonTrainer(), random);
		pokemon.selectStrongestMove();
		PokemonEntity entity = new PokemonEntity(place, pokemon, entityHandler);
		entity.setBehaviorType(PokemonBehaviorType.STATIONARY_POKEMON);
		entity.setSave(true);
		entity.setDespawnTicks(-1);
		entityHandler.addEntity(entity);
		
		return Arrays.asList(new StructureInstance<Structure>(this, loc, getWidth(), getHeight(), getLength()));
	}

	@Override
	public String getIdentifier() {
		return "legendary_tower";
	}

	@Override
	public StructureInstance<? extends Structure> getStandardInstance(Location location) {
		return new StructureInstance<LegendaryTower>(this, location, getWidth(), getHeight(),getLength());
	}

}
