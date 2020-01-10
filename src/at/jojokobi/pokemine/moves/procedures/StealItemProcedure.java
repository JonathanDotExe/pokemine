package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class StealItemProcedure implements MoveProcedure{

	

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		return map;
	}
	
	

	public static StealItemProcedure deserialize(Map<String, Object> map) {
		StealItemProcedure obj = new StealItemProcedure();
		return obj;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		if (performer.getItem() == null && defender.getItem() != null) {
			performer.setItem(defender.getItem());
			defender.setItem(null);
			battle.sendBattleMessage(performer.getName() + "'s stole " + defender.getName() + "'s item.");
		}
	}



	@Override
	public String toString() {
		return "StealItemProcedure []";
	}
	
	

}
