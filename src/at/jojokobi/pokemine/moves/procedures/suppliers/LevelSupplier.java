package at.jojokobi.pokemine.moves.procedures.suppliers;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class LevelSupplier extends SingleUserSupplier{

	public LevelSupplier(boolean performer) {
		super(performer);
	}

	@Override
	public Number get(Battle battle, MoveInstance instance, ProcedurePokemon pokemon) {
		return pokemon.getLevel();
	}
	
	@Override
	public Map<String, Object> serialize() {
		return save (new HashMap<>());
	}

	public static LevelSupplier deserialize (Map<String, Object> map) {
		return (LevelSupplier) new LevelSupplier(false).load(map);
	}

	@Override
	public String toString() {
		return "LevelSupplier [isPerformer()=" + isPerformer() + "]";
	}

}
