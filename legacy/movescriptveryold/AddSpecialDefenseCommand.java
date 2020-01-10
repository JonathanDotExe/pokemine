package at.jojokobi.pokemine.moves.movescriptveryold;

import at.jojokobi.pokemine.pokemon.Pokemon;
@Deprecated
public class AddSpecialDefenseCommand extends AddValueCommand{

	public AddSpecialDefenseCommand(int value, Target target, float chance) {
		super(value, target, chance);
	}

	@Override
	public String applyValues(Pokemon pokemon) {
		pokemon.setSpecialDefenseLevel(getValue() + pokemon.getSpecialDefenseLevel());
		pokemon.getOwner().message("Special Defense increase: " + getValue());
		return pokemon.getName() + "'s special defense rose by " + getValue();
	}

}
