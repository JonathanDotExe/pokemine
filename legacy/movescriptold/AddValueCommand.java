package at.jojokobi.pokemine.moves.movescriptold;

import static at.jojokobi.pokemine.moves.MoveHandler.*;

import at.jojokobi.kiwiscript.NumberUtils;
import at.jojokobi.kiwiscript.Value;
import at.jojokobi.kiwiscript.VariableHandler;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.pokemon.Pokemon;
@Deprecated
public class AddValueCommand extends SetValueCommand {

	public static final String NAME = "addValue";
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public Object execute(Value[] args, VariableHandler handler) {
		Integer value = null;
		if (args[0].getValue() instanceof Pokemon && NumberUtils.isInteger(args[1].getValue().toString())  && NumberUtils.isInteger(args[2].getValue().toString())) {
			Pokemon pokemon = (Pokemon) args[0].getValue();
			Battle battle = (Battle) handler.getVariable(BATTLE).getValue();
			int amount = Integer.parseInt(args[1].getValue().toString());
			int status = Integer.parseInt(args[2].getValue().toString());
			float chance = 1;
			if (args.length > 3 && NumberUtils.isDouble(args[3].getValue().toString())) {
				chance = Float.parseFloat(args[3].getValue().toString());
			}
			if (chance > Math.random()) {
				switch (status) {
				case ATTACK:
					pokemon.setAttackLevel(pokemon.getAttackLevel() + amount);
					battle.sendBattleMessage(pokemon.getName() + "'s attack rose by " + amount + "!");
					value = ATTACK;
					break;
				case DEFENSE:
					pokemon.setDefenseLevel(pokemon.getDefenseLevel() + amount);
					battle.sendBattleMessage(pokemon.getName() + "'s defense rose by " + amount + "!");
					value = DEFENSE;
					break;
				case SPECIAL_ATTACK:
					pokemon.setSpecialAttackLevel(pokemon.getSpecialAttackLevel() + amount);
					battle.sendBattleMessage(pokemon.getName() + "'s special attack rose by " + amount + "!");
					value = SPECIAL_ATTACK;
					break;
				case SPECIAL_DEFENSE:
					pokemon.setSpecialDefenseLevel(pokemon.getSpecialDefenseLevel() + amount);
					battle.sendBattleMessage(pokemon.getName() + "'s special defense rose by " + amount + "!");
					value = SPECIAL_DEFENSE;
					break;
				case SPEED:
					pokemon.setSpeedLevel(pokemon.getSpeedLevel() + amount);
					battle.sendBattleMessage(pokemon.getName() + "'s speed rose by " + amount + "!");
					value = SPEED;
					break;
				default:
					break;
				}
			}
		}
		return value;
//		Object value = null;
//		if (args[0].getValue() instanceof Pokemon && NumberUtils.isInteger(args[1].getValue().toString()) && NumberUtils.isInteger(args[2].getValue().toString())) {
//			Pokemon pokemon = (Pokemon) args[0].getValue();
//			Battle battle = (Battle) handler.getVariable(BATTLE).getValue();
//			int amount = Integer.parseInt(args[1].getValue().toString());
//			int status = Integer.parseInt(args[2].getValue().toString());
//			switch (status) {
//			case ATTACK:
//				battle.sendBattleMessage(pokemon.getName() + "'s attack rose by " + amount + "!");
//				amount += pokemon.getAttackLevel();
//				break;
//			case DEFENSE:
//				battle.sendBattleMessage(pokemon.getName() + "'s defense rose by " + amount + "!");
//				amount += pokemon.getDefenseLevel();
//				break;
//			case SPECIAL_ATTACK:
//				battle.sendBattleMessage(pokemon.getName() + "'s special attack rose by " + amount + "!");
//				amount += pokemon.getSpecialAttackLevel();
//				break;
//			case SPECIAL_DEFENSE:
//				battle.sendBattleMessage(pokemon.getName() + "'s special defense rose by " + amount + "!");
//				amount += pokemon.getSpecialDefenseLevel();
//				break;
//			case SPEED:
//				battle.sendBattleMessage(pokemon.getName() + "'s speed rose by " + amount + "!");
//				amount += pokemon.getSpeedLevel();
//				break;
//			default:
//				break;
//			}
//			args[1] = new SimpleValue(amount);
//			value = super.execute(args, handler);
//		}
//		return value;
	}

}
