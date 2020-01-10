package at.jojokobi.pokemine.moves.procedures.conditions;

import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public abstract class SingleUserCondition implements ProcedureCondition {
	
	private boolean performer;
	
	public SingleUserCondition(boolean performer) {
		super();
		this.performer = performer;
	}

	public abstract boolean applies(Battle battle, MoveInstance move, ProcedurePokemon pokemon);

	@Override
	public boolean applies(Battle battle, MoveInstance move, ProcedurePokemon performer, ProcedurePokemon defender) {
		return applies(battle, move, this.performer ? performer : defender);
	}
	
	public Map<String, Object> save (Map<String, Object> map) {
		map.put("performer", performer);
		return map;
	}
	
	public SingleUserCondition load (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		performer = tMap.getBoolean("performer");
		return this;
	}

	public boolean isPerformer() {
		return performer;
	}
	
	

}
