package at.jojokobi.pokemine.moves.movescriptold;

import at.jojokobi.kiwiscript.NumberUtils;
import at.jojokobi.kiwiscript.Value;
import at.jojokobi.kiwiscript.VariableHandler;
import at.jojokobi.kiwiscript.commands.FunctionCommand;
import at.jojokobi.pokemine.pokemon.Pokemon;

import static at.jojokobi.pokemine.moves.MoveHandler.*;
@Deprecated
public class SetValueCommand extends FunctionCommand{

	public static final String NAME = "setValue";
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Object execute(Value[] args, VariableHandler handler) {
		Integer value = null;
		if (args[0].getValue() instanceof Pokemon && NumberUtils.isInteger(args[1].getValue().toString())  && NumberUtils.isInteger(args[2].getValue().toString())) {
			Pokemon pokemon = (Pokemon) args[0].getValue();
			int amount = Integer.parseInt(args[1].getValue().toString());
			int status = Integer.parseInt(args[2].getValue().toString());
			float chance = 1;
			if (args.length > 3 && NumberUtils.isDouble(args[3].getValue().toString())) {
				chance = Float.parseFloat(args[3].getValue().toString());
			}
			if (chance > Math.random()) {
				switch (status) {
				case ATTACK:
					pokemon.setAttackLevel(amount);
					value = ATTACK;
					break;
				case DEFENSE:
					pokemon.setDefenseLevel(amount);
					value = DEFENSE;
					break;
				case SPECIAL_ATTACK:
					pokemon.setSpecialAttackLevel(amount);
					value = SPECIAL_ATTACK;
					break;
				case SPECIAL_DEFENSE:
					pokemon.setSpecialDefenseLevel(amount);
					value = SPECIAL_DEFENSE;
					break;
				case SPEED:
					pokemon.setSpeedLevel(amount);
					value = SPEED;
					break;
				default:
					break;
				}
			}
		}
		return value;
	}

	

}
