package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.PokemonStatValue;

public class SetValueProcedure extends SingleUserProcedure {
	
	private PokemonStatValue stat;
	private int value;

	public SetValueProcedure(boolean performer, PokemonStatValue stat, int value) {
		super(performer);
		this.stat = stat;
		this.value = value;
	}

	@Override
	protected void singlePerform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon pokemon, ProcedurePokemon performer, int round) {
		stat.setValue(pokemon, value);
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		save(map);
		map.put("stat", stat + "");
		map.put("value", value);
		return map;
	}
	
	public static SetValueProcedure deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		SetValueProcedure procedure = new SetValueProcedure(false, tMap.getEnum("stat", PokemonStatValue.class, PokemonStatValue.ATTACK), tMap.getInt("value"));
		procedure.load(map);
		return procedure;
	}

	@Override
	public String toString() {
		return "SetValueProcedure [stat=" + stat + ", value=" + value + ", isPerformer()=" + isPerformer() + "]";
	}

}
