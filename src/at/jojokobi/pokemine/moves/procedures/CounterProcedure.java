package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.DamageClass;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class CounterProcedure implements MoveProcedure {
	
	private DamageClass damageClass;

	public CounterProcedure(DamageClass damageClass) {
		super();
		this.damageClass = damageClass;
	}

	

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("damageClass", damageClass + "");
		return map;
	}
	
	

	public static CounterProcedure deserialize(Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		CounterProcedure obj = new CounterProcedure(tMap.getEnum("damageClass", DamageClass.class, DamageClass.STATUS));
		return obj;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		if (damageClass == performer.getLastDamage().getDamageClass()) {
			defender.damageHP(performer, performer.getLastDamage().getDamage() * 2, instance.getMove().getDamageClass());
		}
		else {
			battle.sendBattleMessage("But it failed!");
		}
	}



	@Override
	public String toString() {
		return "CounterProcedure [damageClass=" + damageClass + "]";
	}
	
	

}
