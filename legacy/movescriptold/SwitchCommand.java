package at.jojokobi.pokemine.moves.movescriptold;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import at.jojokobi.kiwiscript.Value;
import at.jojokobi.kiwiscript.VariableHandler;
import at.jojokobi.kiwiscript.commands.FunctionCommand;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveHandler;
import at.jojokobi.pokemine.pokemon.Pokemon;
@Deprecated
public class SwitchCommand extends FunctionCommand{

	public static final String NAME = "switchPokemon";
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Object execute(Value[] arguments, VariableHandler handler) {
		Battle battle = (Battle) handler.getVariable(MoveHandler.BATTLE).getValue();
		Pokemon oldPokemon = (Pokemon) arguments[0].getValue();
		Pokemon newPokemon = chooseSwitchPokemon(oldPokemon);
		boolean force = true;
		if (arguments.length > 1) {
			force = Boolean.parseBoolean(arguments[1].getValue().toString());
		}
		if (arguments.length > 2) {
			newPokemon = (Pokemon) arguments[2].getValue();
		}
		battle.switchPokemon(oldPokemon, newPokemon, force);
		return null;
	}
	
	private Pokemon chooseSwitchPokemon (Pokemon old) {
		List<Pokemon> possible = new ArrayList<>();
		for (Pokemon poke : old.getOwner().getParty()) {
			if (poke != null && poke != old) {
				possible.add(poke);
			}
		}
		return possible.get(new Random().nextInt(possible.size()));
	}

}
