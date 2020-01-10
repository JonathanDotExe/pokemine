package at.jojokobi.pokemine.items;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.mcutil.item.PlaceableItem;
import at.jojokobi.mcutil.item.Rotation;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.gui.PcGUI;

public class PcItem extends PlaceableItem{

	public static final String NAME = "PC";
	public static final String IDENTIFIER = "pc";
	public static final Material MATERIAL= Material.DIAMOND_HOE;
	public static final short meta = 5;
	
	private PokeminePlugin plugin;
	
	public PcItem (PokeminePlugin plugin) {
		super(PokeminePlugin.POKEMINE_NAMESPACE, IDENTIFIER);
		this.plugin = plugin;
		setName(NAME);
		setMaterial(MATERIAL);
		setHideFlags(true);
		setMeta(meta);
		setMaxStackSize(1);
		setRotation(Rotation.CARDINAL);
		Bukkit.getScheduler().runTask(plugin, () -> registerRecipe());
		ItemHandler.addCustomItem(this);
	}
	
	@Override
	public Recipe getRecipe() {
		ItemStack item = createItem();
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, getIdentifier()), item);
		recipe.shape("IGI", "IHI", "RDR");
		recipe.setIngredient('R', Material.REDSTONE);
		recipe.setIngredient('D', Material.DROPPER);
		recipe.setIngredient('H', Material.HOPPER);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('G', Material.GLASS);
		return recipe;
	}
	
	@Override
	public void spawnDrops(Location place) {
		place.getWorld().dropItemNaturally(place, new ItemStack(Material.IRON_INGOT, 4));
		place.getWorld().dropItemNaturally(place, new ItemStack(Material.REDSTONE, 2));
		place.getWorld().dropItemNaturally(place, new ItemStack(Material.DROPPER, (int) Math.round(Math.random())));
	}
	
	@EventHandler
	public void onPlayerInteractAtEntity (PlayerInteractAtEntityEvent event) {
		if (isItemEntity(event.getRightClicked())) {
			PcGUI gui = new PcGUI(event.getPlayer(), plugin.getPlayerTrainerHandler().getTrainer(event.getPlayer()));
			plugin.getGUIHandler().addGUI(gui);
			gui.show();
		}
	}
	
	@Override
	public void onUse(ItemStack item, Player player) {
		
	}
	
	@Override
	public void onHit(ItemStack item, Entity damager, Entity defender) {
		
	}

}
