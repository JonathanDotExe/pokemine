package at.jojokobi.pokemine.moves.movescriptveryold;

import at.jojokobi.pokemine.pokemon.Pokemon;
@Deprecated
public class AddSpecialAttackCommand extends AddValueCommand{

	public AddSpecialAttackCommand(int value, Target target, float chance) {
		super(value, target, chance);
	}

	@Override
	public String applyValues(Pokemon pokemon) {
		pokemon.setSpecialAttackLevel(getValue() + pokemon.getSpecialAttackLevel());
		return pokemon.getName() + "'s special attack rose by " + getValue();
	}

}
