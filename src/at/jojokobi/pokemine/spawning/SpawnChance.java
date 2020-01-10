package at.jojokobi.pokemine.spawning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;

public class SpawnChance implements ISpawnChance {

	private int chance = 0;
	private List<SpawnCondition> conditions;

	public SpawnChance(int chance, List<SpawnCondition> conditions) {
		super();
		this.chance = chance;
		this.conditions = conditions;
	}

	@Override
	public int getSpawnChance(EnvironmentSituation env) {
		return conditions.stream().allMatch(c -> c.canSpawn(env)) ? chance : 0;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put("chance", chance);
		map.put("conditions", conditions);
		return map;
	}
	
	public static SpawnChance deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new SpawnChance(tMap.getInt("chance"), tMap.getList("conditions", SpawnCondition.class));
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(chance);
		string.append(": ");
		for (SpawnCondition spawnCondition : conditions) {
			string.append(spawnCondition);
			string.append(" ");
		}
		return string.toString();
	}

}
