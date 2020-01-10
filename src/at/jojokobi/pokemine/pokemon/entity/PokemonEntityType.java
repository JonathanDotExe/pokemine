package at.jojokobi.pokemine.pokemon.entity;

import org.bukkit.Location;

import at.jojokobi.mcutil.entity.CustomEntityType;
import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonSpecies;

public final class PokemonEntityType implements CustomEntityType<PokemonEntity>{
	
	public static final String KEY = "pokemon";

	private static PokemonEntityType instance;
	
	public static PokemonEntityType getInstance () {
		if (instance == null) {
			instance = new PokemonEntityType();
		}
		return instance;
	}
	
	private PokemonEntityType() {
		
	}
	
	@Override
	public String getIdentifier() {
		return KEY;
	}

	@Override
	public String getNamespace() {
		return PokeminePlugin.POKEMINE_NAMESPACE;
	}

	@Override
	public PokemonEntity createInstance(Location loc, EntityHandler handler) {
		return new PokemonEntity(loc, new Pokemon(new PokemonSpecies(PokeminePlugin.POKEMINE_NAMESPACE, "missingno")), handler);
	}

}
