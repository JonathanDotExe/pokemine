package at.jojokobi.pokemine.pokemon.status;

import org.bukkit.Particle;

import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.pokemon.Pokemon;

public abstract class StatChange {

	private int round = 0;
	private Particle particle;
	private int duration = 0;
	
	private boolean hasDuration = false;

	public StatChange() {
//		this.victim = victim;
	}
	
	public abstract String getScriptName ();
	
	public abstract String getAddMessage(Pokemon pokemon);
	
	public boolean canAttack (PokemonContainer victim) {
		return true;
	}
	
	/**
	 * 
	 * @param victim
	 * @return True if the stat change should be removes false otherwise
	 */
	public boolean startRound (PokemonContainer victim) {
		round++;
		return false;
	}
	
	/**
	 * 
	 * @param victim
	 * @return True if the stat change should be removes false otherwise
	 */
	public boolean startTurn (PokemonContainer victim) {
		if (isHasDuration() && getRound() > duration) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param victim
	 * @return True if the stat change should be removes false otherwise
	 */
	public boolean endTurn (PokemonContainer victim) {
		return false;
	}
	
	/**
	 * 
	 * @param victim
	 * @return True if the stat change should be removes false otherwise
	 */
	public boolean endRound (PokemonContainer victim) {
		return false;
	}
	
	/**
	 * 
	 * @param victim
	 * @return True if the stat change should be removed false otherwise
	 */
	public boolean switchPokemon (PokemonContainer victim, PokemonContainer oldPokemon, Pokemon newPokemon) {
		return false;
	}
	
	public float getAttackModifier() {
		return 1;
	}
	
	public float getDefenseModifier() {
		return 1;
	}
	
	public float getSpecialAttackModifier() {
		return 1;
	}

	
	public float getSpecialDefenseModifier() {
		return 1;
	}
	
	public float getSpeedModifier() {
		return 1;
	}
	
	public float getPhysicalDamageModifier () {
		return 1;
	}

	public float getSpecialDamageModifier () {
		return 1;
	}
	
	public boolean canSwitch(PokemonContainer victim) {
		return true;
	}

//	public Pokemon getVictim() {
//		return victim;
//	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}


	public Particle getParticle() {
		return particle;
	}


	protected void setParticle(Particle particle) {
		this.particle = particle;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isHasDuration() {
		return hasDuration;
	}

	public void setHasDuration(boolean hasDuration) {
		this.hasDuration = hasDuration;
	}

//	public void setVictim(Pokemon victim) {
//		if (this.victim == null) {
//			this.victim = victim;
//		}
//		else {
//			throw new IllegalStateException("This Stat change already has a victim!");
//		}
//	}

}
