package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class SwitchProcedure extends SingleUserProcedure{
	
	public SwitchProcedure(boolean performer) {
		super(performer);
	}

	@Override
	protected void singlePerform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon pokemon, ProcedurePokemon performer, int round) {
		pokemon.switchRandom(true);
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		save(map);
		return map;
	}
	
	public static SwitchProcedure deserialize (HashMap<String, Object> map) {
		SwitchProcedure procedure = new SwitchProcedure(false);
		procedure.load(map);
		return procedure;
	}

	@Override
	public String toString() {
		return "SwitchProcedure [isPerformer()=" + isPerformer() + "]";
	}
	
}
