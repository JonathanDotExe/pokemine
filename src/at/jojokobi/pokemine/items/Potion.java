package at.jojokobi.pokemine.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.PokeminePlugin;

public class Potion extends AbstractPotion implements Buyable{

	public static final String NAME = "Potion";
	public static final String IDENTIFIER = "potion";
	public static final Material MATERIAL= Material.GLASS_BOTTLE;
	public static final short meta = 1;
	
	public Potion(PokeminePlugin plugin) {
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
		ItemStack item = createItem();
		item.setAmount(4);
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), getIdentifier()), item);
		recipe.shape(" S ", "SBS", "   ");
		recipe.setIngredient('S', Material.SUGAR);
		recipe.setIngredient('B', Material.GLASS_BOTTLE);
		return recipe;
	}

	@Override
	public int getBuyPrice() {
		return 200;
	}

	@Override
	public byte getMinBuyLevel() {
		return 5;
	}
	
	@Override
	public ItemStack getBoughtItem() {
		return createItem();
	}

}
