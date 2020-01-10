package at.jojokobi.pokemine.moves.procedures;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.status.Poison;
import at.jojokobi.pokemine.pokemon.status.PrimStatChange;

public class AddPrimaryStatusProcedure extends SingleUserProcedure {
	
	private Class<?> statusChange;

	public AddPrimaryStatusProcedure(boolean performer, Class<? extends PrimStatChange> statusChange) {
		super(performer);
		this.statusChange = statusChange;
	}

	@Override
	protected void singlePerform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon pokemon, ProcedurePokemon performer, int round) {
		try {
			Object stat = statusChange.getConstructor().newInstance();
			if (stat instanceof PrimStatChange) {
				pokemon.addPrimStatChange((PrimStatChange) stat);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
	}
	
	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		save(map);
		map.put("statusChange", statusChange.getName());
		return map;
	}
	
	public static AddPrimaryStatusProcedure deserialize (Map<String, Object> map) {
		AddPrimaryStatusProcedure procedure = new AddPrimaryStatusProcedure(true, Poison.class);
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
		return "AddPrimaryStatusProcedure [statusChange=" + statusChange + ", isPerformer()=" + isPerformer() + "]";
	}

	
	
	

}
