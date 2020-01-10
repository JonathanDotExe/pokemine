package at.jojokobi.pokemine.moves.procedures.conditions;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.suppliers.FixedNumberSupplier;
import at.jojokobi.pokemine.moves.procedures.suppliers.NumberSupplier;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class NumberSmallerThanCondition implements ProcedureCondition {

	private NumberSupplier number1;
	private NumberSupplier number2;
	
	public NumberSmallerThanCondition(NumberSupplier number1, NumberSupplier number2) {
		super();
		this.number1 = number1;
		this.number2 = number2;
	}

	@Override
	public boolean applies(Battle battle, MoveInstance move, ProcedurePokemon performer, ProcedurePokemon defender) {
		return number1.get(battle, move, performer, defender).doubleValue() < number2.get(battle, move, performer, defender).doubleValue();
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("number1", number1);
		map.put("number2", number2);
		return map;
	}
	
	public static NumberSmallerThanCondition deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new NumberSmallerThanCondition(tMap.get("number1", NumberSupplier.class, new FixedNumberSupplier(0)), tMap.get("number2", NumberSupplier.class, new FixedNumberSupplier(0)));
	}

	@Override
	public String toString() {
		return "NumberSmallerThanCondition [number1=" + number1 + ", number2=" + number2 + "]";
	}

}
