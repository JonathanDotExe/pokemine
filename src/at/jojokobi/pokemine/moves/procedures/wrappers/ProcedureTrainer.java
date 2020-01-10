package at.jojokobi.pokemine.moves.procedures.wrappers;

import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.trainer.Trainer;

public class ProcedureTrainer {
	
	private Trainer trainer;

	public ProcedureTrainer(Trainer trainer) {
		super();
		this.trainer = trainer;
	}

	public String getName () {
		return trainer.getName();
	}
	
	public void message (String message) {
		trainer.message(message);
	}
	
	public boolean representsTrainer (Trainer trainer) {
		return this.trainer == trainer;
	}
	
	public boolean ownsPokemon (Pokemon pokemon) {
		return pokemon.getOwner() == trainer;
	}

}
