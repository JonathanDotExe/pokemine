package at.jojokobi.pokemine.moves.procedures.suppliers;

import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public abstract class SingleUserSupplier implements NumberSupplier{
	
	private boolean performer;

	public SingleUserSupplier(boolean performer) {
		super();
		this.performer = performer;
	}
	
	public abstract Number get(Battle battle, MoveInstance instance, ProcedurePokemon pokemon);
	
	public Map<String, Object> save (Map<String, Object> map) {
		map.put("performer", performer);
		return map;
	}
	
	public SingleUserSupplier load (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		performer = tMap.getBoolean("performer");
		return this;
	}

	@Override
	public Number get(Battle battle, MoveInstance instance, ProcedurePokemon performer, ProcedurePokemon defender) {
		return get(battle, instance, this.performer ? performer : defender);
	}

	public boolean isPerformer() {
		return performer;
	}

}
