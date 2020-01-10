package at.jojokobi.pokemine.moves.movescript;

import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.CausedDamage;
import at.jojokobi.pokemine.pokemon.PokemonType;

public class ScriptPokemon {

	private ProcedurePokemon pokemon;
	
	public ScriptPokemon(ProcedurePokemon pokemon) {
		this.pokemon = pokemon;
	}
	
	protected ProcedurePokemon getPokemon () {
		return pokemon;
	}
	
	public byte getLevel() {
		return pokemon.getLevel();
	}
	
	public int getHealth() {
		return pokemon.getHealth();
	}
	
	public float getHealthInPercent () {
		return pokemon.getHealthInPercent();
	}
	
	public int getMaxHealth() {
		return pokemon.getMaxHealth();
	}
	
	public boolean hasStatus (String status) {
		return pokemon.hasSecStatChangeLegacy(status);
	}
	
	public boolean hasType (String type) {
		return pokemon.hasType(PokemonType.valueOf(type.toUpperCase()));
	}
	
	public void setHealth(int health) {
		pokemon.setHealth(health);
	}
	
	public void heal (int health) {
		setHealth(getHealth() + health);
	}
	
	public int getAttackLevel() {
		return pokemon.getAttackLevel();
	}
	
	public int getDefenseLevel() {
		return pokemon.getDefenseLevel();
	}
	
	public int getSpecialAttackLevel() {
		return pokemon.getSpecialAttackLevel();
	}
	
	public int getSpecialDefenseLevel() {
		return pokemon.getSpecialDefenseLevel();
	}
	
	public int getSpeedLevel() {
		return pokemon.getSpeedLevel();
	}
	
	public int getAccuracyLevel() {
		return pokemon.getAccuracyLevel();
	}
	
	public int getEvasionLevel() {
		return pokemon.getEvasionLevel();
	}
	
	
	public void setAttackLevel(int attack) {
		pokemon.setAttackLevel(attack);
	}
	
	public void setDefenseLevel(int defense) {
		pokemon.setDefenseLevel(defense);
	}
	
	public void setSpecialAttackLevel(int specialAttack) {
		pokemon.setSpecialAttackLevel(specialAttack);
	}
	
	public void setSpecialDefenseLevel(int specialDefense) {
		pokemon.setSpecialDefenseLevel(specialDefense);
	}
	
	public void setSpeedLevel(int speed) {
		pokemon.setSpeedLevel(speed);
	}
	
	public void getAccuracyLevel(int accuracy) {
		pokemon.setAccuracyLevel(accuracy);
	}
	
	public void setEvasionLevel(int evasion) {
		pokemon.setEvasionLevel(evasion);
	}
	
	public CausedDamage getLastTakenDamage() {
		return pokemon.getLastDamage();
	}
	
	public boolean willSwitch() {
		return pokemon.willSwitch();
	}
	
	public boolean willDamage() {
		return pokemon.willDamage();
	}

}
