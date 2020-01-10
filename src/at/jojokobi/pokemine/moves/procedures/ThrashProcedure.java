package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.status.Confusion;

public class ThrashProcedure implements MoveProcedure{

	

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		return map;
	}
	
	public static ThrashProcedure deserialize(Map<String, Object> map) {
		ThrashProcedure obj = new ThrashProcedure();
		return obj;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		boolean finished = (Math.random() < 0.5 && round == 2) || round > 2;
		defender.damage(performer, instance.getMove());
		if (!finished) {
			performer.repeatMove(instance, false, round, defender);
		}
		else {
			performer.addSecStatChange(new Confusion());
		}
	}

	@Override
	public String toString() {
		return "TharshProcedure []";
	}
	
	

}
