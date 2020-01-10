package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class DoNothingProcedure implements MoveProcedure {
	
	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon ProcedurePokemon, int round) {
	}

	@Override
	public Map<String, Object> serialize() {
		return new HashMap<String, Object> ();
	}

	public static DoNothingProcedure deserialize () {
		return new DoNothingProcedure();
	}

	@Override
	public String toString() {
		return "DoNothingProcedure []";
	}

}
