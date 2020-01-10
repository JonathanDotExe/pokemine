package at.jojokobi.pokemine.moves.movescriptveryold;

import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.status.Paralysis;
@Deprecated
public class ParalysisCommand extends StatusCommand {

	public ParalysisCommand(Target target, float chance) {
		super(target, chance);
	}

	@Override
	public String applyValues(Pokemon pokemon) {
		String text = "But it failed!";
		if (pokemon.getPrimStatChange() == null) {
			pokemon.setPrimStatChange(new Paralysis());
			text = pokemon.getName() + " was paralysed!";
		}
		return text;
	}

}
