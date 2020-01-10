package at.jojokobi.pokemine.moves.movescriptveryold;

import at.jojokobi.pokemine.pokemon.Pokemon;
@Deprecated
public class AddAttackCommand extends AddValueCommand{

	public AddAttackCommand(int value, Target target, float chance) {
		super(value, target, chance);
	}

	@Override
	public String applyValues(Pokemon pokemon) {
		pokemon.setAttackLevel(getValue() + pokemon.getAttackLevel());
		return pokemon.getName() + "'s attack rose by " + getValue();
	}

}
