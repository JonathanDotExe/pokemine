package at.jojokobi.pokemine.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.trainer.Trainer;


public abstract class PartySelectionGUI extends PartyGUI implements PokemonMethod{
	
	
	public PartySelectionGUI(Player owner, Trainer trainer) {
		super(owner, trainer);
		initGUI();
	}

	@Override
	protected void onPokemonClick(Pokemon pokemon, ClickType click) {
		if (call(pokemon)) {
			close();
		}
	}

}
