package at.jojokobi.pokemine.pokemon.entity.ability;

public enum PokemonEntityAbilityType {
	HEAL_PLAYER;
	
	public String getName() {
		String name = "None";
		switch (this) {
		case HEAL_PLAYER:
			name = "Heal Player";
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
		}
		return ability;
	}
	
}
