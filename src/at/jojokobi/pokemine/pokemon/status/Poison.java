package at.jojokobi.pokemine.pokemon.status;

import java.util.Map;

import org.bukkit.Particle;

import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class Poison extends PrimStatChange {

	public static final String NAME = "poison";
	
	public Poison() {
		super("PSN");
		setParticle(Particle.DRAGON_BREATH);
	}

	@Override
	public boolean endRound(PokemonContainer victim) {
		victim.getPokemon().setHealth(Math.round(victim.getPokemon().getHealth() - victim.getPokemon().getMaxHealth()*0.125f));
		return super.endRound(victim);
	}
	@Override
	public String getScriptName() {
		return NAME;
	}
	
	public static Poison deserialize (Map<String, Object> map) {
		Poison obj = new Poison();
		obj.load(map);
		return obj;
	}
	
	@Override
	public String getAddMessage(Pokemon pokemon) {
		return pokemon.getName() + " was poisoned!";
	}

}
