package at.jojokobi.pokemine.pokemon.evolution;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import at.jojokobi.pokemine.pokemon.Pokemon;

public class TradeCondition implements EvolutionCondition{

	public TradeCondition() {
		
	}

	@Override
	public boolean canEvolve(Pokemon pokemon, boolean trade, ItemStack giveItem) {
		return trade;
	}

	@Override
	public Map<String, Object> serialize() {
		return new HashMap<>();
	}
	
	public static TradeCondition deserialize (Map<String, Object> map) {
		return new TradeCondition();
	}

	@Override
	public String toString() {
		return "TradeCondition []";
	}

}
