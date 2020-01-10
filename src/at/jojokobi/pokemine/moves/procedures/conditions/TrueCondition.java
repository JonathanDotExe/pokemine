package at.jojokobi.pokemine.moves.procedures.conditions;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class TrueCondition implements ProcedureCondition{

	@Override
	public Map<String, Object> serialize() {
		return new HashMap<>();
	}
	
	public static TrueCondition deserialize(Map<String, Object> map) {
		TrueCondition obj = new TrueCondition();
		return obj;
	}

	@Override
	public boolean applies(Battle battle, MoveInstance move, ProcedurePokemon performer, ProcedurePokemon defender) {
		return true;
	}

	@Override
	public String toString() {
		return "TrueCondition []";
	}
	
	

}
