package at.jojokobi.pokemine.pokemon.entity;

import at.jojokobi.mcutil.entity.EntityCriteria;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class PokemonCriteria implements EntityCriteria<PokemonEntity>{

	private Pokemon pokemon;
	
	public PokemonCriteria(Pokemon pokemon) {
		super();
		this.pokemon = pokemon;
	}

	@Override
	public boolean matches(PokemonEntity entity) {
		return entity.getPokemon() == pokemon;
	}

	@Override
	public Class<PokemonEntity> getFilterClass() {
		return PokemonEntity.class;
	}

}
