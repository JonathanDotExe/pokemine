package at.jojokobi.pokemine.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.PokeminePlugin;

public class HyperPotion extends AbstractPotion implements Buyable{

	public static final String NAME = "Hyper Potion";
	public static final String IDENTIFIER = "hyper_potion";
	public static final Material MATERIAL= Material.GLASS_BOTTLE;
	public static final short meta = 3;
	
	public HyperPotion(PokeminePlugin plugin) {
		super(PokeminePlugin.POKEMINE_NAMESPACE, IDENTIFIER, plugin);
		setName(NAME);
		setMaterial(MATERIAL);
		setMeta(meta);
		Bukkit.getScheduler().runTask(plugin, () -> registerRecipe());
		setHeal(120);
		setMaxStackSize(0);
		ItemHandler.addCustomItem(this);
	}

	@Override
	public Recipe getRecipe() {
		ItemStack item = createItem();
		item.setAmount(1);
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), getIdentifier()), item);
		recipe.shape("SSS", "SBS", "AMA");
		recipe.setIngredient('S', Material.SUGAR);
		recipe.setIngredient('B', Material.GLASS_BOTTLE);
		recipe.setIngredient('M', Material.MILK_BUCKET);
		recipe.setIngredient('A', Material.APPLE);
		return recipe;
	}

	@Override
	public int getBuyPrice() {
		return 1500;
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
