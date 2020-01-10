package at.jojokobi.pokemine.moves;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonType;

public final class MoveUtil {

	private MoveUtil() {
		
	}
	
	public static ItemStack itemFromMove (MoveInstance move) {
		Move type = move.getMove();
		ItemStack item = new ItemStack(getMoveMaterial(type.getType()));
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(type.getName());
		List<String> lore = new ArrayList<>();
		lore.add("Power " + type.getPower());
		lore.add("Accuracy: " + type.getAccuracy());
		lore.add("PP: " + move.getPp() + "/" + move.getMaxPP());
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static Material getMoveMaterial (PokemonType type) {
		Material material = Material.AIR;
		switch (type) {
		case BUG:
			material = Material.VINE;
			break;
		case DARK:
			material = Material.ENDER_EYE;
			break;
		case DRAGON:
			material = Material.ELYTRA;
			break;
		case ELECTRIC:
			material = Material.REDSTONE;
			break;
		case FAIRY:
			material = Material.END_ROD;
			break;
		case FIGHTING:
			material = Material.IRON_SWORD;
			break;
		case FIRE:
			material = Material.LAVA_BUCKET;
			break;
		case FLYING:
			material = Material.FEATHER;
			break;
		case GHOST:
			material = Material.TOTEM_OF_UNDYING;
			break;
		case GRASS:
			material = Material.OAK_LEAVES;
			break;
		case GROUND:
			material = Material.DIRT;
			break;
		case ICE:
			material = Material.ICE;
			break;
		case NORMAL:
			material = Material.EGG;
			break;
		case POISON:
			material = Material.POTION;
			break;
		case PSYCHIC:
			material = Material.ENCHANTED_BOOK;
			break;
		case ROCK:
			material = Material.COBBLESTONE;
			break;
		case STEEL:
			material = Material.IRON_BLOCK;
			break;
		case WATER:
			material = Material.WATER_BUCKET;
			break;
		default:
			break;
		}
		return material;
	}
	
	public static MoveInstance[] generateRandomMoveset (Pokemon pokemon) {
		MoveInstance[] moveset = new MoveInstance[Pokemon.MAX_MOVES];
		List<Move> moves = getLearnableMoves(pokemon);
		Random random = new Random();
		for (int i = 0; i < moveset.length && !moves.isEmpty(); i++) {
			Move move = moves.get(random.nextInt(moves.size()));
			moves.remove(move);
			moveset[i] = new MoveInstance(move);
		}
		return moveset;
	}
	
	
	public static List<Move> getLearnableMoves (Pokemon pokemon) {
		List<Move> moves = new ArrayList<>();
		List<MoveLearnCondition> allMoves = pokemon.getSpecies().getLearnableMoves();
		for (MoveLearnCondition move : allMoves) {
			if (move.canLearn(pokemon)) {
				moves.add(move.getRealMove());
			}
		}
		return moves;
	}

}
