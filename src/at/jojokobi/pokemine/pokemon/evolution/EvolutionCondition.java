package at.jojokobi.pokemine.pokemon.evolution;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.pokemine.pokemon.Pokemon;

public interface EvolutionCondition extends ConfigurationSerializable{
	
	public boolean canEvolve (Pokemon pokemon, boolean trade, ItemStack giveItem);

}
