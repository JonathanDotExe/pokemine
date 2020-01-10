package at.jojokobi.pokemine.moves.procedures.suppliers;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.conditions.ProcedureCondition;
import at.jojokobi.pokemine.moves.procedures.conditions.TrueCondition;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class ConditionalNumberSupplier implements NumberSupplier{
	
	private ProcedureCondition condition;
	private NumberSupplier number1;
	private NumberSupplier number2;

	public ConditionalNumberSupplier(ProcedureCondition condition, NumberSupplier number1, NumberSupplier number2) {
		super();
		this.condition = condition;
		this.number1 = number1;
		this.number2 = number2;
	}

	@Override
	public Number get(Battle battle, MoveInstance instance, ProcedurePokemon performer, ProcedurePokemon defender) {
		return condition.applies(battle, instance, performer, defender) ? number1.get(battle, instance, performer, defender) : number2.get(battle, instance, performer, defender);
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put("condition", condition);
		map.put("number1", number1);
		map.put("number2", number2);
		return map;
	}
	
	public static ConditionalNumberSupplier deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new ConditionalNumberSupplier(tMap.get("condition", ProcedureCondition.class, new TrueCondition()), tMap.get("number1", NumberSupplier.class, new FixedNumberSupplier(0)), tMap.get("number2", NumberSupplier.class, new FixedNumberSupplier(0)));
	}

	@Override
	public String toString() {
		return "ConditionalNumberSupplier [condition=" + condition + ", number1=" + number1 + ", number2=" + number2
				+ "]";
	}

}
