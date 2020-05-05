package at.jojokobi.pokemine.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import at.jojokobi.mcutil.item.CustomItem;
import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.PokeminePlugin;

public class ExpShare extends CustomItem{

	public static final String NAME = "Exp. Share";
	public static final String IDENTIFIER = "exp_share";
	public static final Material MATERIAL= Material.DIAMOND_HOE;
	public static final short meta = 15;
	
	private PokeminePlugin plugin;

	public ExpShare(PokeminePlugin plugin) {
		super(PokeminePlugin.POKEMINE_NAMESPACE, IDENTIFIER);
		this.plugin = plugin;
		setName(NAME);
		setMaterial(MATERIAL);
		setMeta(meta);
		setMaxStackSize(1);
		Bukkit.getScheduler().runTask(plugin, () -> registerRecipe());
		setHelmet(true);
		ItemHandler.addCustomItem(this);
	}
	
	@Override
	public Recipe getRecipe() {
		ItemStack item = createItem();
		item.setAmount(1);
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, getIdentifier()), item);
		recipe.shape("GDG", "DID", "I I");
		recipe.setIngredient('G', Material.GOLD_INGOT);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('D', Material.GLOWSTONE_DUST);
		return recipe;
	}
	
	@Override
	public boolean onUse(ItemStack item, Player player) {
		return false;
	}
	
	@Override
	public void onHit(ItemStack item, Entity damager, Entity defender) {
		
	}

}
