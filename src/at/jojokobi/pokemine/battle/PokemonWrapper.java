package at.jojokobi.pokemine.battle;

import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.status.SecStatChange;

public interface PokemonWrapper {
	
	public Pokemon getPokemon ();
	
	public boolean hasSecStatChange (Class<? extends SecStatChange> stat);
	
	public void removeSecStatChange (Class<? extends SecStatChange> stat);
	
	public void clearSecStatChanges ();
	
	public void heal ();
	
	public static PokemonWrapper fromPokemon (Pokemon pokemon) {
		return new PokemonWrapper() {
			
			@Override
			public void removeSecStatChange(Class<? extends SecStatChange> stat) {
				
			}
			
			@Override
			public void heal() {
				
			}
			
			@Override
			public boolean hasSecStatChange(Class<? extends SecStatChange> stat) {
				return false;
			}
			
			@Override
			public Pokemon getPokemon() {
				return pokemon;
			}
			
			@Override
			public void clearSecStatChanges() {
				
			}
		};
	}
	
	public static PokemonWrapper fromPokemonContainer (PokemonContainer container) {
		return new PokemonWrapper() {
			
			@Override
			public void removeSecStatChange(Class<? extends SecStatChange> stat) {
				container.removeSecStatChange(stat);
			}
			
			@Override
			public void heal() {
				container.heal();
			}
			
			@Override
			public boolean hasSecStatChange(Class<? extends SecStatChange> stat) {
				return container.hasSecStatChange(stat);
			}
			
			@Override
			public Pokemon getPokemon() {
				return container.getPokemon();
			}
			
			@Override
			public void clearSecStatChanges() {
				container.clearSecStatChanges();
			}
		};
	}

}
