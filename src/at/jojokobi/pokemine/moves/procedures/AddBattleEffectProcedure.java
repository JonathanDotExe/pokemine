package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.effects.BattleEffect;
import at.jojokobi.pokemine.battle.effects.BattleEffectFactory;
import at.jojokobi.pokemine.battle.effects.Safeguard;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class AddBattleEffectProcedure implements MoveProcedure{
	
	private Class<?> effect;
	
	public AddBattleEffectProcedure(Class<? extends BattleEffect> effect) {
		super();
		this.effect = effect;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put("effect", effect.getName());
		return map;
	}
	
	public static AddBattleEffectProcedure deserialize (Map<String, Object> map) {
		AddBattleEffectProcedure proc = new AddBattleEffectProcedure(Safeguard.class);
		try {
			proc.effect = Class.forName(map.get("effect") + "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return proc;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		BattleEffect effect = BattleEffectFactory.getInstance().create(this.effect, battle, performer, defender);
		if (effect != null) {
			battle.addBattleEffect(effect);
			battle.sendBattleMessage(effect.getAddMessage());
		}
	}

	@Override
	public String toString() {
		return "AddBattleEffectProcedure [effect=" + effect + "]";
	}
	
	

}
