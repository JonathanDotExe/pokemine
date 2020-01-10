package at.jojokobi.pokemine.spawning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Biome;

import at.jojokobi.mcutil.TypedMap;

public class BiomeSpawnCondition implements SpawnCondition {

	private List<Biome> biomes = new ArrayList<>();

	public BiomeSpawnCondition(List<Biome> biomes) {
		super();
		this.biomes = biomes;
	}

	@Override
	public boolean canSpawn(EnvironmentSituation env) {
		return biomes.contains(env.getBiome());
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object> ();
		new TypedMap(map).putEnumList("biomes", biomes);
		return map;
	}
	
	public static BiomeSpawnCondition deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new BiomeSpawnCondition(tMap.getEnumList("biomes", Biome.class));
	}
	
	@Override
	public String toString() {
		return biomes + "";
	}
	
}
