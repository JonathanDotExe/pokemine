package at.jojokobi.pokemine.moves.procedures;

import java.util.Map;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public abstract class SingleUserProcedure implements MoveProcedure {

	private boolean performer;

	public SingleUserProcedure(boolean performer) {
		super();
		this.performer = performer;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer, ProcedurePokemon defender,
			int round) {
		singlePerform(battle, instance, init, this.performer ? performer : defender, performer, round);
	}
	
	public void save (Map<String, Object> map) {
		map.put("performer", performer);
	}
	
	public void load (Map<String, Object> map) {
		performer = Boolean.parseBoolean(map.get("performer") + "");
	}

	public boolean isPerformer() {
		return performer;
	}

	protected abstract void singlePerform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon pokemon, ProcedurePokemon performer, int round);
	
}
