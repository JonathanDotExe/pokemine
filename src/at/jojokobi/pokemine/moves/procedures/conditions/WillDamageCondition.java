package at.jojokobi.pokemine.moves.procedures.conditions;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class WillDamageCondition extends SingleUserCondition{

	public WillDamageCondition(boolean performer) {
		super(performer);
	}

	@Override
	public boolean applies(Battle battle, MoveInstance move, ProcedurePokemon pokemon) {
		return pokemon.willDamage();
	}
	
	@Override
	public Map<String, Object> serialize() {
		return save(new HashMap<>());
	}
	
	public static WillDamageCondition deserialize (Map<String, Object> map) {
		return (WillDamageCondition) new WillDamageCondition(false).load(map);
	}

	@Override
	public String toString() {
		return "WillDamageCondition [isPerformer()=" + isPerformer() + "]";
	}

}
