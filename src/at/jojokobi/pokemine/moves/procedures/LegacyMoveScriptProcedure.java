package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.bukkit.ChatColor;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.effects.DestinyBond;
import at.jojokobi.pokemine.battle.effects.Safeguard;
import at.jojokobi.pokemine.moves.DamageClass;
import at.jojokobi.pokemine.moves.MoveHandler;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveExecution;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.movescript.ScriptPokemon;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.status.Burn;
import at.jojokobi.pokemine.pokemon.status.Confusion;
import at.jojokobi.pokemine.pokemon.status.Cursed;
import at.jojokobi.pokemine.pokemon.status.Drowsiness;
import at.jojokobi.pokemine.pokemon.status.Flinched;
import at.jojokobi.pokemine.pokemon.status.Freeze;
import at.jojokobi.pokemine.pokemon.status.HeavyPoison;
import at.jojokobi.pokemine.pokemon.status.LeechSeed;
import at.jojokobi.pokemine.pokemon.status.Paralysis;
import at.jojokobi.pokemine.pokemon.status.Poison;
import at.jojokobi.pokemine.pokemon.status.Sleep;
import at.jojokobi.pokemine.pokemon.status.SwitchBlock;
import at.jojokobi.pokemine.pokemon.status.Wrapped;

public class LegacyMoveScriptProcedure implements MoveProcedure {

	private String initializeScript = "";
	private String preExecuteScript = "";
	private String script = "move.damage();";
	
	public LegacyMoveScriptProcedure() {
		
	}

	public LegacyMoveScriptProcedure(String initializeScript, String preExecuteScript, String script) {
		super();
		this.initializeScript = initializeScript;
		this.preExecuteScript = preExecuteScript;
		this.script = script;
	}

//	@Override
//	public void initialize(Battle battle, MoveInitializer init, MoveInstance instance, ProcedurePokemon performer,
//			ProcedurePokemon defender, int round) {
//		// Init Script Engine
//		ScriptEngineManager manager = new ScriptEngineManager();
//		ScriptEngine js = manager.getEngineByName("js");
//		// Put Vars
//		js.put(MoveHandler.INITIALZER, init);
//
//		putConstants(js, instance.getMove().getPower(), round);
//
//		js.put(MoveHandler.PERFORMER, new ScriptPokemon(performer, battle));
//		js.put(MoveHandler.DEFENDER, new ScriptPokemon(defender, battle));
//
//		try {
//			js.eval(initializeScript);
//		} catch (ScriptException e) {
//			performer.messageOwner(ChatColor.RED + "An error occured during executing the move!");
//			performer.messageOwner(ChatColor.RED + e.getMessage());
//			performer.messageOwner(ChatColor.RED + "Please contact an admin!");
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void preExecute(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer,
//			ProcedurePokemon defender, int round) {
//		// Init Script Engine
//		ScriptEngineManager manager = new ScriptEngineManager();
//		ScriptEngine js = manager.getEngineByName("js");
//		// Put Vars
//		MoveExecution exec = new MoveExecution(battle, instance, performer, defender, round);
//		js.put(MoveHandler.INITIALZER, init);
//		js.put(MoveHandler.MOVE_EXECUTION, exec);
//
//		putConstants(js, instance.getMove().getPower(), round);
//
//		js.put(MoveHandler.PERFORMER, new ScriptPokemon(performer, battle));
//		js.put(MoveHandler.DEFENDER, new ScriptPokemon(defender, battle));
//
//		try {
//			js.eval(preExecuteScript);
//		} catch (ScriptException e) {
//			performer.messageOwner(ChatColor.RED + "An error occured during executing the move!");
//			performer.messageOwner(ChatColor.RED + e.getMessage());
//			performer.messageOwner(ChatColor.RED + "Please contact an admin!");
//			e.printStackTrace();
//		}
//	}

	@Override
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer, ProcedurePokemon defender, int round) {
		//Init Script Engine
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine js = manager.getEngineByName("js");
		//Put Vars
		MoveExecution exec = new MoveExecution(battle, instance, performer, defender, round);
		js.put(MoveHandler.MOVE_EXECUTION, exec);
		// Put Vars
		js.put(MoveHandler.INITIALZER, init);
		
		putConstants(js, instance.getMove().getPower(), round);
		
		js.put(MoveHandler.PERFORMER, new ScriptPokemon(performer));
		js.put(MoveHandler.DEFENDER, new ScriptPokemon(defender));

		
		try {
			js.eval(script);
		} catch (ScriptException e) {
			performer.messageOwner(ChatColor.RED + "An error occured during executing the move!");
			performer.messageOwner(ChatColor.RED + e.getMessage());
			performer.messageOwner(ChatColor.RED + "Please contact an admin!");
			e.printStackTrace();
		}
	}

