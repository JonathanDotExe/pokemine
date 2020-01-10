package at.jojokobi.pokemine.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.PokeminePlugin;

public class Pokeball extends AbstractPokeball implements Buyable{

	public static final String NAME = "Pokeball";
	public static final String IDENTIFIER = "pokeball";
	public static final Material MATERIAL= Material.SNOWBALL;
	
	public Pokeball(PokeminePlugin plugin) {
		super(PokeminePlugin.POKEMINE_NAMESPACE, IDENTIFIER, plugin);
		setName(NAME);
		setMaterial(MATERIAL);
		Bukkit.getScheduler().runTask(plugin, () -> registerRecipe());
		setMaxStackSize(0);
		ItemHandler.addCustomItem(this);
	}

	@Override
	public ShapedRecipe getRecipe() {
		ItemStack item = createItem();
		item.setAmount(4);
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), getIdentifier()), item);
		recipe.shape("RRR", "RBR", "NIN");
		recipe.setIngredient('R', Material.REDSTONE);
		recipe.setIngredient('B', Material.STONE_BUTTON);
		recipe.setIngredient('N', Material.IRON_NUGGET);
		recipe.setIngredient('I', Material.IRON_INGOT);
		return recipe;
	}

	@Override
	public int getBuyPrice() {
		return 200;
	}

	@Override
	public byte getMinBuyLevel() {
		return 1;
	}
	
	@Override
	public ItemStack getBoughtItem() {
		return createItem();
	}
	
}
