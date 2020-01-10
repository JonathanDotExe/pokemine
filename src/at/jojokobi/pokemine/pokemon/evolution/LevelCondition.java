package at.jojokobi.pokemine.pokemon.evolution;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class LevelCondition implements EvolutionCondition {
	
	@Override
	public String toString() {
		return "LevelCondition [level=" + level + "]";
	}

	private int level = 0;

	public LevelCondition(int level) {
		super();
		this.level = level;
	}

	@Override
	public boolean canEvolve(Pokemon pokemon, boolean trade, ItemStack giveItem) {
		return level <= pokemon.getLevel();
	}
	
	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("level", level);
		return map;
	}
	
	public static LevelCondition deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new LevelCondition (tMap.getInt("level"));
	}
	
}
