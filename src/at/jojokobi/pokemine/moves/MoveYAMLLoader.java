package at.jojokobi.pokemine.moves;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import at.jojokobi.mcutil.FileLoader;

public class MoveYAMLLoader implements FileLoader<Move>{
	
	private static MoveYAMLLoader instance;
	
	public static MoveYAMLLoader getInstance () {
		if (instance == null) {
			instance = new MoveYAMLLoader();
		}
		return instance;
	}

	@Override
	public Move load(InputStream in) throws IOException, InvalidConfigurationException {
		FileConfiguration config = new YamlConfiguration();
		config.load(new InputStreamReader(in));
		return config.getSerializable("move", Move.class, null);
	}

}
