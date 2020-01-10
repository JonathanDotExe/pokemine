package at.jojokobi.pokemine.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.item.CustomItem;
import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.items.Buyable;
import at.jojokobi.pokemine.trainer.Trainer;

public class ItemShopGUI extends ShopGUI{

	public ItemShopGUI(Player owner, Trainer trainer) {
		super(owner, trainer);
		initGUI();
	}

	@Override
	public List<Buyable> generateShopItems() {
		List<Buyable> items = new ArrayList<>();
		for (NamespacedEntry  key : ItemHandler.getAllItems().keySet()) {
			CustomItem item = ItemHandler.getCustomItem(key.getNamespace(), key.getIdentifier());
			if (item instanceof Buyable) {
				items.add((Buyable) item);
			}
		}
		return items;
	}

}
