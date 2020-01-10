package at.jojokobi.pokemine.moves;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.Identifiable;
import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.item.CustomItem;
import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.items.Buyable;
import at.jojokobi.pokemine.items.TM;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.DamageProcedure;
import at.jojokobi.pokemine.moves.procedures.LegacyMoveScriptProcedure;
import at.jojokobi.pokemine.moves.procedures.MoveProcedure;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.PokemonType;

public class Move implements Identifiable, Buyable, ConfigurationSerializable{
	
	public static final String MOVE_NAME_ELEMENT = "name";
	public static final String MOVE_IDENTIFER_ELEMENT = "identifier";
	public static final String MOVE_TYPE_ELEMENT = "type";
	public static final String MOVE_DAMAGE_CLASS_ELEMENT = "damageClass";
	public static final String MOVE_LEARNING_METHOD_ELEMENT = "learningMethod";
	public static final String MOVE_PRICE_ELEMENT = "price";
	public static final String MOVE_MIN_LEVEL_ELEMENT = "minLevel";
	public static final String MOVE_POWER_ELEMENT = "power";
	public static final String MOVE_ACCURACY_ELEMENT = "accuracy";
	public static final String MOVE_PP_ELEMENT = "pp";
	public static final String MOVE_MAX_PP_ELEMENT = "maxPp";
	public static final String MOVE_PRIORITY_ELEMENT = "priority";
	public static final String MOVE_CRITICAL_RATE_ELEMENT = "criticalRate";
	public static final String MOVE_ANIMATION_ELEMENT = "animation";
	public static final String MOVE_PROCEDURE_ELEMENT = "procedure";
	public static final String MOVE_PRE_EXECUTE_PROCEDURE_ELEMENT = "preExecuteProcedure";
	public static final String MOVE_INITIALIZE_PROCEDURE_ELEMENT = "initializeProcedure";
	

