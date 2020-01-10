package at.jojokobi.pokemine.battle.effects;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class BattleEffectFactory {

	private static BattleEffectFactory instance;
	
	public static BattleEffectFactory getInstance () {
		if (instance == null) {
			instance = new BattleEffectFactory();
		}
		return instance;
	}
	
	private BattleEffectFactory() {
		
	}
	
	public BattleEffect create (Class<?> clazz, Battle battle, ProcedurePokemon performer, ProcedurePokemon defender) {
		BattleEffect effect = null;
		switch (clazz.getSimpleName()) {
		case "DestinyBond":
			effect = new DestinyBond(battle, performer);
			break;
		case "Safeguard":
			effect = new Safeguard(battle, performer.getOwner());
			break;
		}
		return effect;
	}

}
