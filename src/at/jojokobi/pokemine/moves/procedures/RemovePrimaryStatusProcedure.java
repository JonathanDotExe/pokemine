package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.status.Poison;
import at.jojokobi.pokemine.pokemon.status.PrimStatChange;

public class RemovePrimaryStatusProcedure extends SingleUserProcedure{

	private Class<?> statusChange;

	public RemovePrimaryStatusProcedure(boolean performer, Class<? extends PrimStatChange> statusChange) {
		super(performer);
		this.statusChange = statusChange;
	}

	@Override
	protected void singlePerform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon pokemon, ProcedurePokemon performer, int round) {
		pokemon.removeSecStatChange(statusChange);
	}
	
	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		save(map);
		map.put("statusChange", statusChange.getName());
		return map;
	}
	
	public static RemovePrimaryStatusProcedure deserialize (Map<String, Object> map) {
		RemovePrimaryStatusProcedure procedure = new RemovePrimaryStatusProcedure(true, Poison.class);
		procedure.load(map);
		try {
			procedure.statusChange = Class.forName(map.get("statusChange") + "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return procedure;
	}

	@Override
	public String toString() {
		return "RemovePrimaryStatusProcedure [statusChange=" + statusChange + ", isPerformer()=" + isPerformer() + "]";
	}


}
