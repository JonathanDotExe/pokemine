package at.jojokobi.pokemine.spawning;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface ISpawnChance  extends ConfigurationSerializable{

	public int getSpawnChance (EnvironmentSituation env);

}
