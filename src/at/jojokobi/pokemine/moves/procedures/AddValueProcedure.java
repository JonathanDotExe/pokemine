package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.PokemonStatValue;

public class AddValueProcedure extends SingleUserProcedure {
	
	private PokemonStatValue stat;
	private int value;

	public AddValueProcedure(boolean performer, PokemonStatValue stat, int value) {
		super(performer);
		this.stat = stat;
		this.value = value;
	}

	@Override
	public void singlePerform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon pokemon, ProcedurePokemon performer, int round) {
		stat.addValue(pokemon, value);
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		save(map);
		map.put("stat", stat + "");
		map.put("value", value);
		return map;
	}
	
	public static AddValueProcedure deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		AddValueProcedure procedure = new AddValueProcedure(false, tMap.getEnum("stat", PokemonStatValue.class, PokemonStatValue.ATTACK), tMap.getInt("value"));
		procedure.load(map);
		return procedure;
	}

	@Override
	public String toString() {
		return "AddValueProcedure [stat=" + stat + ", value=" + value + ", isPerformer()=" + isPerformer() + "]";
	}


	
	

}
