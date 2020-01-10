package at.jojokobi.pokemine.pokemon.status;

import java.util.Random;

import org.bukkit.Particle;

import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class Wrapped extends SecStatChange{

	public static final String SCRIPT_NAME = "wrapped";
	
	private ProcedurePokemon perpetrator;
	
	public Wrapped(ProcedurePokemon perpetrator) {
		super();
		this.perpetrator = perpetrator;
		setHasDuration(true);
		setDuration(new Random().nextBoolean() ? 5 : 4);
		setParticle(Particle.SWEEP_ATTACK);
	}
	
	@Override
	public boolean canSwitch(PokemonContainer victim) {
		return false;
	}
	
	@Override
	public boolean switchPokemon (PokemonContainer victim, PokemonContainer oldPokemon, Pokemon newPokemon) {
		super.switchPokemon(victim,oldPokemon, newPokemon);
		return perpetrator.representsPokemon(oldPokemon.getPokemon()) && !perpetrator.representsPokemon(newPokemon);
	}
	
	@Override
	public boolean endRound(PokemonContainer victim) {
		victim.getPokemon().setHealth(Math.round(victim.getPokemon().getHealth() - victim.getPokemon().getMaxHealth()*0.125f));
		return super.endRound(victim);
	}

	public ProcedurePokemon getPerpetrator() {
		return perpetrator;
	}
	
	@Override
	public String getScriptName() {
		return SCRIPT_NAME;
	}
	
	@Override
	public String getMessage(Pokemon victim) {
		return victim.getName() + " is wrapped by " + perpetrator.getName();
	}
	
	@Override
	public String getAddMessage(Pokemon pokemon) {
		return pokemon.getName() + " was wrapped by " + perpetrator.getName() + "!";
	}

}
