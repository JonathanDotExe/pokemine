package at.jojokobi.pokemine.moves.procedures.conditions;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public interface ProcedureCondition extends ConfigurationSerializable{

	public boolean applies (Battle battle, MoveInstance move, ProcedurePokemon performer, ProcedurePokemon defender);
	
}
