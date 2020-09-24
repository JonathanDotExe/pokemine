package at.jojokobi.pokemine.pokemon.entity.ability;

public enum PokemonEntityAbilityType {
	HEAL_PLAYER, HEAL_POKEMON, SLOW_MONSTER, GROWTH_BOOST, SWIM_BOOST;
	
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
		case GROWTH_BOOST:
			name = "Growth Boost";
			break;
		case SWIM_BOOST:
			name = "Swim Boost";
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
		case GROWTH_BOOST:
			ability = new GrowthBoostAbility();
			break;
		case SWIM_BOOST:
			ability = new SwimBoostAbility();
			break;
		}
		return ability;
	}
	
}
