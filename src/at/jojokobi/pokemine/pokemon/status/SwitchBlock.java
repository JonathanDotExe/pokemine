package at.jojokobi.pokemine.pokemon.status;

import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class SwitchBlock extends SecStatChange{

	public static final String SCRIPT_NAME = "switch_block";
	
	public SwitchBlock() {
		super();
		
	}

	@Override
	public boolean canSwitch(PokemonContainer victim) {
		return false;
	}

	@Override
	public String getScriptName() {
		return SCRIPT_NAME;
	}
	
	@Override
	public String getMessage(Pokemon victim) {
		return "";
	}
	
	@Override
	public String getAddMessage(Pokemon pokemon) {
		return pokemon.getName() + " can't escape anymore!";
	}

}
