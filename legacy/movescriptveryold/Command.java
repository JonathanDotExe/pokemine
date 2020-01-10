package at.jojokobi.pokemine.moves.movescriptveryold;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.pokemon.Pokemon;
@Deprecated
public abstract class Command {
	
	public abstract void execute (Battle battle, Pokemon performer, Pokemon defender);
	
}
