package at.jojokobi.pokemine.pokemon.status;

import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class Drowsiness extends SecStatChange{

	public static final String SCRIPT_NAME = "drowsiness";
	
	public Drowsiness() {
		setHasDuration(true);
		setDuration(1);
	}
	
	@Override
	public boolean startRound(PokemonContainer victim) {
		boolean remove = super.startRound(victim);
		if (getRound() > getDuration() && victim.getPokemon().getPrimStatChange() == null) {
			victim.getPokemon().setPrimStatChange(new Sleep());
		}
		return remove;
	}
	
	@Override
	public String getScriptName() {
		return SCRIPT_NAME;
	}
	
	@Override
	public String getMessage(Pokemon victim) {
		return victim.getName() + " is drowsy!";
	}
	
	@Override
	public String getAddMessage(Pokemon pokemon) {
		return pokemon.getName() + " became drowsy!";
	}

}
