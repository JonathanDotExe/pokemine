package at.jojokobi.pokemine.pokemon.status;


import org.bukkit.Particle;

import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class LeechSeed extends SecStatChange{

	public static final String SCRIPT_NAME = "leech_seed";
	
	private ProcedurePokemon perpetrator;
	
	public LeechSeed(ProcedurePokemon perpetrator) {
		this.perpetrator = perpetrator;
		setHasDuration(false);
		setParticle(Particle.VILLAGER_HAPPY);
	}
	
	@Override
	public boolean switchPokemon(PokemonContainer victim, PokemonContainer oldPokemon, Pokemon newPokemon) {
		super.switchPokemon(victim, oldPokemon, newPokemon);
		return perpetrator.representsPokemon(oldPokemon.getPokemon()) && !perpetrator.representsPokemon(newPokemon);
	}
	
	@Override
	public boolean endRound(PokemonContainer victim) {
		int damage = Math.round(victim.getPokemon().getMaxHealth()*0.125f);
		victim.getPokemon().setHealth(victim.getPokemon().getHealth() - damage);
		if (perpetrator.getHealth() > 0) {
			perpetrator.setHealth(perpetrator.getHealth() + damage);
		}
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
		return victim.getName() + " is planted by " + perpetrator.getName() + "!";
	}
	
	@Override
	public String getAddMessage(Pokemon pokemon) {
		return pokemon.getName() + " was planted!";
	}

}
