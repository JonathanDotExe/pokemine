package at.jojokobi.pokemine.pokemon.status;

import java.util.Map;

import org.bukkit.Particle;

import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class Paralysis extends PrimStatChange{

	public static final String NAME = "paralysis";
	
	public Paralysis() {
		super("PAR");
		setParticle(Particle.VILLAGER_ANGRY);
	}
	
	@Override
	public boolean canAttack(PokemonContainer victim) {
		return Math.random() > 0.25;
	}
	
	@Override
	public float getSpeedModifier() {
		return 0.5f;
	}

	@Override
	public String getScriptName() {
		return NAME;
	}

	public static Paralysis deserialize (Map<String, Object> map) {
		Paralysis obj = new Paralysis();
		obj.load(map);
		return obj;
	}
	
	@Override
	public String getAddMessage(Pokemon pokemon) {
		return pokemon.getName() + " was paralysed!";
	}
	
}
