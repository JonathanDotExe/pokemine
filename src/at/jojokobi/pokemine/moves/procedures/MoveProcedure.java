package at.jojokobi.pokemine.moves.procedures;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public interface MoveProcedure extends ConfigurationSerializable{
	
//	public default void initialize (Battle battle, MoveInitializer init, MoveInstance instance, ProcedurePokemon performer,  ProcedurePokemon defender, int round) {
//		
//	}
//	
//	public default void preExecute (Battle battle, MoveInstance instance, MoveInitializer init,  ProcedurePokemon performer,  ProcedurePokemon defender, int round) {
//		
//	}
	
	public void perform (Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer, ProcedurePokemon  defender, int round);

}
