package at.jojokobi.pokemine.gui;

import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.trainer.Trainer;

public class PokemonSwapper {
	
	private Pokemon selectedPokemon;
	private Trainer trainer;
	
	public PokemonSwapper (Trainer trainer) {
		this.trainer = trainer;
	}
	
	protected void pokemonClicked (Pokemon pokemon) {
		//Last Pokemon
		boolean isParty = trainer.getParty().contains (selectedPokemon);
		if (selectedPokemon == null) {
			selectedPokemon = pokemon;
		}
		else if (pokemon != null) {
			trainer.swapPokemon(pokemon, selectedPokemon);
			selectedPokemon = null;
		}
		else {
			if (isParty) {
				trainer.moveToPc(selectedPokemon);
			}
			else {
				trainer.moveToParty(selectedPokemon);
			}
			selectedPokemon = null;
		}
	}

	protected Pokemon getSelectedPokemon() {
		return selectedPokemon;
	}

	protected void setSelectedPokemon(Pokemon selectedPokemon) {
		this.selectedPokemon = selectedPokemon;
	}
	
}
