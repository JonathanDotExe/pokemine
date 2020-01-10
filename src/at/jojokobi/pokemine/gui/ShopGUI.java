package at.jojokobi.pokemine.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.mcutil.gui.ListGUI;
import at.jojokobi.pokemine.items.Buyable;
import at.jojokobi.pokemine.trainer.Trainer;

public abstract class ShopGUI extends ListGUI {

	private Trainer trainer;
	private List<Buyable> shopItems = new ArrayList<>();

	public ShopGUI(Player owner, Trainer trainer) {
		super(owner, Bukkit.createInventory(owner, INV_ROW * 6, "Shop: "));
		setItemsPerPage(INV_ROW * 5);
		setStartIndex(0);
		this.trainer = trainer;
	}

	public abstract List<Buyable> generateShopItems();

	@Override
	protected void initGUI() {
		// Generate shop entries
		List<Buyable> buyables = generateShopItems();
		List<ItemStack> items = new ArrayList<>();
		for (Buyable buyable : buyables) {
			if (trainer.getLevel() >= buyable.getMinBuyLevel()) {
				ItemStack stack = buyable.getShopIcon();
				ItemMeta meta = stack.getItemMeta();
				List<String> lore = meta.getLore();
				if (lore == null) {
					lore = new ArrayList<>();
				}
				lore.add("Price: " + buyable.getBuyPrice());
				meta.setLore(lore);
				stack.setItemMeta(meta);
				items.add(stack);
				shopItems.add(buyable);
			}
		}
		setItems(items);
		super.initGUI();
		fillEmpty(getFiller());
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		int pageIndex = getInventory().first(button);
		System.out.println(pageIndex);
		if (pageIndex < getItemsPerPage() && pageIndex >= 0) {
			int index = getPage() * getItemsPerPage() + pageIndex;
			if (index < shopItems.size()) {
				Buyable bought = shopItems.get(index);
				if (trainer.getMoney() >= bought.getBuyPrice()) {
					buy(trainer, getOwner(), bought);
				}
				else {
					trainer.message("You can't afford this item!");
				}
			}
		}
	}
	
	protected void buy (Trainer trainer, Player player, Buyable bought) {
		trainer.setMoney(trainer.getMoney() - bought.getBuyPrice());
		player.getInventory().addItem(bought.getBoughtItem());
	}

	public Trainer getTrainer() {
		return trainer;
	}

}
