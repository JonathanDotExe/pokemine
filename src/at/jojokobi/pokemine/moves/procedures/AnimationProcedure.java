package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class AnimationProcedure implements MoveProcedure{

	private String animation;
	
	
	public AnimationProcedure(String animation) {
		super();
		this.animation = animation;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("animation", animation);
		return map;
	}
	
	public AnimationProcedure deserialize (Map<String, Object> map) {
		return new AnimationProcedure(map.get("animation") + "");
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		init.setAnimation(animation);
	}

	@Override
	public String toString() {
		return "AnimationProcedure [animation=" + animation + "]";
	}

	
	
}
