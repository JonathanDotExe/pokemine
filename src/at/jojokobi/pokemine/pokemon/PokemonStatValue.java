package at.jojokobi.pokemine.pokemon;

import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public enum PokemonStatValue {
	ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED, ACCURACY, EVASAION;
	
	public int getValue (ProcedurePokemon pokemon) {
		int value = 0;
		switch (this) {
		case ATTACK:
			value = pokemon.getAttackLevel();
			break;
		case DEFENSE:
			value = pokemon.getDefenseLevel();
			break;
		case SPECIAL_ATTACK:
			value = pokemon.getSpecialAttackLevel();
			break;
		case SPECIAL_DEFENSE:
			value = pokemon.getSpecialAttackLevel();
			break;
		case SPEED:
			value = pokemon.getSpeedLevel();
			break;
		case ACCURACY:
			value = pokemon.getAccuracyLevel();
			break;
		case EVASAION:
			value = pokemon.getEvasionLevel();
			break;
		}
		return value;
	}
	
	public void setValue (ProcedurePokemon pokemon, int value) {
		switch (this) {
		case ATTACK:
			pokemon.setAttackLevel(value);
			break;
		case DEFENSE:
			pokemon.setDefenseLevel(value);
			break;
		case SPECIAL_ATTACK:
			pokemon.setSpecialAttackLevel(value);
			break;
		case SPECIAL_DEFENSE:
			pokemon.setSpecialDefenseLevel(value);
			break;
		case SPEED:
			pokemon.setSpeedLevel(value);
			break;
		case ACCURACY:
			pokemon.setAccuracyLevel(value);
			break;
		case EVASAION:
			pokemon.setEvasionLevel(value);
			break;
		}
	}
	
	public void addValue (ProcedurePokemon pokemon, int value) {
		switch (this) {
		case ATTACK:
			pokemon.addAttackLevel(value);
			break;
		case DEFENSE:
			pokemon.addDefenseLevel(value);
			break;
		case SPECIAL_ATTACK:
			pokemon.addSpecialAttackLevel(value);
			break;
		case SPECIAL_DEFENSE:
			pokemon.addSpecialDefenseLevel(value);
			break;
		case SPEED:
			pokemon.addSpeedLevel(value);
			break;
		case ACCURACY:
			pokemon.addAccuracyLevel(value);
			break;
		case EVASAION:
			pokemon.addEvasionLevel(value);
			break;
		}
	}
}
