package at.jojokobi.pokemine.moves;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class MoveLearnCondition implements ConfigurationSerializable {
	
	public static final int EVOLUTION_LEVEL = 0;
	
	private NamespacedEntry move;
	private int level;
	
	
	public MoveLearnCondition(NamespacedEntry move, int level) {
		super();
		this.move = move;
		this.level = level;
	}
	
	public Move getRealMove() {
		return MoveHandler.getInstance().getItem(move);
	}
	public NamespacedEntry getMove() {
		return move;
	}
	public int getLevel() {
		return level;
	}
	
	public boolean canLearn (Pokemon pokemon) {
		return pokemon.getLevel() >= this.getLevel();
	}
	
	public static MoveLearnCondition deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new MoveLearnCondition(tMap.get("move", NamespacedEntry.class, new NamespacedEntry(PokeminePlugin.POKEMINE_NAMESPACE, "pound")), tMap.getInt("level"));
	}

	@Override
	public String toString() {
		return "Lvl. " + level + " " + move.getNamespace() + ":" + move.getIdentifier();
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("move", move);
		map.put("level", level);
		return map;
	}
	
}
