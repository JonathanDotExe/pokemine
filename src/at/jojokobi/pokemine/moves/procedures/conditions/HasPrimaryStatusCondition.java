package at.jojokobi.pokemine.moves.procedures.conditions;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.status.Poison;
import at.jojokobi.pokemine.pokemon.status.PrimStatChange;

public class HasPrimaryStatusCondition extends SingleUserCondition{

	private Class<?> statusChange;

	public HasPrimaryStatusCondition(boolean performer, Class<? extends PrimStatChange> statChange) {
		super(performer);
		this.statusChange = statChange;
	}

	@Override
	public boolean applies(Battle battle, MoveInstance move, ProcedurePokemon pokemon) {
		return pokemon.hasPrimStatChange(statusChange);
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("statusChange", statusChange.getName());
		return save(map);
	}
	
	public static HasPrimaryStatusCondition deserialize (Map<String, Object> map) {
		HasPrimaryStatusCondition cond = new HasPrimaryStatusCondition(false, Poison.class);
		cond.load(map);
		try {
			cond.statusChange = Class.forName(map.get("statusChange") + "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return cond;
	}

	@Override
	public String toString() {
		return "HasPrimaryStatusCondition [statusChange=" + statusChange + ", isPerformer()=" + isPerformer() + "]";
	}
	
}
