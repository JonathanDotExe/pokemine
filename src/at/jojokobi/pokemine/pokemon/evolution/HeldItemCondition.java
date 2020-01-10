package at.jojokobi.pokemine.pokemon.evolution;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class HeldItemCondition implements EvolutionCondition {

	private Material material;
	
	public HeldItemCondition(Material material) {
		super();
		this.material = material;
	}

	@Override
	public boolean canEvolve(Pokemon pokemon, boolean trade, ItemStack giveItem) {
		return pokemon.getItem() != null && pokemon.getItem().getType() == material;
	}
	
	@Override
	public String toString() {
		return "HeldItemCondition [material=" + material + "]";
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("material", material + "");
		return map;
	}
	
	public static HeldItemCondition deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new HeldItemCondition (tMap.getEnum("material", Material.class, Material.AIR));
	}
	
}
