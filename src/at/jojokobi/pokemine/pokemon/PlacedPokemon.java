package at.jojokobi.pokemine.pokemon;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import at.jojokobi.mcutil.TypedMap;

public class PlacedPokemon implements ConfigurationSerializable{

	private UUID pokemon;
	private Location location;
	
	public PlacedPokemon(UUID pokemon, Location location) {
		super();
		this.pokemon = pokemon;
		this.location = location;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("pokemon", pokemon + "");
		map.put("location", location);
		return map;
	}

	public static PlacedPokemon deserialize(Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		PlacedPokemon obj = new PlacedPokemon(tMap.getUUID("pokemon", UUID::randomUUID), tMap.get("location", Location.class, null));
		return obj;
	}

	public UUID getPokemon() {
		return pokemon;
	}

	public Location getLocation() {
		return location;
	}

}
