package at.jojokobi.pokemine.items;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.gui.MoveLearnGUI;
import at.jojokobi.pokemine.gui.PartySelectionGUI;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.trainer.Trainer;

public abstract class EvolutionItem extends PokemineItem {

	public EvolutionItem(String namespace, String identifier, PokeminePlugin plugin) {
		super(namespace, identifier, plugin);
		setMaxStackSize(16);
		setHideFlags(true);
	}

	@Override
	public boolean onTrainerUse(ItemStack item, Trainer trainer, Player player) {
		PartySelectionGUI gui = new PartySelectionGUI(player, trainer) {
			@Override
			public boolean call(Pokemon pokemon) {
				String name = pokemon.getName();
				boolean toLearn = pokemon.evolve(PokemonHandler.getInstance(), item, false);
				if (toLearn) {
					item.setAmount(item.getAmount() - 1);
					pokemon.getOwner().message("Your " + name + " evolved to " + pokemon.getSpecies().getName() + "!");
					MoveLearnGUI.learnMoves(getPlugin().getGUIHandler(), pokemon);
				}
				else {
					pokemon.getOwner().message("It had no effect!");
				}
				return true;
			}
		};
		getPlugin().getGUIHandler().addGUI(gui);
		gui.show();
		
		return true;
	}
	
	@Override
	public void onHit(ItemStack item, Entity damager, Entity defender) {
		
	}

}
