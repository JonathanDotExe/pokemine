package at.jojokobi.pokemine.battle;

public class RestAction extends BattleAction{

	public RestAction(Battle battle, PokemonContainer performer, PokemonContainer defender) {
		super(battle, performer, defender);
	}

	@Override
	public void init() {
		
	}

	@Override
	public String[] startPerform() {
		return new String[] {getPerformer().getPokemon().getName() + " needs to rest!"};
	}

	@Override
	public String[] tickPerform() {
		return null;
	}

	@Override
	public String[] stopPerform() {
		return null;
	}

}
