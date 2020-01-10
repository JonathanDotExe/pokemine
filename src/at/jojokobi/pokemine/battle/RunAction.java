package at.jojokobi.pokemine.battle;


public class RunAction extends BattleAction {

	public RunAction(Battle battle, PokemonContainer performer, PokemonContainer defender) {
		super(battle, performer, defender);
		setPriority(5);
	}

	@Override
	public String[] startPerform() {
		String[] text = {getPerformer() + " escaped!"};
		if (getPerformer().canSwitch()) {
			getBattle().end();
		}
		else {
			text = new String[] {getPerformer().getPokemon() + "couldn't escape!"};
		}
		return text;
	}

	@Override
	public String[] tickPerform() {
		return new String[] {};
	}

	@Override
	public String[] stopPerform() {
		return new String[] {};
	}
	
	@Override
	public void init() {
		
	}

}
