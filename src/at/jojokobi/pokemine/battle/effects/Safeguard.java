package at.jojokobi.pokemine.battle.effects;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedureTrainer;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.status.Confusion;
import at.jojokobi.pokemine.pokemon.status.SecStatChange;

public class Safeguard extends BattleEffect{

	
	public static final String SCRIPT_NAME = "safeguard";
	
	private ProcedureTrainer owner;
	
	public Safeguard(Battle battle, ProcedureTrainer owner) {
		super(battle);
		this.owner = owner;
		setHasDuration(true);
		setDuration(5);
		setEndMessage(owner.getName() + "'s Team is no longer protected!");
	}

	@Override
	public void startRound() {
		
	}

	@Override
	public void startTurn(PokemonContainer pokemon) {
		
	}

	@Override
	public void endTurn(PokemonContainer pokemon) {
		for (PokemonContainer pokeCont : getBattle().getPokemonContainers()) {
			Pokemon poke = pokeCont.getPokemon();
			if (owner.ownsPokemon(poke)) {
				if (poke.getPrimStatChange() != null) {
					getBattle().sendBattleMessage("Safeguard safed " + poke.getName() + " from the " + poke.getPrimStatChange().getScriptName() + "!");
					poke.setPrimStatChange(null);
				}
				for (SecStatChange stat : pokemon.getStatChanges()) {
					if (stat instanceof Confusion) {
						pokeCont.removeSecStatChange(stat);
						getBattle().sendBattleMessage("Safeguard safed " + poke.getName() + " from the confusion!");
					}
				}
			}
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
		return owner.getName() + "'s team is no protected from status changes!";
	}

}
