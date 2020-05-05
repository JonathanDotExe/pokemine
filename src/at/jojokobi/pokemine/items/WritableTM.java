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
import at.jojokobi.pokemine.gui.MoveShopGUI;
import at.jojokobi.pokemine.moves.MoveHandler;

public class WritableTM extends CustomItem implements Buyable{

	public static final String NAME = "Writable TM";
	public static final String IDENTIFIER = "writable_tm";
	public static final Material MATERIAL= Material.DIAMOND_HOE;
	public static final short meta = 4;
	
	private PokeminePlugin plugin;

	public WritableTM(PokeminePlugin plugin) {
		super(PokeminePlugin.POKEMINE_NAMESPACE, IDENTIFIER);
		this.plugin = plugin;
		setName(NAME);
		setMaterial(MATERIAL);
		setMeta(meta);
//		setMaxStackSize(16);
		Bukkit.getScheduler().runTask(plugin, () -> registerRecipe());
		setMaxStackSize(0);
		ItemHandler.addCustomItem(this);
	}
	
	@Override
	public Recipe getRecipe() {
		ItemStack item = createItem();
		item.setAmount(1);
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, getIdentifier()), item);
		recipe.shape(" I ", "IGI", " I ");
		recipe.setIngredient('G', Material.GOLD_INGOT);
		recipe.setIngredient('I', Material.IRON_INGOT);
		return recipe;
	}
	
	@Override
	public boolean onUse(ItemStack item, Player player) {
		MoveShopGUI gui = new MoveShopGUI(player, plugin.getPlayerTrainerHandler().getTrainer(player), MoveHandler.getInstance(), item);
		plugin.getGUIHandler().addGUI(gui);
		gui.show();
		return true;
	}
	
	@Override
	public void onHit(ItemStack item, Entity damager, Entity defender) {
		
	}

	@Override
	public int getBuyPrice() {
		return 500;
	}

	@Override
	public byte getMinBuyLevel() {
		return 10;
	}
	
	@Override
	public ItemStack getBoughtItem() {
		return createItem();
	}

}
