package at.jojokobi.pokemine.pokemon.evolution;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.mcutil.item.CustomItem;
import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class CustomItemCondition implements EvolutionCondition {
	
	private NamespacedEntry item = null;

	public CustomItemCondition(NamespacedEntry identifier) {
		super();
		this.item = identifier;
	}

	@Override
	public boolean canEvolve(Pokemon pokemon, boolean trade, ItemStack giveItem) {
		CustomItem item = ItemHandler.getCustomItem(this.item.getNamespace(), this.item.getIdentifier());
		return item != null && item.isItem(giveItem);
	}

	@Override
	public String toString() {
		return "CustomItemCondition [item=" + item + "]";
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("item", item);
		return map;
	}
	
	public static CustomItemCondition deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new CustomItemCondition (tMap.get("item", NamespacedEntry.class, new NamespacedEntry(PokeminePlugin.POKEMINE_NAMESPACE, "")));
	}

}
