package at.jojokobi.pokemine.battle.effects;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class DestinyBond extends BattleEffect{

	
	public static final String SCRIPT_NAME = "destiny_bond";
	
	private ProcedurePokemon owner;
	
	public DestinyBond(Battle battle, ProcedurePokemon owner) {
		super(battle);
		this.owner = owner;
	}

	@Override
	public void startRound() {
		
	}

	@Override
	public void startTurn(PokemonContainer pokemon) {
		if (owner.representsPokemon(pokemon.getPokemon())) {
			getBattle().removeBattleEffect(this);
		}
	}

	@Override
	public void endTurn(PokemonContainer pokemon) {
		if (owner.getHealth() <= 0) {
			pokemon.getPokemon().setHealth(0);
			getBattle().sendBattleMessage(owner.getName() + " took " + pokemon.getPokemon().getName() + " with it!");
			getBattle().removeBattleEffect(this);
		}
	}

	@Override
	public void endRound() {
		
	}

	@Override
	public String getScriptName() {
		return SCRIPT_NAME;
	}

	@Override
	public String getAddMessage() {
		return owner.getName() + " is trying to take it's opponent with it!";
	}

}
