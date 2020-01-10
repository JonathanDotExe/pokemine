package at.jojokobi.pokemine.moves.movescriptold;

import static at.jojokobi.pokemine.moves.MoveHandler.*;

import at.jojokobi.kiwiscript.NumberUtils;
import at.jojokobi.kiwiscript.Value;
import at.jojokobi.kiwiscript.VariableHandler;
import at.jojokobi.kiwiscript.commands.FunctionCommand;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.status.Confusion;
import at.jojokobi.pokemine.pokemon.status.Drowsiness;
import at.jojokobi.pokemine.pokemon.status.Flinched;
import at.jojokobi.pokemine.pokemon.status.Wrapped;
@Deprecated
public class AddStatusCommand extends FunctionCommand{

	public static final String NAME = "addStatus";
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Object execute(Value[] args, VariableHandler handler) {
		if (args[0].getValue() instanceof Pokemon && NumberUtils.isInteger(args[1].getValue().toString())) {
			Pokemon pokemon = (Pokemon) args[0].getValue();
			Battle battle = (Battle) handler.getVariable(BATTLE).getValue();
			int status = Integer.parseInt(args[1].getValue().toString());
			float chance = 1;
			if (args.length > 2 && NumberUtils.isDouble(args[2].getValue().toString())) {
				chance = Float.parseFloat(args[2].getValue().toString());
			}
			if (chance > Math.random()) {
				switch (status) {
				case FLINCH:
					pokemon.addSecStatChange(new Flinched());
					battle.sendBattleMessage(pokemon.getName() + " is flinched!");
					break;
				case CONFUSION:
					pokemon.addSecStatChange(new Confusion());
					battle.sendBattleMessage(pokemon.getName() + " is confused!");
					break;
				case DROWSINESS:
					pokemon.addSecStatChange(new Drowsiness());
					battle.sendBattleMessage(pokemon.getName() + " became drowsy!");
					break;
				case WRAPPED:
					pokemon.addSecStatChange(new Wrapped((Pokemon) handler.getVariable(PERFORMER).getValue()));
					battle.sendBattleMessage(pokemon.getName() + " was wrapped!");
					break;
				}
			}
		}
		return null;
	}

}