	private void putConstants(ScriptEngine engine, int power, int round) {
		engine.put(MoveExecution.ATTACK, MoveExecution.ATTACK);
		engine.put(MoveExecution.DEFENSE, MoveExecution.DEFENSE);
		engine.put(MoveExecution.SPECIAL_ATTACK, MoveExecution.SPECIAL_ATTACK);
		engine.put(MoveExecution.SPECIAL_DEFENSE, MoveExecution.SPECIAL_DEFENSE);
		engine.put(MoveExecution.SPEED, MoveExecution.SPEED);
		engine.put(MoveExecution.EVASION, MoveExecution.EVASION);
		engine.put(MoveExecution.ACCURACY, MoveExecution.ACCURACY);

		engine.put(Burn.NAME, Burn.NAME);
		engine.put(Freeze.NAME, Freeze.NAME);
		engine.put(HeavyPoison.NAME, HeavyPoison.NAME);
		engine.put(Poison.NAME, Poison.NAME);
		engine.put(Paralysis.NAME, Paralysis.NAME);
		engine.put(Sleep.NAME, Sleep.NAME);

		engine.put(Flinched.SCRIPT_NAME, Flinched.SCRIPT_NAME);
		engine.put(Confusion.SCRIPT_NAME, Confusion.SCRIPT_NAME);
		engine.put(Drowsiness.SCRIPT_NAME, Drowsiness.SCRIPT_NAME);
		engine.put(Wrapped.SCRIPT_NAME, Wrapped.SCRIPT_NAME);
		engine.put(SwitchBlock.SCRIPT_NAME, SwitchBlock.SCRIPT_NAME);
		engine.put(Cursed.SCRIPT_NAME, Cursed.SCRIPT_NAME);
		engine.put(LeechSeed.SCRIPT_NAME, LeechSeed.SCRIPT_NAME);

		engine.put(Safeguard.SCRIPT_NAME, Safeguard.SCRIPT_NAME);
		engine.put(DestinyBond.SCRIPT_NAME, DestinyBond.SCRIPT_NAME);

		engine.put(MoveExecution.POWER_DAMAGE, MoveExecution.POWER_DAMAGE);
		engine.put(MoveExecution.HP, MoveExecution.HP);
		engine.put(MoveExecution.PERCENT, MoveExecution.PERCENT);
		engine.put(MoveExecution.WHOLE_PERCENT, MoveExecution.WHOLE_PERCENT);

		engine.put(MoveHandler.ROUND, round);
		engine.put(MoveExecution.POWER, power);

		// Integrate Damage Class
		engine.put("DamageClassClass", DamageClass.class);
		try {
			engine.eval("var DamageClass = DamageClassClass.static;");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public String getInitializeScript() {
		return initializeScript;
	}

	public void setInitializeScript(String initializeScript) {
		this.initializeScript = initializeScript;
	}

	public String getPreExecuteScript() {
		return preExecuteScript;
	}

	public void setPreExecuteScript(String preExecuteScript) {
		this.preExecuteScript = preExecuteScript;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("initializeScript", initializeScript);
		map.put("preExecuteScript", preExecuteScript);
		map.put("script", script);
		return map;
	}
	
	public static LegacyMoveScriptProcedure deserialize (Map<String, Object> map) {
		return new LegacyMoveScriptProcedure(map.get("initializeScript") + "", map.get("preExecuteScript") + "", map.get("script") + "");
	}

}
