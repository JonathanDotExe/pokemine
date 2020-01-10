package at.jojokobi.pokemine.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.PokeminePlugin;

public class UltraBall extends AbstractPokeball implements Buyable{

	public static final String NAME = "Ultra Ball";
	public static final String IDENTIFIER = "ultraball";
	public static final Material MATERIAL= Material.SNOWBALL;
	public static final short meta = 3;
	
	public UltraBall(PokeminePlugin plugin) {
		super(PokeminePlugin.POKEMINE_NAMESPACE, IDENTIFIER, plugin);
		setCatchRate(2);
		setName(NAME);
		setMaterial(MATERIAL);
		setMeta(meta);
		Bukkit.getScheduler().runTask(plugin, () -> registerRecipe());
		setMaxStackSize(0);
		ItemHandler.addCustomItem(this);
	}

	@Override
	public ShapedRecipe getRecipe() {
		ItemStack item = createItem();
		item.setAmount(2);
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), getIdentifier()), item);
		recipe.shape("CGC", "RBR", "NIN");
		recipe.setIngredient('R', Material.REDSTONE);
		recipe.setIngredient('C', Material.COAL);
		recipe.setIngredient('G', Material.GOLD_INGOT);
		recipe.setIngredient('B', Material.STONE_BUTTON);
		recipe.setIngredient('N', Material.IRON_NUGGET);
		recipe.setIngredient('I', Material.IRON_INGOT);
		return recipe;
	}

	@Override
	public int getBuyPrice() {
		return 800;
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
