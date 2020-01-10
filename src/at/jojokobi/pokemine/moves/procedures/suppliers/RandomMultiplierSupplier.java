package at.jojokobi.pokemine.moves.procedures.suppliers;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class RandomMultiplierSupplier implements NumberSupplier{

	private NumberSupplier decorating;
	private double min;
	private double max;


	public RandomMultiplierSupplier(NumberSupplier decorating, double min, double max) {
		super();
		this.decorating = decorating;
		this.min = min;
		this.max = max;
	}

	@Override
	public Number get(Battle battle, MoveInstance instance, ProcedurePokemon performer, ProcedurePokemon defender) {
		double multiplier = min + Math.random() * (max - min);
		return (multiplier * decorating.get(battle, instance, performer, defender).doubleValue());
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("decorating", decorating);
		map.put("min", min);
		map.put("max", max);
		return map;
	}
	
	public static RandomMultiplierSupplier deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new RandomMultiplierSupplier(tMap.get("decoration", NumberSupplier.class, new FixedNumberSupplier(0)), tMap.getDouble("min"), tMap.getDouble("max"));
	}

	@Override
	public String toString() {
		return "RandomMultiplierSupplier [decorating=" + decorating + ", min=" + min + ", max=" + max + "]";
	}

}
