package at.jojokobi.pokemine.pokemon;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import at.jojokobi.mcutil.FileLoader;

public class PokemonYAMLLoader implements FileLoader<PokemonSpecies>{
	
	private static PokemonYAMLLoader instance;
	
	public static PokemonYAMLLoader getInstance () {
		if (instance == null) {
			instance = new PokemonYAMLLoader();
		}
		return instance;
	}

	@Override
	public PokemonSpecies load(InputStream in) throws IOException, InvalidConfigurationException {
		FileConfiguration config = new YamlConfiguration();
		config.load(new InputStreamReader(in));
		return config.getSerializable("species", PokemonSpecies.class, null);
	}

}
