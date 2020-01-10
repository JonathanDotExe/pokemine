package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.PokemonStatValue;

public class SwapStatusValueProcedure implements MoveProcedure{

	private PokemonStatValue statusValue;

	public SwapStatusValueProcedure(PokemonStatValue statusValue) {
		super();
		this.statusValue = statusValue;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("statusValue", statusValue + "");
		return map;
	}

	public static SwapStatusValueProcedure deserialize(Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		SwapStatusValueProcedure obj = new SwapStatusValueProcedure(tMap.getEnum("statusValue", PokemonStatValue.class, PokemonStatValue.ATTACK));
		return obj;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		int performerValue = statusValue.getValue(performer);
		int defenderValue = statusValue.getValue(defender);
		statusValue.setValue(performer, defenderValue);
		statusValue.setValue(defender, performerValue);
		battle.sendBattleMessage(performer.getName() + " and " + defender.getName() + " swapped their " + statusValue + " values!");
	}

	@Override
	public String toString() {
		return "SwapStatusValueProcedure [statusValue=" + statusValue + "]";
	}
	
	
	
}
