package at.jojokobi.pokemine.moves;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class MoveInstance implements ConfigurationSerializable {
	
	private Move move;
	private int pp;
	private int maxPP;
	
	public MoveInstance(Move move) {
		super();
		this.move = move;
		pp = move.getPp();
		maxPP = move.getPp();
	}
	
	@Override
	public String toString() {
		return getMove() + " " + getPp() + "/" + getMaxPP();
	}

	public int getPp() {
		return pp;
	}

	public void setPp(int pp) {
		if (pp > maxPP) {
			this.pp = maxPP;
		}
		else if (pp < 0) {
			this.pp = 0;
		}
		else {
			this.pp = pp;
		}
	}

	public int getMaxPP() {
		return maxPP;
	}

	public void setMaxPP(int maxPP) {
		if (maxPP > move.getMaxPp()) {
			this.maxPP = move.getMaxPp();
		}
		else if (maxPP < move.getPp()) {
			this.maxPP = move.getPp();
		}
		else {
			this.maxPP = maxPP;
		}
	}

	public Move getMove() {
		return move;
	}

	public void perform(Battle battle, MoveInitializer init, ProcedurePokemon performer, ProcedurePokemon defender, int round) {
		getMove().perform(battle, this, init, performer, defender, round);
	}
	
	public void preExecute(Battle battle, MoveInitializer init, ProcedurePokemon performer, ProcedurePokemon defender, int round) {
		getMove().preExecute(battle, this, init, performer, defender, round);
	}
	
	public MoveInitializer initialize(Battle battle, ProcedurePokemon performer, ProcedurePokemon defender, int round) {
		return getMove().initialize(battle, this, performer, defender, round);
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("move", move.toNamespacedEntry());
		map.put("pp", pp);
		map.put("maxPp", maxPP);
		return map;
	}
	
	public static MoveInstance deserialize (Map<String, Object> map) {
		NamespacedEntry entry = null;
		if (map.get("move") instanceof NamespacedEntry) {
			entry = (NamespacedEntry) map.get("move");
		}
		else {
			entry = new NamespacedEntry(PokeminePlugin.POKEMINE_NAMESPACE, "pound");
		}
		MoveInstance instance = new MoveInstance(MoveHandler.getInstance().getItem(entry.getNamespace(), entry.getIdentifier()));
		try {
			instance.setPp(Integer.parseInt(map.get("pp") + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			instance.setMaxPP(Integer.parseInt(map.get("maxPp") + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return instance;
	}
	
}
