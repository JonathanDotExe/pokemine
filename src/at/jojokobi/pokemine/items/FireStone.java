package at.jojokobi.pokemine.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.PokeminePlugin;

public class FireStone extends EvolutionItem implements Buyable{

	public static final String NAME = "Fire Stone";
	public static final String IDENTIFIER = "firestone";
	public static final Material MATERIAL= Material.EMERALD;
	public static final short meta = 2;
	
	public FireStone(PokeminePlugin plugin) {
		super(PokeminePlugin.POKEMINE_NAMESPACE, IDENTIFIER, plugin);
		setName(NAME);
		setMaterial(MATERIAL);
		setMeta(meta);
		Bukkit.getScheduler().runTask(plugin, () -> registerRecipe());
		setMaxStackSize(0);
		ItemHandler.addCustomItem(this);
	}

	@Override
	public Recipe getRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), getIdentifier()), createItem());
		recipe.shape("FLF", "LEL", "FLF");
		recipe.setIngredient('F', Material.FLINT);
		recipe.setIngredient('L', Material.LAVA_BUCKET);
		recipe.setIngredient('E', Material.EMERALD);
		return recipe;
	}

	@Override
	public int getBuyPrice() {
		return 2100;
	}

	@Override
	public byte getMinBuyLevel() {
		return 25;
	}
	
	@Override
	public ItemStack getBoughtItem() {
		return createItem();
	}

}
