package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.PokemonStatValue;

public class StoredPowerProcedure implements MoveProcedure{

	

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		return map;
	}
	
	

	public static StoredPowerProcedure deserialize(Map<String, Object> map) {
		StoredPowerProcedure obj = new StoredPowerProcedure();
		return obj;
	}
	
	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		int stats = 0;
		for (PokemonStatValue stat : PokemonStatValue.values()) {
			stats += Math.max (0, stat.getValue(performer));
		}
		defender.damage(performer, 20 + 20 * stats, instance.getMove().getType(), instance.getMove().getDamageClass());
	}



	@Override
	public String toString() {
		return "StoredPowerProcedure []";
	}
	
	

}
