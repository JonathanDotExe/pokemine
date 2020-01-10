package at.jojokobi.pokemine.moves.procedures.conditions;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class DamageTakenCondition extends SingleUserCondition{

	public DamageTakenCondition(boolean performer) {
		super(performer);
	}

	@Override
	public boolean applies(Battle battle, MoveInstance move, ProcedurePokemon pokemon) {
		return pokemon.getLastDamage() != null;
	}

	@Override
	public Map<String, Object> serialize() {
		return save(new HashMap<>());
	}
	
	public static DamageTakenCondition deserialize (Map<String, Object> map) {
		return (DamageTakenCondition) new DamageTakenCondition(false).load(map);
	}

	@Override
	public String toString() {
		return "DamageTakenCondition [isPerformer()=" + isPerformer() + "]";
	}
	
	

}
