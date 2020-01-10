package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class PriorityProcedure implements MoveProcedure{

	private float priority;

	public PriorityProcedure(float priority) {
		super();
		this.priority = priority;
	}

	

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("priority", priority);
		return map;
	}
	
	

	public static PriorityProcedure deserialize(Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		PriorityProcedure obj = new PriorityProcedure(tMap.getFloat("priority"));
		return obj;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		init.setPriority(priority);
	}



	@Override
	public String toString() {
		return "PriorityProcedure [priority=" + priority + "]";
	}
	
	
	
}
