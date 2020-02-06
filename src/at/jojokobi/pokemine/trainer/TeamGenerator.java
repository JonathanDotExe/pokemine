package at.jojokobi.pokemine.trainer;

import java.util.List;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import at.jojokobi.pokemine.pokemon.Pokemon;

public interface TeamGenerator extends ConfigurationSerializable {
	
	public List<Pokemon> create (TrainerRank rank);
	
	public int getLevel ();

}
