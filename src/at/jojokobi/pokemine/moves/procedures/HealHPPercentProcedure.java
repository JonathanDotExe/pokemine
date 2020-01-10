package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.suppliers.FixedNumberSupplier;
import at.jojokobi.pokemine.moves.procedures.suppliers.NumberSupplier;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class HealHPPercentProcedure implements MoveProcedure{

	private NumberSupplier hp;
	private boolean performer;

	public HealHPPercentProcedure(boolean performer, NumberSupplier hp) {
		this.performer = performer;
		this.hp = hp;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("hp", hp);
		return map;
	}
	
	public static HealHPPercentProcedure deserialize(Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		HealHPPercentProcedure obj = new HealHPPercentProcedure(tMap.getBoolean("performer"), tMap.get("hp", NumberSupplier.class, new FixedNumberSupplier(0)));
		return obj;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		ProcedurePokemon pokemon = this.performer ? performer : defender;
		pokemon.heal(hp.get(battle, instance, performer, defender).intValue());
	}

	@Override
	public String toString() {
		return "HealHPPercentProcedure [hp=" + hp + ", performer=" + performer + "]";
	}

}
