package at.jojokobi.pokemine.pokemon.status;

import java.util.Random;

import org.bukkit.Particle;

import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.pokemon.MathUtil;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class Confusion extends SecStatChange{

	public static final String SCRIPT_NAME = "confusion";
	
	public Confusion() {
		super();
		setHasDuration(true);
		setDuration(new Random().nextInt(5) + 1);
		setParticle(Particle.SPELL_MOB);
	}
	
	@Override
	public boolean canAttack(PokemonContainer victim) {
		boolean attack =  Math.random() < (2.0/3.0);
		if (!attack) {
			victim.getPokemon().setHealth(Math.round(victim.getPokemon().getHealth() - MathUtil.calcDamage(victim.getPokemon().getLevel(), 40, victim.getAttack(), victim.getDefense())));
		}
		return attack;
	}
	
	@Override
	public String getMessage(Pokemon victim) {
		return victim.getName() + " is confused!";
	}
	
	@Override
	public String getScriptName() {
		return SCRIPT_NAME;
	}
	
	@Override
	public String getAddMessage(Pokemon pokemon) {
		return pokemon.getName() + " became confused!";
	}

}
