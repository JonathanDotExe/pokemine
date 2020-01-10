package at.jojokobi.pokemine.pokemon.status;


import java.util.Map;

import org.bukkit.Particle;

import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class Burn extends PrimStatChange {

	public static final String NAME = "burn";
	
	public Burn() {
		super("BRN");
		setParticle(Particle.FLAME);
	}

	@Override
	public boolean endRound(PokemonContainer pokemon) {
		super.endRound(pokemon);
		Pokemon victim = pokemon.getPokemon();
		victim.setHealth(Math.round(victim.getHealth() - victim.getMaxHealth()*0.0625f));
		return false;
	}
	
	@Override
	public float getPhysicalDamageModifier() {
		return 0.5f;
	}

	@Override
	public String getScriptName() {
		return NAME;
	}
	
	public static Burn deserialize (Map<String, Object> map) {
		Burn obj = new Burn();
		obj.load(map);
		return obj;
	}

	@Override
	public String getAddMessage(Pokemon pokemon) {
		return pokemon.getName() + " is now burnt!";
	}

}
