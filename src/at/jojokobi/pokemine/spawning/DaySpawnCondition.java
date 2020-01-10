package at.jojokobi.pokemine.spawning;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;

public class DaySpawnCondition implements SpawnCondition{

	private boolean day = true;

	public DaySpawnCondition(boolean day) {
		super();
		this.day = day;
	}

	@Override
	public boolean canSpawn(EnvironmentSituation env) {
		return env.isDay() == day;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put("day", day);
		return map;
	}
	
	public static DaySpawnCondition deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new DaySpawnCondition(tMap.getBoolean("day"));
	}
	
	@Override
	public String toString() {
		return day ? "Day" : "Night";
	}
	
}
