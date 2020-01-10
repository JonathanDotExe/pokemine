package at.jojokobi.pokemine.moves.movescriptveryold;

import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.status.Poison;
@Deprecated
public class PoisonCommand extends StatusCommand {

	public PoisonCommand(Target target, float chance) {
		super(target, chance);
	}

	@Override
	public String applyValues(Pokemon pokemon) {
		String text = "But it failed!";
		if (pokemon.getPrimStatChange() == null) {
			pokemon.setPrimStatChange(new Poison());
			text = pokemon.getName() + " was poisoned!";
		}
		return text;
	}

}
