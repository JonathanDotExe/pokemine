package at.jojokobi.pokemine.spawning;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;

public class RainSpawnCondition implements SpawnCondition{

	private boolean rain;

	public RainSpawnCondition(boolean rain) {
		super();
		this.rain = rain;
	}

	@Override
	public boolean canSpawn(EnvironmentSituation env) {
		return env.isRain() == rain;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put("rain", rain);
		return map;
	}
	
	public static RainSpawnCondition deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new RainSpawnCondition(tMap.getBoolean("rain"));
	}
	
	@Override
	public String toString() {
		return rain ? "Rain" : "Clear";
	}

}
