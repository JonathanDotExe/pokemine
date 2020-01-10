package at.jojokobi.pokemine.moves.procedures.suppliers;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class HealthPercentSupplier extends SingleUserSupplier{

	public HealthPercentSupplier(boolean performer) {
		super(performer);
	}

	@Override
	public Number get(Battle battle, MoveInstance instance, ProcedurePokemon pokemon) {
		return pokemon.getHealthInPercent();
	}

	@Override
	public Map<String, Object> serialize() {
		return save (new HashMap<>());
	}

	public static HealthPercentSupplier deserialize (Map<String, Object> map) {
		return (HealthPercentSupplier) new HealthPercentSupplier(false).load(map);
	}
	
	
}
