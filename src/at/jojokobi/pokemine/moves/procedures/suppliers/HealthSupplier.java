package at.jojokobi.pokemine.moves.procedures.suppliers;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class HealthSupplier extends SingleUserSupplier{

	public HealthSupplier(boolean performer) {
		super(performer);
	}

	@Override
	public Number get(Battle battle, MoveInstance instance, ProcedurePokemon pokemon) {
		return pokemon.getHealth();
	}
	
	@Override
	public Map<String, Object> serialize() {
		return save (new HashMap<>());
	}

	public static HealthSupplier deserialize (Map<String, Object> map) {
		return (HealthSupplier) new HealthSupplier(false).load(map);
	}

	@Override
	public String toString() {
		return "HealthSupplier [isPerformer()=" + isPerformer() + "]";
	}

}
