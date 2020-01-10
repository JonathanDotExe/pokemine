package at.jojokobi.pokemine.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import at.jojokobi.mcutil.gui.InventoryGUI;
import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.trainer.Trainer;

public class DeathSwitchGUI extends PartySelectionGUI{

	private Pokemon newPokemon = null;
	private PokemonContainer oldPokemon;
	
	public DeathSwitchGUI(Player owner, Trainer trainer, PokemonContainer oldPokemon) {
		super(owner, trainer);
		this.oldPokemon = oldPokemon;
	}

	public Pokemon getNewPokemon() {
		return newPokemon;
	}
	
	public PokemonContainer getOldPokemon() {
		return oldPokemon;
	}

	@Override
	public boolean call(Pokemon pokemon) {
		boolean switchPoke = pokemon.getHealth() > 0;
		if (switchPoke) {
			this.newPokemon = pokemon;
		}
		return switchPoke;
	}
	
	@Override
	protected InventoryGUI onInventoryClose(InventoryCloseEvent event) {
		if (newPokemon == null) {
			setNext(this);
		}
		return super.onInventoryClose(event);
	}

}
