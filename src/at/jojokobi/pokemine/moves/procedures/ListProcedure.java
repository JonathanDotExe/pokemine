package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class ListProcedure implements MoveProcedure{

	private List<MoveProcedure> procedures;

	public ListProcedure(List<MoveProcedure> procedures) {
		super();
		this.procedures = procedures;
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("procedures", procedures);
		return map;
	}
	
	public static ListProcedure deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new ListProcedure(tMap.getList("procedures", MoveProcedure.class));
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer, ProcedurePokemon defender,
			int round) {
		procedures.forEach(p -> p.perform(battle, instance, init, performer, defender, round));
	}

	@Override
	public String toString() {
		return "ListProcedure [procedures=" + procedures + "]";
	}

	public List<MoveProcedure> getProcedures() {
		return procedures;
	}

	public void setProcedures(List<MoveProcedure> procedures) {
		this.procedures = procedures;
	}
	
//	@Override
//	public void preExecute(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
//			ProcedurePokemon defender, int round) {
//		procedures.forEach(p -> p.preExecute(battle, instance, init, performer, defender, round));
//	}
//	
//	@Override
//	public void initialize(Battle battle, MoveInitializer init, MoveInstance instance, ProcedurePokemon performer,
//			ProcedurePokemon defender, int round) {
//		procedures.forEach(p -> p.initialize(battle, init, instance, performer, defender, round));
//	}

}
