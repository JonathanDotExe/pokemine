package at.jojokobi.pokemine.moves.movescriptveryold;

import at.jojokobi.pokemine.pokemon.Pokemon;
@Deprecated
public class AddDefenseCommand extends AddValueCommand{

	public AddDefenseCommand(int value, Target target, float chance) {
		super(value, target, chance);
	}

	@Override
	public String applyValues(Pokemon pokemon) {
		pokemon.setDefenseLevel(getValue() + pokemon.getDefenseLevel());
		return pokemon.getName() + "'s defense rose by " + getValue();
	}

}
