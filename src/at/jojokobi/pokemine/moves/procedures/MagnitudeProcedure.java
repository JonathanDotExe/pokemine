package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class MagnitudeProcedure implements MoveProcedure{

	

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		return map;
	}
	
	

	public static MagnitudeProcedure deserialize(Map<String, Object> map) {
		MagnitudeProcedure obj = new MagnitudeProcedure();
		return obj;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		double random = Math.random();
		int f = 0;
		
		if (random >= 0.95) {
			f = 7;
		}
		else if (random >= 0.85) {
			f = 5;
		}
		else if (random >= 0.65) {
			f = 4;
		}
		else if (random >= 0.35) {
			f = 3;
		}
		else if (random >= 0.15) {
			f = 2;
		}
		else if (random >= 0.5) {
			f = 1;
		}
		
		battle.sendBattleMessage("Magnitude " + f + "!");
		defender.damage(performer, 10 + 20 * f, instance.getMove().getType(), instance.getMove().getDamageClass());
	}



	@Override
	public String toString() {
		return "MagnitudeProcedure []";
	}
	
	

}
