package at.jojokobi.pokemine.battle;

import at.jojokobi.pokemine.pokemon.Pokemon;

public class SwitchAction extends BattleAction{
	
	private Pokemon newPokemon;
	
	public SwitchAction(Battle battle, PokemonContainer oldPokemon, Pokemon newPokemon) {
		super(battle, oldPokemon, oldPokemon);
		this.newPokemon = newPokemon;
		setPriority(5);
	}

	@Override
	public String[] startPerform() {
		String[] text = {getPerformer().getPokemon().getName() + " returned!", newPokemon.getOwner().getName() + " sent out " + newPokemon.getName()};
		if (newPokemon.getHealth() > 0) {
			getBattle().switchPokemon(getPerformer(), newPokemon, false);
		}
		else {
			text = new String[] {getPerformer().getPokemon().getName() + " couldn't be switched!"};
		}
		return text;
	}

	@Override
	public String[] tickPerform() {
		return new String[] {};
	}

	@Override
	public String[] stopPerform() {
		return new String[] {};
	}
	
	@Override
	public void init() {
		
	}

}
