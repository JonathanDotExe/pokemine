package at.jojokobi.pokemine.pokemon.evolution;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class FriendshipCondition implements EvolutionCondition {

	@Override
	public String toString() {
		return "FriendshipCondition [friendship=" + friendship + "]";
	}

	private int friendship;
	
	public FriendshipCondition(int friendship) {
		super();
		this.friendship = friendship;
	}

	@Override
	public boolean canEvolve(Pokemon pokemon, boolean trade, ItemStack giveItem) {
		return pokemon.getFriendship() >= friendship;
	}
	
	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("friendship", friendship);
		return map;
	}
	
	public static FriendshipCondition deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new FriendshipCondition(tMap.getInt("friendship"));
	}

}