	public static Move deserialize (Map<String, Object> map) {
		NamespacedEntry entry = null;
		if (map.get(MOVE_IDENTIFER_ELEMENT) instanceof NamespacedEntry) {
			entry = (NamespacedEntry) map.get(MOVE_IDENTIFER_ELEMENT);
		}
		else {
			entry = new NamespacedEntry(PokeminePlugin.POKEMINE_NAMESPACE, "move");
		}
		Move move = new Move(entry.getNamespace(), entry.getIdentifier());
		move.setName(map.get(MOVE_NAME_ELEMENT) + "");
		try {
			move.setType(PokemonType.valueOf((map.get(MOVE_TYPE_ELEMENT) + "").toUpperCase()));
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		try {
			move.setDamageClass(DamageClass.valueOf((map.get(MOVE_DAMAGE_CLASS_ELEMENT) + "").toUpperCase()));
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		try {
			move.setLearningMethod(LearningMethod.valueOf((map.get(MOVE_LEARNING_METHOD_ELEMENT) + "").toUpperCase()));
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		try {
			move.setPrice(Integer.parseInt(map.get(MOVE_PRICE_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			move.setMinLevel(Byte.parseByte(map.get(MOVE_MIN_LEVEL_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			move.setPower(Integer.parseInt(map.get(MOVE_POWER_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			move.setAccuracy(Float.parseFloat(map.get(MOVE_ACCURACY_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			move.setPp(Integer.parseInt(map.get(MOVE_PP_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			move.setMaxPp(Integer.parseInt(map.get(MOVE_MAX_PP_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			move.setPriority(Integer.parseInt(map.get(MOVE_PRIORITY_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			move.setCriticalRate(Integer.parseInt(map.get(MOVE_CRITICAL_RATE_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		move.setAnimation(map.get(MOVE_ANIMATION_ELEMENT) + "");
		if (map.get(MOVE_PROCEDURE_ELEMENT) instanceof MoveProcedure) {
			MoveProcedure procedure = (MoveProcedure) map.get(MOVE_PROCEDURE_ELEMENT);
			move.setProcedure(procedure);
			if (procedure instanceof LegacyMoveScriptProcedure) {
				String init = ((LegacyMoveScriptProcedure) procedure).getInitializeScript();
				String preExecute = ((LegacyMoveScriptProcedure) procedure).getPreExecuteScript();
				move.setInitializeProcedure(new LegacyMoveScriptProcedure(init, init, init));
				move.setPreExecuteProcedure(new LegacyMoveScriptProcedure(preExecute, preExecute, preExecute));
			}
		}
		if (map.get(MOVE_PRE_EXECUTE_PROCEDURE_ELEMENT) instanceof MoveProcedure) {
			MoveProcedure procedure = (MoveProcedure) map.get(MOVE_PRE_EXECUTE_PROCEDURE_ELEMENT);
			move.setPreExecuteProcedure(procedure);
		}
		if (map.get(MOVE_INITIALIZE_PROCEDURE_ELEMENT) instanceof MoveProcedure) {
			MoveProcedure procedure = (MoveProcedure) map.get(MOVE_INITIALIZE_PROCEDURE_ELEMENT);
			move.setInitializeProcedure(procedure);
		}
		
		return move;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put(MOVE_NAME_ELEMENT, name);
		map.put(MOVE_IDENTIFER_ELEMENT, new NamespacedEntry(namespace, identifier));
		map.put(MOVE_TYPE_ELEMENT, type + "");
		map.put(MOVE_DAMAGE_CLASS_ELEMENT, damageClass + "");
		map.put(MOVE_LEARNING_METHOD_ELEMENT, learningMethod + "");
		map.put(MOVE_PRICE_ELEMENT, price);
		map.put(MOVE_MIN_LEVEL_ELEMENT, minLevel);
		map.put(MOVE_POWER_ELEMENT, power);
		map.put(MOVE_ACCURACY_ELEMENT, accuracy);
		map.put(MOVE_PP_ELEMENT, pp);
		map.put(MOVE_MAX_PP_ELEMENT, maxPp);
		map.put(MOVE_PRIORITY_ELEMENT, priority);
		map.put(MOVE_CRITICAL_RATE_ELEMENT, criticalRate);
		map.put(MOVE_ANIMATION_ELEMENT, animation);
		map.put(MOVE_PROCEDURE_ELEMENT, procedure);
		map.put(MOVE_PRE_EXECUTE_PROCEDURE_ELEMENT, preExecuteProcedure);
		map.put(MOVE_INITIALIZE_PROCEDURE_ELEMENT, initializeProcedure);
		return map;
	}
	
//	public static final String MOVE_EFFECTS_ELEMENT = "effects";
//	public static final String MOVE_INITIALIZE_ELEMENT = "initialize";
//	public static final String MOVE_PRE_EXECUTE_ELEMENT = "pre_execute";
	
	private String name = "Pound";
	private final String namespace;
	private final String identifier;
	private PokemonType type = PokemonType.NORMAL;
	private DamageClass damageClass = DamageClass.PHYSICAL;
	private int power = 40;
	private float accuracy = 1.0f;
	private int pp = 35;
	private int maxPp  = 56;
	private int priority = 0;
	private int criticalRate = 1;
	private String animation = "hit";
	
	private LearningMethod learningMethod = LearningMethod.NONE;
	private int price = 0;
	private byte minLevel = 0;
	
//	private String initializeScript = "";
//	private String preExecuteScript = "";
//	private String script = "move.damage();";
	private MoveProcedure procedure = new DamageProcedure(false, 0, null, null);
	private MoveProcedure preExecuteProcedure = null;
	private MoveProcedure initializeProcedure = null;
	
//	private List<CommandPlaceholder> commands = new ArrayList<>();
	
	
	public Move(String namespace, String identifier) {
		this.namespace = namespace;
		this.identifier = identifier;
	}
	
	public String getName() {
		return name;
	}
	public PokemonType getType() {
		return type;
	}
	public DamageClass getDamageClass() {
		return damageClass;
	}
	public int getPower() {
		return power;
	}
	public float getAccuracy() {
		return accuracy;
	}
	public int getPp() {
		return pp;
	}
	public Move setName(String name) {
		this.name = name;
		return this;
	}
	public Move setType(PokemonType type) {
		this.type = type;
		return this;
	}
	public Move setDamageClass(DamageClass damageClass) {
		this.damageClass = damageClass;
		return this;
	}
	public Move setPower(int power) {
		this.power = power;
		return this;
	}
	public Move setAccuracy(float accuracy) {
		this.accuracy = accuracy;
		return this;
	}
	public Move setPp(int pp) {
		this.pp = pp;
		return this;
	}
//	public void addCommand (CommandPlaceholder command) {
//		if (command != null) {
//			commands.add(command);
//		}
//	}
//	public String getScript() {
//		return script;
//	}
//	public void setScript(String script) {
//		this.script = script;
//	}
	
	@Override
	public String getIdentifier() {
		return identifier;
	}
//	@Override
//	public void setIdentifier(String identifier) {
//		this.identifier = identifier;
//	}
	
	public MoveProcedure getProcedure() {
		return procedure;
	}

	public Move setProcedure(MoveProcedure procedure) {
		this.procedure = procedure;
		return this;
	}

	@Override
	public String toString() {
		return getName() + " [" + getType() +"]";
	}
	
	public MoveInitializer initialize(Battle battle, MoveInstance instance, ProcedurePokemon performer, ProcedurePokemon defender, int round) {
		MoveInitializer init = new MoveInitializer(this);
//		procedure.initialize(battle, init, instance, performer, defender, round);
		if (initializeProcedure != null) {
			initializeProcedure.perform(battle, instance, init, performer, defender, round);
		}
		return init;
	}

	public void preExecute (Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer, ProcedurePokemon defender, int round) {
//		procedure.preExecute(battle, instance, init, performer, defender, round);
		if (preExecuteProcedure != null) {
			preExecuteProcedure.perform(battle, instance, init, performer, defender, round);
		}
	}
	
	public void perform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon performer, ProcedurePokemon defender, int round) {
		if (procedure != null) {
			procedure.perform(battle, instance, init, performer, defender, round);
		}
		
//		Interpreter interpreter = new Interpreter(commands);
//		VariableHandler handler = interpreter.getVariableHandler();
//		handler.addVariable(new Variable("attack", ATTACK, true));
//		handler.addVariable(new Variable("defense", DEFENSE, true));
//		handler.addVariable(new Variable("special_attack",SPECIAL_ATTACK, true));
//		handler.addVariable(new Variable("special_defense", SPECIAL_DEFENSE, true));
//		handler.addVariable(new Variable("speed", SPEED, true));
//		
//		handler.addVariable(new Variable("burn", BURN, true));
//		handler.addVariable(new Variable("freeze", FREEZE, true));
//		handler.addVariable(new Variable("heavy_poison", HEAVY_POISON, true));
//		handler.addVariable(new Variable("poison", POISON, true));
//		handler.addVariable(new Variable("paralysis", PARALYSIS, true));
//		handler.addVariable(new Variable("sleep", SLEEP, true));
//		
//		handler.addVariable(new Variable("flinch", FLINCH, true));
//		handler.addVariable(new Variable("confusion", CONFUSION, true));
//		handler.addVariable(new Variable("drowsiness", DROWSINESS, true));
//		handler.addVariable(new Variable("wrapped", WRAPPED, true));
//		
//		handler.addVariable(new Variable("power", POWER, true));
//		handler.addVariable(new Variable("hp", HP, true));
//		handler.addVariable(new Variable("percent", PERCENT, true));
//		handler.addVariable(new Variable("whole_percent", WHOLE_PERCENT, true));
//		
//		handler.addVariable(new Variable(PERFORMER, performer, true));
//		handler.addVariable(new Variable(DEFENDER, defender.getPokemon(), true));
//		handler.addVariable(new Variable(DEFENDER_CONTAINER, defender, true));
//		handler.addVariable(new Variable(BATTLE, battle, true));
//		handler.addVariable(new Variable(MOVE, instance, true));
//		handler.addVariable(new Variable(ROUND, round, true));
//		
//		System.out.println("Round: " + round);
//		
//		handler.addVariable(new Variable(PERFORMER + VAR_SEPARATOR + POKEMON_LEVEL, performer.getLevel(), true));
//		handler.addVariable(new Variable(DEFENDER + VAR_SEPARATOR + POKEMON_LEVEL, defender.getPokemon().getLevel(), true));
//		interpreter.run();
		
		
//		for (CommandPlaceholder command : commands) {
//			command.execute(battle, performer, defender);
//		}
//		Random random = new Random();
//		int attack = getDamageClass() == DamageClass.PHYSICAL ? performer.getAttack() : performer.getSpecialAttack();
//		int defense = getDamageClass() == DamageClass.PHYSICAL ? defender.getDefense() : defender.getSpecialDefense();
//		int z = 100 - (random.nextInt(16));
//		float damage = ((performer.getLevel() * (2.0f/5.0f) + 2) * getPower() * (attack/(50.0f*defense))*1 + 2) * 1 * 1 * (z/100.0f) * 1 * 1 * 1 * 1;
//		defender.setHealth(defender.getHealth() - Math.round(damage));
	}
	
	public String getAnimation() {
		return animation;
	}
	public Move setAnimation(String animation) {
		this.animation = animation;
		return this;
	}
	
	public static float getCriticalChance (int criticalRate) {
		float chance = 0.046f;
		if (criticalRate >= 4) {
			chance = 1;
		}
		else if (criticalRate >= 3) {
			chance = 0.5f;
		}
		else if (criticalRate >= 2) {
			chance = 0.125f;
		}
		return chance;
	}
	public int getPriority() {
		return priority;
	}
	public Move setPriority(int priority) {
		this.priority = priority;
		return this;
	}
	public int getCriticalRate() {
		return criticalRate;
	}
	public Move setCriticalRate(int criticalRate) {
		this.criticalRate = criticalRate;
		return this;
	}
//	public String getInitializeScript() {
//		return initializeScript;
//	}
//	public void setInitializeScript(String initializeScript) {
//		this.initializeScript = initializeScript;
//	}
//
//	public String getPreExecuteScript() {
//		return preExecuteScript;
//	}
//
//	public void setPreExecuteScript(String preExecuteScript) {
//		this.preExecuteScript = preExecuteScript;
//	}

	@Override
	public String getNamespace() {
		return namespace;
	}

	public LearningMethod getLearningMethod() {
		return learningMethod;
	}

	public Move setLearningMethod(LearningMethod learningMethod) {
		this.learningMethod = learningMethod;
		return this;
	}

	public int getPrice() {
		return price;
	}

	public Move setPrice(int price) {
		this.price = price;
		return this;
	}

	public byte getMinLevel() {
		return minLevel;
	}

	public Move setMinLevel(byte minLevel) {
		this.minLevel = minLevel;
		return this;
	}

	@Override
	public int getBuyPrice() {
		return getPrice();
	}

	@Override
	public byte getMinBuyLevel() {
		return getMinLevel();
	}
	
	@Override
	public int getSellPrice() {
		return 0;
	}
	
	@Override
	public ItemStack getShopIcon() {
		return MoveUtil.itemFromMove(new MoveInstance(this));
	}
	
	@Override
	public ItemStack getBoughtItem() {
		CustomItem item = ItemHandler.getCustomItem(PokeminePlugin.POKEMINE_NAMESPACE, TM.IDENTIFIER);
		ItemStack bought = null;
		if (item instanceof TM) {
			bought = ((TM) item).getItem(this);
		}
		return bought;
	}

	public int getMaxPp() {
		return maxPp;
	}

	public void setMaxPp(int maxPp) {
		this.maxPp = maxPp;
	}

	public MoveProcedure getPreExecuteProcedure() {
		return preExecuteProcedure;
	}

	public void setPreExecuteProcedure(MoveProcedure preExecuteProcedure) {
		this.preExecuteProcedure = preExecuteProcedure;
	}

	public MoveProcedure getInitializeProcedure() {
		return initializeProcedure;
	}

	public void setInitializeProcedure(MoveProcedure initializeProcedure) {
		this.initializeProcedure = initializeProcedure;
	}
	
}
