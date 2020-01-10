package at.jojokobi.pokemine.moves.movescriptold;

import at.jojokobi.kiwiscript.Value;
import at.jojokobi.kiwiscript.VariableHandler;
import at.jojokobi.kiwiscript.commands.FunctionCommand;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.MoveAction;
import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.moves.MoveHandler;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.pokemon.Pokemon;
@Deprecated
public class RepeatCommand extends FunctionCommand{
	
	public static final String NAME = "repeat";
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Object execute(Value[] arguments, VariableHandler handler) {
		boolean takePP = true;
		if (arguments.length > 0) {
			takePP = Boolean.parseBoolean(arguments[0].getValue().toString());
		}
		Pokemon performer = (Pokemon) handler.getVariable(MoveHandler.PERFORMER).getValue();
		Battle battle = (Battle) handler.getVariable(MoveHandler.BATTLE).getValue();
		MoveInstance move = (MoveInstance) handler.getVariable(MoveHandler.MOVE).getValue();
		PokemonContainer defender = (PokemonContainer) handler.getVariable(MoveHandler.DEFENDER_CONTAINER).getValue();
		int round = Integer.parseInt(handler.getVariable(MoveHandler.ROUND).getValue().toString());
		performer.setNextAction(new MoveAction(battle, move, performer, defender, round + 1, takePP));
		return null;
	}

	

}
