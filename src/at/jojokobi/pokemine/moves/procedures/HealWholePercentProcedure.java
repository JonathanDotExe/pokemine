package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class HealWholePercentProcedure extends SingleUserProcedure {

	private float percent;
	
	public HealWholePercentProcedure(boolean performer, float percent) {
		super(performer);
		this.percent = percent;
	}

	

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("percent", percent);
		return map;
	}
	
	

	public static HealWholePercentProcedure deserialize(Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		HealWholePercentProcedure obj = new HealWholePercentProcedure(false, tMap.getFloat("percent"));
		obj.load(map);
		return obj;
	}

	@Override
	protected void singlePerform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon pokemon,
			ProcedurePokemon performer, int round) {
		pokemon.heal(Math.round(pokemon.getMaxHealth() * percent));
	}



	@Override
	public String toString() {
		return "HealWholePercentProcedure [percent=" + percent + ", isPerformer()=" + isPerformer() + "]";
	}

}
