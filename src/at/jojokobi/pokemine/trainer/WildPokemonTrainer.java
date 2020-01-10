package at.jojokobi.pokemine.trainer;

import org.bukkit.entity.Entity;

public class WildPokemonTrainer extends Trainer{
	
	public WildPokemonTrainer () {
		setName("Wild");
	}

	@Override
	public void message(String message) {
		
	}

	@Override
	public Entity getEntity() {
		return null;
	}

	@Override
	public boolean hasExpShare() {
		return false;
	}
	
}
