package at.jojokobi.pokemine.pokemon.evolution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class EvolutionCause implements ConfigurationSerializable{

//	public static final String NO_ITEM = "";
	
	private NamespacedEntry species;
//	private byte level;
//	private String item = "";
//	private boolean trade = false;
//	private int friendship = 0;
//	private String heldItem = "";
	private List<EvolutionCondition> conditions;
	
	public EvolutionCause(NamespacedEntry species, List<EvolutionCondition> conditions) {
		super();
		this.species = species;
		this.conditions = conditions;
	}
	
	public boolean canEvolve (Pokemon pokemon, ItemStack item, boolean trade) {
//		return pokemon.getLevel() >= getLevel() && item.equalsIgnoreCase(this.item) && ((isTrade()) ? trade : true) && (pokemon.getFriendship() >= getFriendship()) && heldItem.equalsIgnoreCase(this.heldItem);
		return conditions.stream().allMatch(c -> c.canEvolve(pokemon, trade, item));
	}
	
	public NamespacedEntry getSpecies() {
		return species;
	}
//	public void setSpecies(String species) {
//		this.species = species;
//	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("species", species);
		map.put("conditions", conditions);
		return map;
	}
	
	public List<EvolutionCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<EvolutionCondition> conditions) {
		this.conditions = conditions;
	}

	public void setSpecies(NamespacedEntry species) {
		this.species = species;
	}

	public static EvolutionCause deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		return new EvolutionCause(tMap.get("species", NamespacedEntry.class, new NamespacedEntry(PokeminePlugin.POKEMINE_NAMESPACE, "missingno")), tMap.getList("conditions", EvolutionCondition.class)); 
	}
	
	@Override
		public String toString() {
			return species.getIdentifier();
		}
}
