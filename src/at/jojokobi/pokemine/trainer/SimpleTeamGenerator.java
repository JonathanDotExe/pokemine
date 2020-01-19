package at.jojokobi.pokemine.trainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.PokemonSpecies;

public class SimpleTeamGenerator implements TeamGenerator, ConfigurationSerializable{

	private byte level;
	private int count;
	private long seed;

	public SimpleTeamGenerator(byte level, int count, long seed) {
		super();
		this.level = level;
		this.count = count;
		this.seed = seed;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("level", level);
		map.put("count", count);
		map.put("seed", seed);
		return map;
	}
	
	public static SimpleTeamGenerator deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new SimpleTeamGenerator(tMap.getByte("level"), tMap.getInt("count"), tMap.getLong("seed"));
	}

	@Override
	public List<Pokemon> create(TrainerRank rank) {
		List<Pokemon> pokemon = new ArrayList<>();
		List<PokemonSpecies> species = TrainerUtil.getUsablePokemon(PokemonHandler.getInstance(), rank);
		Random random = new Random(seed);
		for (int i = 0; i < count; i++) {
			pokemon.add(new Pokemon(species.get(random.nextInt(species.size())), level));
		}
		return pokemon;
	}

}
