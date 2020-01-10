package at.jojokobi.pokemine.moves;

import java.util.function.BiConsumer;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.procedures.DecoratorProcedure;
import at.jojokobi.pokemine.moves.procedures.MoveProcedure;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class FunctionProcedure extends DecoratorProcedure {
	
	/**
	 * Function arguments: Pokemon performer, Pokemon defender
	 */
	private BiConsumer<ProcedurePokemon, ProcedurePokemon> function;

	public FunctionProcedure(MoveProcedure decorating, BiConsumer<ProcedurePokemon, ProcedurePokemon> function) {
		super(decorating);
		this.function = function;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, ProcedurePokemon performer, ProcedurePokemon defender, int round) {
		super.perform(battle, instance, performer, defender, round);
		function.accept(performer, defender);
	}

}
