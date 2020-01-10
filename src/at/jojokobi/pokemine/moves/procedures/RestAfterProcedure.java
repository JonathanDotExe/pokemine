package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class RestAfterProcedure implements MoveProcedure {

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		return map;
	}

	public static RestAfterProcedure deserialize(Map<String, Object> map) {
		RestAfterProcedure obj = new RestAfterProcedure();
		return obj;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		performer.rest(defender);
	}

	@Override
	public String toString() {
		return "RestAfterProcedure []";
	}
	
	

}
