package at.jojokobi.pokemine.pokemon.entity.ability;

public enum PokemonEntityAbilityType {
	HEAL_PLAYER, HEAL_POKEMON, SLOW_MONSTER;
	
	public String getName() {
		String name = "None";
		switch (this) {
		case HEAL_PLAYER:
			name = "Heal Player";
			break;
		case HEAL_POKEMON:
			name = "Heal Pokémon";
			break;
		case SLOW_MONSTER:
			name = "Slow Monster";
			break;
		}
		return name;
	}
	
	public PokemonEntityAbility create () {
		PokemonEntityAbility ability = null;
		switch (this) {
		case HEAL_PLAYER:
			ability = new HealPlayerAbility();
			break;
		case HEAL_POKEMON:
			ability = new HealPokemonAbility();
			break;
		case SLOW_MONSTER:
			ability = new SlowMonsterAbility();
			break;
		}
		return ability;
	}
	
}
