package at.jojokobi.pokemine.battle;

import at.jojokobi.pokemine.items.BattleItem;

public class ItemAction extends BattleAction{

	private BattleItem item;
	private PokemonWrapper usedOn;

	public ItemAction(Battle battle, BattleItem item, PokemonContainer performer, PokemonWrapper usedOn, PokemonContainer defender) {
		super(battle, performer, defender);
		this.item = item;
		this.usedOn = usedOn;
		setPriority(5);
	}

	@Override
	public String[] startPerform() {
		item.startUse(usedOn, PokemonWrapper.fromPokemonContainer(getDefender()), getBattle());
		return new String[] {getPerformer().getPokemon().getOwner().getName() + " used " + item.getName() + "!"};
	}

	@Override
	public String[] tickPerform() {
		return new String[] {};
	}

	@Override
	public String[] stopPerform() {
		item.endUse(usedOn, PokemonWrapper.fromPokemonContainer(getDefender()), getBattle());
		return new String[] {};
	}

	@Override
	public void init() {
		
	}

}
