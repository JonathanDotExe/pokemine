package at.jojokobi.pokemine.moves.procedures.suppliers;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public interface NumberSupplier extends ConfigurationSerializable {

	public Number get (Battle battle, MoveInstance instance, ProcedurePokemon performer, ProcedurePokemon defender);
	
}
