package at.jojokobi.pokemine.pokemon.status;

import at.jojokobi.pokemine.pokemon.Pokemon;

public abstract class SecStatChange extends StatChange {
	
	public SecStatChange() {
		super();
	}

	public abstract String getMessage(Pokemon victim);

}
