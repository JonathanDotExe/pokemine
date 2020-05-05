package at.jojokobi.pokemine.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.item.CustomItem;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.trainer.Trainer;

public abstract class PokemineItem extends CustomItem {
	
	private PokeminePlugin plugin;

	public PokemineItem(String namespace, String identifier, PokeminePlugin plugin) {
		super(namespace, identifier);
		this.plugin = plugin;
	}

	@Override
	public final boolean onUse(ItemStack item, Player player) {
		Trainer trainer = plugin.getPlayerTrainerHandler().getTrainer(player);
		if (plugin.getBattleHandler().getTrainersBattle(trainer) == null) {
			return onTrainerUse(item, trainer, player);
		}
		else {
			trainer.message("You can't you this item during a battle.");
			return false;
		}
	}
	
	public PokeminePlugin getPlugin() {
		return plugin;
	}

	public abstract boolean onTrainerUse (ItemStack item, Trainer trainer, Player player);

}
