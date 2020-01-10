package at.jojokobi.pokemine.moves.movescriptveryold;

import at.jojokobi.pokemine.pokemon.Pokemon;
@Deprecated
public class AddSpeedCommand extends AddValueCommand{

	public AddSpeedCommand(int value, Target target, float chance) {
		super(value, target, chance);
	}

	@Override
	public String applyValues(Pokemon pokemon) {
		pokemon.setSpeedLevel(getValue() + pokemon.getSpeedLevel());
		return pokemon.getName() + "'s speed rose by " + getValue();
	}

}
