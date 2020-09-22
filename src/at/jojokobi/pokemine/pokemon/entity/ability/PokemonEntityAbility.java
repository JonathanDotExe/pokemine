package at.jojokobi.pokemine.pokemon.entity.ability;

import java.util.List;

import at.jojokobi.mcutil.entity.ai.EntityTask;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;

public interface PokemonEntityAbility {
	
	void entityTick (PokemonEntity entity);
	
	List<EntityTask> createTasks(PokemonEntity entity);

}
