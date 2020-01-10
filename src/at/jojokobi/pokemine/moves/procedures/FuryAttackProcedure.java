package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class FuryAttackProcedure implements MoveProcedure{

	

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		return map;
	}
	
	

	public static FuryAttackProcedure deserialize(Map<String, Object> map) {
		FuryAttackProcedure obj = new FuryAttackProcedure();
		return obj;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		int times = 2;
		double random = Math.random();
		if (random > 1.0 - 1/6.0) {
			times = 5;
		}
		else if (random > 1.0 - 2/6.0) {
			times = 4;
		}
		else if (random > 1.0 - 4/6.0) {
			times = 3;
		}
		for (int i = 0; i < times; i++) {
			defender.damage(performer, instance.getMove());
		}
		battle.sendBattleMessage("Hit " + times + " times!");
	}



	@Override
	public String toString() {
		return "FuryAttackProcedure []";
	}
	
	

}
