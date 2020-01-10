package at.jojokobi.pokemine.moves.movescriptveryold;

import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.status.Freeze;
@Deprecated
public class FreezeCommand extends StatusCommand {

	public FreezeCommand(Target target, float chance) {
		super(target, chance);
	}

	@Override
	public String applyValues(Pokemon pokemon) {
		String text = "But it failed!";
		if (pokemon.getPrimStatChange() == null) {
			pokemon.setPrimStatChange(new Freeze());
			text = pokemon.getName() + " was frozen!";
		}
		return text;
	}

}
