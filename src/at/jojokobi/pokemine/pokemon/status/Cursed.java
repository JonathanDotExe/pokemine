package at.jojokobi.pokemine.pokemon.status;

import org.bukkit.Particle;

import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class Cursed extends SecStatChange{

	public static final String SCRIPT_NAME = "cursed";
	
	public Cursed() {
		super();
		setParticle(Particle.TOWN_AURA);
	}
	@Override
	public boolean endRound(PokemonContainer victim) {
		victim.getPokemon().setHealth(victim.getPokemon().getHealth() - victim.getPokemon().getMaxHealth()/4);
		return super.endRound(victim);
	}
	
	@Override
	public String getScriptName() {
		return SCRIPT_NAME;
	}
	
	@Override
	public String getMessage(Pokemon victim) {
		return victim + " is cursed!";
	}
	
	@Override
	public String getAddMessage(Pokemon pokemon) {
		return "A curse was put on " + pokemon.getName() + "!";
	}

}
