package at.jojokobi.pokemine.moves.procedures.suppliers;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class MultiplierSupplier implements NumberSupplier{

	private double multiplier;
	private NumberSupplier decorating;

	public MultiplierSupplier(double multiplier, NumberSupplier decorating) {
		super();
		this.multiplier = multiplier;
		this.decorating = decorating;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("multiplier", multiplier);
		map.put("decorating", decorating);
		return map;
	}

	public static MultiplierSupplier deserialize(Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		MultiplierSupplier obj = new MultiplierSupplier(tMap.getDouble("multiplier"), tMap.get("decorating", NumberSupplier.class, new FixedNumberSupplier(0)));
		return obj;
	}

	@Override
	public Number get(Battle battle, MoveInstance instance, ProcedurePokemon performer, ProcedurePokemon defender) {
		return multiplier * decorating.get(battle, instance, performer, defender).doubleValue();
	}

}
