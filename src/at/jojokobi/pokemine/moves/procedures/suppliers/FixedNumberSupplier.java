package at.jojokobi.pokemine.moves.procedures.suppliers;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class FixedNumberSupplier implements NumberSupplier{

	private double number;

	public FixedNumberSupplier(double number) {
		super();
		this.number = number;
	}

	@Override
	public Number get(Battle battle, MoveInstance instance, ProcedurePokemon performer, ProcedurePokemon defender) {
		return number;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("number", number);
		return map;
	}
	
	public static FixedNumberSupplier deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new FixedNumberSupplier(tMap.getDouble("number"));
	}

	@Override
	public String toString() {
		return "FixedNumberSupplier [number=" + number + "]";
	}

}
