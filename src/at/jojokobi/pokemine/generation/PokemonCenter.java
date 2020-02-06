package at.jojokobi.pokemine.generation;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import at.jojokobi.mcutil.generation.FurnitureGenUtil;
import at.jojokobi.mcutil.generation.population.Structure;
import at.jojokobi.mcutil.generation.population.StructureInstance;
import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.mcutil.item.PlaceableItem;
import at.jojokobi.mcutil.music.Music;
import at.jojokobi.mcutil.music.MusicPlayer;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.items.Healer;
import at.jojokobi.pokemine.items.PcItem;

public class PokemonCenter extends Structure implements Listener{

	public static final String POKEMON_CENTER_THEME_NAME = "pokemoncenter.ambient";
	public static final Music POKEMON_CENTER_THEME= new Music(POKEMON_CENTER_THEME_NAME, 90);
	
	public PokeminePlugin plugin;
	
	public PokemonCenter(PokeminePlugin plugin) {
		super(10,10,8,600,1);
		setxModifier(16165);
		setzModifier(981);
		this.plugin = plugin;
	}
	
	@Override
	public boolean canGenerate(Chunk chunk, long seed) {
		return super.canGenerate(chunk, seed) || chunk.getWorld().getSpawnLocation().getBlock().getChunk() == chunk;
	}

	@Override
	public List<StructureInstance<? extends Structure>> generate(Location loc, long seed) {
		loc.setY(calculatePlacementY(getWidth(), getLength(), loc));
		Location place = loc.clone();
		//Structure
		for (int x = 0; x < getWidth(); x++) {
			place.setX(loc.getX() + x);
			for (int z = 0; z < getLength(); z++) {
				place.setZ(loc.getZ() + z);
				for (int y = 0; y < getHeight(); y++) {
					place.setY(loc.getY() + y);
					Material material = Material.AIR;
					if (y >= getHeight() - 2) {
						if (y == getHeight() - 1 && ((x == 0 && z == 0) || (x == getWidth() - 1 && z == 0) || (x == 0 && z == getLength()-1) || (x == getWidth() - 1 && z == getLength()-1))) {
							material = Material.AIR;
						}
						else {
							material = Material.RED_CONCRETE;
						}
					}
					else if (y >= 2 && y <= getHeight() - 2 && z >= 2 && z <= getLength() - 3 && (x == 0 || x == getWidth() - 1)) {
						material = Material.GLASS;
					}
					else if (x == 0 || x == getWidth() - 1 || y == 0 || z == 0 || z == getLength() - 1) {
						material = Material.WHITE_CONCRETE;
					}
					else if (y == getHeight() - 3 && ((x == 1 || x == getWidth() - 2) && (z == 1 || z == getLength() - 2))) {
						material = Material.SEA_LANTERN;
					}
					place.getBlock().setType(material, false);
//					BlockState state = place.getBlock().getState();
//					state.update();
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
		//Table
		place.setZ(loc.getZ() + getLength() - 3);
		for (int i = 0; i < getWidth() - 4; i++) {
			place.setX(loc.getX() + 2 + i);
			place.getBlock().setType(Material.STONE_BRICKS);
		}
		//Healer
		place.setZ(loc.getZ() + getLength() - 2);
		place.setX(loc.getX() + 1);
		if (ItemHandler.getCustomItem(PokeminePlugin.POKEMINE_NAMESPACE, Healer.IDENTIFIER) instanceof PlaceableItem) {
			((PlaceableItem) ItemHandler.getCustomItem(PokeminePlugin.POKEMINE_NAMESPACE, Healer.IDENTIFIER)).getItemEntity(place, new Vector(0,0,-1));
		}
		//PC
		place.setZ(loc.getZ() + getLength() - 3);
		place.setX(loc.getX() + getLength() - 3);
		place.setY(loc.getY() + 2);
		if (ItemHandler.getCustomItem(PokeminePlugin.POKEMINE_NAMESPACE, PcItem.IDENTIFIER) instanceof PlaceableItem) {
			((PlaceableItem) ItemHandler.getCustomItem(PokeminePlugin.POKEMINE_NAMESPACE, PcItem.IDENTIFIER)).getItemEntity(place, new Vector(0,0,-1));
		}
		return Arrays.asList(new StructureInstance<PokemonCenter>(this, loc, getWidth(), getHeight(), getLength()));
	}

	@Override
	public String getIdentifier() {
		return "pokemon_center";
	}
	
	@Override
	public StructureInstance<? extends Structure> getStandardInstance(Location location) {
		return new StructureInstance<PokemonCenter>(this, location, getWidth(), getHeight(),getLength());
	}
	
	@EventHandler
	public void onPlayerMove (PlayerMoveEvent event) {
		StructureInstance<? extends Structure> structure = plugin.getGenerationHandler().getInstanceAt(event.getTo());
		if (structure != null && structure.getStructure() == this && plugin.getMusicHandler().getMusic(event.getPlayer()) == null) {
			plugin.getMusicHandler().playMusic(POKEMON_CENTER_THEME, event.getPlayer(), true);
		}
		else if ((structure == null || !(structure.getStructure() instanceof PokemonCenter))) {
			MusicPlayer music = plugin.getMusicHandler().getMusic(event.getPlayer());
			if (music != null && music.getMusic() == POKEMON_CENTER_THEME) {
				music.stop();
			}
		}
	}

}
