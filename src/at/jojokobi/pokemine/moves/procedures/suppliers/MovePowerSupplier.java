package at.jojokobi.pokemine.moves.procedures.suppliers;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class MovePowerSupplier implements NumberSupplier{

	@Override
	public Integer get(Battle battle, MoveInstance instance, ProcedurePokemon performer, ProcedurePokemon defender) {
		return instance.getMove().getPower();
	}

	@Override
	public Map<String, Object> serialize() {
		return new HashMap <>();
	}
	
	public static MovePowerSupplier deserialize (Map<String, Object> map) {
		return new MovePowerSupplier();
	}

	@Override
	public String toString() {
		return "MovePowerSupplier []";
	}
	
	

}
