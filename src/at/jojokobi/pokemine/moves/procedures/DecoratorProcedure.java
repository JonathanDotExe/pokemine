package at.jojokobi.pokemine.moves.procedures;

import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public abstract class DecoratorProcedure implements MoveProcedure {
	
	private MoveProcedure decorating;

	public DecoratorProcedure(MoveProcedure decorating) {
		super();
		this.decorating = decorating;
	}

//	@Override
//	public void initialize(Battle battle, MoveInitializer init, MoveInstance instance, ProcedurePokemon performer,
//			ProcedurePokemon defender, int round) {
//		decorating.initialize(battle, init, instance, performer, defender, round);
//	}
//
//	@Override
//	public void preExecute(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
//			ProcedurePokemon defender, int round) {
//		decorating.preExecute(battle, instance, init, performer, defender, round);
//	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer, ProcedurePokemon defender, int round) {
		decorating.perform(battle, instance, init, performer, defender, round);
	}
	
	public void save (Map<String, Object> map) {
		map.put("procedure", decorating);
	}
	
	public void load (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		decorating = tMap.get("procedure", MoveProcedure.class, new DoNothingProcedure());
	}

	public MoveProcedure getDecorating() {
		return decorating;
	}

	public void setDecorating(MoveProcedure decorating) {
		this.decorating = decorating;
	}
	
}
