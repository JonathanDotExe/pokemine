package at.jojokobi.pokemine.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import at.jojokobi.mcutil.gui.InventoryGUIHandler;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.trainer.Trainer;

@Deprecated
public class PokemineGUIHandler extends InventoryGUIHandler{
	
	private PokeminePlugin plugin;
	
	public PokemineGUIHandler(PokeminePlugin plugin) {
		super(plugin);
		this.plugin = plugin;
	}
	
//	@Override
//	@EventHandler
//	public void onInventoryClose (InventoryCloseEvent event) {
//		super.onInventoryClose(event);
//		Iterator<InventoryGUI> iter = getRealGUIs().iterator();
//		while (iter.hasNext()) {
//			InventoryGUI gui = iter.next();
//			if (event.getInventory().equals(gui.getInventory())) {
//				iter.remove();
//			}
//		}
//	}
	
	@Override
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		super.onInventoryClick(event);
		if (event.getClickedInventory() == event.getWhoClicked().getInventory()) {
			if (event.getWhoClicked() instanceof Player) {
				Player player = (Player) event.getWhoClicked();
				if (event.getClick() == ClickType.DOUBLE_CLICK) {
					Trainer trainer = plugin.getPlayerTrainerHandler().getTrainer(player);
					TrainerGUI gui = new TrainerGUI(player, trainer, plugin);
					addGUI(gui);
					gui.show();
				}
			}
		}
	}
	
	@EventHandler
	@Override
	public void onInventoryOpen(InventoryOpenEvent event) {
		super.onInventoryOpen(event);
	}

	public PokeminePlugin getPlugin() {
		return plugin;
	}
	
}
