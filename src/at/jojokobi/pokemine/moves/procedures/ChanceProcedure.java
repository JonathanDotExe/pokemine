package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class ChanceProcedure extends DecoratorProcedure{

	private double chance = 0;

	public ChanceProcedure(MoveProcedure decorating, double chance) {
		super(decorating);
		this.chance = chance;
	}
	
	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer, ProcedurePokemon defender,
			int round) {
		if (Math.random() < chance) {
			super.perform(battle, instance, init, performer, defender, round);
		}
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object> ();
		save(map);
		map.put("chance", chance);
		return map;
	}
	
	public static ChanceProcedure deserialize (Map<String, Object> map) {
		ChanceProcedure procedure = new ChanceProcedure(null, 0);
		procedure.load(map);
		procedure.chance = new TypedMap(map).getDouble("chance");
		return procedure;
	}

	@Override
	public String toString() {
		return "ChanceProcedure [chance=" + chance + ", getDecorating()=" + getDecorating() + "]";
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}

}
