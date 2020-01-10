package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.conditions.ProcedureCondition;
import at.jojokobi.pokemine.moves.procedures.conditions.TrueCondition;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class ConditionalProcedure implements MoveProcedure {

	private ProcedureCondition condition;
	private MoveProcedure ifProcedure;
	private MoveProcedure elseProcedure;


	public ConditionalProcedure(ProcedureCondition condition, MoveProcedure ifProcedure, MoveProcedure elseProcedure) {
		super();
		this.condition = condition;
		this.ifProcedure = ifProcedure;
		this.elseProcedure = elseProcedure;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		if (condition.applies(battle, instance, performer, defender)) {
			if (ifProcedure != null) {
				ifProcedure.perform(battle, instance, init, performer, defender, round);
			}
		}
		else if (elseProcedure != null) {
			ifProcedure.perform(battle, instance, init, performer, defender, round);
		}
	}

	

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("condition", condition);
		map.put("ifProcedure", ifProcedure);
		map.put("elseProcedure", elseProcedure);
		return map;
	}
	
	

	public static ConditionalProcedure deserialize(Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		ConditionalProcedure obj = new ConditionalProcedure(tMap.get("condition", ProcedureCondition.class, new TrueCondition()), tMap.get("ifProcedure", MoveProcedure.class, null), tMap.get("elseProcedure", MoveProcedure.class, null));
		return obj;
	}

	@Override
	public String toString() {
		return "ConditionalProcedure [condition=" + condition + ", ifProcedure=" + ifProcedure + ", elseProcedure="
				+ elseProcedure + "]";
	}

	public ProcedureCondition getCondition() {
		return condition;
	}

	public void setCondition(ProcedureCondition condition) {
		this.condition = condition;
	}

	public MoveProcedure getIfProcedure() {
		return ifProcedure;
	}

	public void setIfProcedure(MoveProcedure ifProcedure) {
		this.ifProcedure = ifProcedure;
	}

	public MoveProcedure getElseProcedure() {
		return elseProcedure;
	}

	public void setElseProcedure(MoveProcedure elseProcedure) {
		this.elseProcedure = elseProcedure;
	}

}
