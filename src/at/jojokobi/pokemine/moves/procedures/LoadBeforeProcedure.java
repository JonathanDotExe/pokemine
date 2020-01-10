package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class LoadBeforeProcedure implements MoveProcedure {

	private MoveProcedure loadProcedure;
	private MoveProcedure decorating;

	public LoadBeforeProcedure(MoveProcedure loadProcedure, MoveProcedure decorating) {
		super();
		this.loadProcedure = loadProcedure;
		this.decorating = decorating;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("loadProcedure", loadProcedure);
		map.put("decorating", decorating);
		return map;
	}
	
	

	public static LoadBeforeProcedure deserialize(Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		LoadBeforeProcedure proc = new LoadBeforeProcedure(tMap.get("loadProcedure", MoveProcedure.class, new DoNothingProcedure()), tMap.get("decorating", MoveProcedure.class, new DoNothingProcedure()));
		return proc;
	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
			ProcedurePokemon defender, int round) {
		if (round == 0) {
			loadProcedure.perform(battle, instance, init, performer, defender, round);
			performer.repeatMove(instance, true, round, defender);
		}
		else {
			decorating.perform(battle, instance, init, performer, defender, round);
		}
	}

	@Override
	public String toString() {
		return "LoadBeforeProcedure [loadProcedure=" + loadProcedure + ", decorating=" + decorating + "]";
	}

	public MoveProcedure getLoadProcedure() {
		return loadProcedure;
	}

	public void setLoadProcedure(MoveProcedure loadProcedure) {
		this.loadProcedure = loadProcedure;
	}

	public MoveProcedure getDecorating() {
		return decorating;
	}

	public void setDecorating(MoveProcedure decorating) {
		this.decorating = decorating;
	}

}
