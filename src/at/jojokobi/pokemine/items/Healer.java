package at.jojokobi.pokemine.items;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
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
import at.jojokobi.pokemine.trainer.Trainer;

public class Healer extends PlaceableItem implements Buyable{
	
	public static final String NAME = "Healer";
	public static final String IDENTIFIER = "healer";
	public static final Material MATERIAL= Material.DIAMOND_HOE;
	public static final short meta = 4;
	
	PokeminePlugin plugin;
	
	public Healer(PokeminePlugin plugin) {
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
		recipe.shape("IGI", "IAI", "ROR");
		recipe.setIngredient('R', Material.REDSTONE);
		recipe.setIngredient('O', Material.GOLD_INGOT);
		recipe.setIngredient('A', Material.GOLDEN_APPLE);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('G', Material.GLASS);
		return recipe;
	}
	
	@Override
	public boolean onUse(ItemStack item, Player player) {
		return false;
	}
	
	@Override
	public void onHit(ItemStack item, Entity damager, Entity defender) {
		
	}
	
	@Override
	public void spawnDrops(Location place) {
		place.getWorld().dropItemNaturally(place, new ItemStack(Material.IRON_INGOT, 4));
		place.getWorld().dropItemNaturally(place, new ItemStack(Material.REDSTONE, 2));
		place.getWorld().dropItemNaturally(place, new ItemStack(Material.GOLD_INGOT, (int) Math.round(Math.random() * 2)));
	}
	
	@EventHandler
	public void onPlayerInteractAtEntity (PlayerInteractAtEntityEvent event) {
		if (isItemEntity(event.getRightClicked())) {
			Player player = event.getPlayer();
			Trainer trainer = plugin.getPlayerTrainerHandler().getTrainer(player);
			if (plugin.getBattleHandler().getTrainersBattle(trainer) == null) {
				trainer.heal();
				trainer.setEliteFourDefeatLevel(0);
				player.playSound(event.getRightClicked().getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, 1);
				trainer.message("You Pokemon have been fully regenerated!");
			}
			else {
				trainer.message("You can't heal during a battle!");
			}
		}
	}

	@Override
	public int getBuyPrice() {
		return 100000;
	}

	@Override
	public byte getMinBuyLevel() {
		return 30;
	}
	
	@Override
	public ItemStack getBoughtItem() {
		return createItem();
	}

}
