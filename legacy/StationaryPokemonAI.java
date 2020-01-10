package at.jojokobi.pokemine.pokemon.entity;

import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.ai.EntityAI;
import at.jojokobi.pokemine.PokeminePlugin;

public class StationaryPokemonAI implements EntityAI {
	
	public static final StationaryPokemonAI INSTANCE = new StationaryPokemonAI();

	@Override
	public String getIdentifier() {
		return "stationary_pokemon";
	}

	@Override
	public String getNamespace() {
		return PokeminePlugin.POKEMINE_NAMESPACE;
	}

	@Override
	public void changeAI(CustomEntity<?> entity) {
		entity.setTask(null);
	}

}
