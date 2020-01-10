package at.jojokobi.pokemine.spawning;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface SpawnCondition extends ConfigurationSerializable{
	
	public boolean canSpawn (EnvironmentSituation env);

}
