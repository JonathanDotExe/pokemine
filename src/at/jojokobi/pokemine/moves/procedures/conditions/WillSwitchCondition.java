package at.jojokobi.pokemine.moves.procedures.conditions;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class WillSwitchCondition extends SingleUserCondition{

	public WillSwitchCondition(boolean performer) {
		super(performer);
	}

	@Override
	public boolean applies(Battle battle, MoveInstance move, ProcedurePokemon pokemon) {
		return pokemon.willSwitch();
	}
	
	@Override
	public Map<String, Object> serialize() {
		return save(new HashMap<>());
	}
	
	public static WillSwitchCondition deserialize (Map<String, Object> map) {
		return (WillSwitchCondition) new WillSwitchCondition(false).load(map);
	}

	@Override
	public String toString() {
		return "WillSwitchCondition [isPerformer()=" + isPerformer() + "]";
	}
	

}
