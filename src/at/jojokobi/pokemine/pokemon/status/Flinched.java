package at.jojokobi.pokemine.pokemon.status;

import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class Flinched extends SecStatChange{

	public static final String SCRIPT_NAME = "flinch";
	
	public Flinched() {
		
	}
	
	@Override
	public boolean canAttack(PokemonContainer victim) {
		return false;
	}

	@Override
	public boolean endRound(PokemonContainer victim) {
		super.endRound(victim);
		return true;
	}

	@Override
	public String getScriptName() {
		return SCRIPT_NAME;
	}
	
	@Override
	public String getMessage(Pokemon victim) {
		return victim.getName() + " is flinched!";
	}
	
	@Override
	public String getAddMessage(Pokemon pokemon) {
		return pokemon.getName() + " became flinched!";
	}

}
