package at.jojokobi.pokemine.moves.procedures.conditions;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.PokemonType;

public class HasTypeCondition extends SingleUserCondition{

	private PokemonType type;
	
	public HasTypeCondition(boolean performer, PokemonType type) {
		super(performer);
		this.type = type;
	}

	@Override
	public boolean applies(Battle battle, MoveInstance move, ProcedurePokemon pokemon) {
		return pokemon.hasType(type);
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("type", type + "");
		return save(map);
	}
	
	public static HasTypeCondition deserialize (Map<String, Object> map) {
		HasTypeCondition cond = new HasTypeCondition(false, new TypedMap(map).getEnum("type", PokemonType.class, PokemonType.NORMAL));
		cond.load(map);
		return cond;
	}

	@Override
	public String toString() {
		return "HasTypeCondition [type=" + type + ", isPerformer()=" + isPerformer() + "]";
	}
	
	

}
