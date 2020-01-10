package at.jojokobi.pokemine.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.PokeminePlugin;

public class ThunderStone extends EvolutionItem implements Buyable{

	public static final String NAME = "Thunder Stone";
	public static final String IDENTIFIER = "thunderstone";
	public static final Material MATERIAL= Material.EMERALD;
	public static final short meta = 1;
	
	public ThunderStone(PokeminePlugin plugin) {
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
		recipe.shape("GRG", "RER", "GRG");
		recipe.setIngredient('R', Material.REDSTONE);
		recipe.setIngredient('G', Material.GLOWSTONE_DUST);
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
