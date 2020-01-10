package at.jojokobi.pokemine.moves.procedures.suppliers;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class FlialDamageSupplier implements NumberSupplier{

	@Override
	public Map<String, Object> serialize() {
		return new HashMap<>();
	}
	
	public static FlialDamageSupplier deserialize (Map<String, Object> map) {
		return new FlialDamageSupplier();
	}

	@Override
	public Number get(Battle battle, MoveInstance instance, ProcedurePokemon performer, ProcedurePokemon defender) {
		int power = 20;
		float health = performer.getHealthInPercent();
		if (health  < 0.0417f) {
			power = 200;
		}
		else if (health  < 0.1042f) {
			power = 150;
		}
		else if (health  < 0.2083f) {
			power = 100;
		}
		else if (health  < 0.3542f) {
			power = 80;
		}
		else if (health  < 0.6875f) {
			power = 40;
		}
		return power;
	}

	@Override
	public String toString() {
		return "FlialDamageSupplier [serialize()=" + serialize() + "]";
	}	

}
