package at.jojokobi.pokemine.moves.movescriptold;

import static at.jojokobi.pokemine.moves.MoveHandler.*;

import at.jojokobi.kiwiscript.NumberUtils;
import at.jojokobi.kiwiscript.Value;
import at.jojokobi.kiwiscript.VariableHandler;
import at.jojokobi.kiwiscript.commands.FunctionCommand;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.status.Burn;
import at.jojokobi.pokemine.pokemon.status.Freeze;
import at.jojokobi.pokemine.pokemon.status.HeavyPoison;
import at.jojokobi.pokemine.pokemon.status.Paralysis;
import at.jojokobi.pokemine.pokemon.status.Poison;
import at.jojokobi.pokemine.pokemon.status.Sleep;
@Deprecated
public class SetStatusCommand extends FunctionCommand{

	public static final String NAME = "setStatus";
	
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
				if (pokemon.getPrimStatChange()  == null) {
					switch (status) {
					case BURN:
						pokemon.setPrimStatChange(new Burn());
						battle.sendBattleMessage(pokemon.getName() + " was burned!");
						break;
					case FREEZE:
						pokemon.setPrimStatChange(new Freeze());
						battle.sendBattleMessage(pokemon.getName() + " was frozen!");
						break;
					case HEAVY_POISON:
						pokemon.setPrimStatChange(new HeavyPoison());
						battle.sendBattleMessage(pokemon.getName() + " was poisoned heavily!");
						break;
					case PARALYSIS:
						pokemon.setPrimStatChange(new Paralysis());
						battle.sendBattleMessage(pokemon.getName() + " was paralysed!");
						break;
					case POISON:
						pokemon.setPrimStatChange(new Poison());
						battle.sendBattleMessage(pokemon.getName() + " was poisoned!");
						break;
					case SLEEP:
						pokemon.setPrimStatChange(new Sleep());
						battle.sendBattleMessage(pokemon.getName() + " is now sleeping!");
						break;
					}
				}
				else {
					battle.sendBattleMessage("But it failed!");
				}
			}
		}
		return null;
	}

}
