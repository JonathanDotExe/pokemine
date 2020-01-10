package at.jojokobi.pokemine.moves.procedures;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.status.Confusion;
import at.jojokobi.pokemine.pokemon.status.SecStatChange;

public class AddSecondaryStatusProcedure extends SingleUserProcedure {
	
	private Class<?> statusChange;

	public AddSecondaryStatusProcedure (boolean performer, Class<? extends SecStatChange> statusChange) {
		super(performer);
		this.statusChange = statusChange;
	}

	@Override
	protected void singlePerform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon pokemon, ProcedurePokemon performer, int round) {
		Object stat = null;
		try {
			try {
				stat = statusChange.getConstructor().newInstance();
			} catch (NoSuchMethodException e) {
				stat = statusChange.getConstructor(ProcedurePokemon.class).newInstance(performer);
			}
		}catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		if (stat instanceof SecStatChange) {
			pokemon.addSecStatChange((SecStatChange) stat);
		}
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		save(map);
		map.put("statusChange", statusChange.getName());
		return map;
	}
	
	public static AddSecondaryStatusProcedure deserialize (Map<String, Object> map) {
		AddSecondaryStatusProcedure procedure = new AddSecondaryStatusProcedure(true, Confusion.class);
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
		return "AddSecondaryStatusProcedure [statusChange=" + statusChange + "]";
	}
	

}
