package at.jojokobi.pokemine.moves.movescriptveryold;

import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.status.Sleep;
@Deprecated
public class SleepCommand extends StatusCommand {

	public SleepCommand(Target target, float chance) {
		super(target, chance);
	}

	@Override
	public String applyValues(Pokemon pokemon) {
		String text = "But it failed!";
		if (pokemon.getPrimStatChange() == null) {
			pokemon.setPrimStatChange(new Sleep());
			text = pokemon.getName() + " fell asleep!";
		}
		return text;
	}

}
