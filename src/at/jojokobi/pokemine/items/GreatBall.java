package at.jojokobi.pokemine.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.PokeminePlugin;

public class GreatBall extends AbstractPokeball implements Buyable{

	public static final String NAME = "Great Ball";
	public static final String IDENTIFIER = "greatball";
	public static final Material MATERIAL= Material.SNOWBALL;
	public static final short meta = 2;
	
	public GreatBall(PokeminePlugin plugin) {
		super(PokeminePlugin.POKEMINE_NAMESPACE, IDENTIFIER, plugin);
		setCatchRate(1.5);
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
		item.setAmount(4);
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), getIdentifier()), item);
		recipe.shape("RLR", "LBL", "NIN");
		recipe.setIngredient('R', Material.REDSTONE);
		recipe.setIngredient('L', Material.LAPIS_LAZULI);
		recipe.setIngredient('B', Material.STONE_BUTTON);
		recipe.setIngredient('N', Material.IRON_NUGGET);
		recipe.setIngredient('I', Material.IRON_INGOT);
		return recipe;
	}

	@Override
	public int getBuyPrice() {
		return 600;
	}

	@Override
	public byte getMinBuyLevel() {
		return 17;
	}

	@Override
	public ItemStack getBoughtItem() {
		return createItem();
	}

}
