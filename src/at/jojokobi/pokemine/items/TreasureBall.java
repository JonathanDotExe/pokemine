package at.jojokobi.pokemine.items;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.mcutil.item.PlaceableItem;
import at.jojokobi.pokemine.PokeminePlugin;

public class TreasureBall extends PlaceableItem implements Buyable{

	public static final String NAME = "Treasure Ball";
	public static final String IDENTIFIER = "treasure_ball";
	public static final Material MATERIAL= Material.DIAMOND_HOE;
	
	public TreasureBall(PokeminePlugin plugin) {
		super(PokeminePlugin.POKEMINE_NAMESPACE, IDENTIFIER);
		setName(NAME);
		setMaterial(MATERIAL);
		ItemHandler.addCustomItem(this);
		setMaxStackSize(0);
		//Loop
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				for (World world : Bukkit.getWorlds()) {
					for (Entity entity : world.getEntities()) {
						if (entity instanceof ArmorStand) {
							ArmorStand stand = (ArmorStand) entity;
							if (isItem(stand.getEquipment().getHelmet()) && stand.getTicksLived() > 5000) {
								stand.getEquipment().setHelmet(new ItemStack(Material.AIR));
								stand.remove();
							}
						}
					}
				}
			}
		}, 100L, 100L);
	}

	@Override
	public Recipe getRecipe() {
		return null;
	}
	
	@Override
	public void onHit(ItemStack item, Entity damager, Entity defender) {
		
	}
	
	@Override
	public void spawnDrops(Location place) {
		Random random = new Random();
		ItemStack item = null;
		int num = random.nextInt(65);
		if (num < 8) {
			item = ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, Pokeball.IDENTIFIER);
			item.setAmount(random.nextInt(3)+1);
		}
		else if (num < 15) {
			item = ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, GreatBall.IDENTIFIER);
			item.setAmount(random.nextInt(3)+1);
		}
		else if (num < 19) {
			item = ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, UltraBall.IDENTIFIER);
			item.setAmount(random.nextInt(2)+1);
		}
		else if (num < 21) {
			item = ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, TM.IDENTIFIER);
			item.setAmount(1);
		}
		else if (num < 31) {
			item = ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, WritableTM.IDENTIFIER);
			item.setAmount(1);
		}
		else if (num < 37) {
			item = ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, Revive.IDENTIFIER);
			item.setAmount(1);
		}
		else if (num < 47) {
			item = ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, FullHeal.IDENTIFIER);
			item.setAmount(1);
		}
		else if (num < 58) {
			item = ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, Potion.IDENTIFIER);
			item.setAmount(random.nextInt(3)+1);
		}
		else if (num < 63){
			item = ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, SuperPotion.IDENTIFIER);
			item.setAmount(random.nextInt(2)+1);
		}
		else {
			item = ItemHandler.getItemStack(PokeminePlugin.POKEMINE_NAMESPACE, HyperPotion.IDENTIFIER);
			item.setAmount(1);
		}
		place.getWorld().dropItem(place, item);
	}

	@Override
	public void onUse(ItemStack item, Player player) {
		
	}

	@Override
	public int getBuyPrice() {
		return 1500;
	}

	@Override
	public byte getMinBuyLevel() {
		return 15;
	}
	
	@Override
	public ItemStack getBoughtItem() {
		return createItem();
	}

}
