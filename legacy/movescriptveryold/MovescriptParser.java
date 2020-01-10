package at.jojokobi.pokemine.moves.movescriptveryold;

import org.w3c.dom.Element;

import at.jojokobi.pokemine.moves.Move;
@Deprecated
public final class MovescriptParser {

	private static final String DAMAGE = "damage";
	
	private static final String ADD_ATTACK = "add_attack";
	private static final String ADD_DEFENSE = "add_defense";
	private static final String ADD_SPECIAL_ATTACK = "add_special_attack";
	private static final String ADD_SPECIAL_DEFENSE = "add_special_defense";
	private static final String ADD_SPEED = "add_speed";
	
	private static final String BURN = "burn";
	private static final String FREEZE = "freeze";
	private static final String HEAVY_POISON = "heavy_poison";
	private static final String SLEEP = "sleep";
	private static final String PARALYSIS = "paralysis";
	private static final String POISON = "poison";
	
	private static final String VALUE = "value";
	private static final String TARGET = "target";
	private static final String CHANCE = "chance";

	private MovescriptParser() {
		
	}
	
	public static Command parse (Element keyword, Move move) {
		Command command = null;
		switch (keyword.getTagName()) {
		case DAMAGE:
			command = new DamageCommand(move);
			break;
		case ADD_ATTACK:
			command = new AddAttackCommand(Integer.parseInt(keyword.getAttribute(VALUE)), Target.fromString(keyword.getAttribute(TARGET)), Float.parseFloat(keyword.getAttribute(CHANCE)));
			break;
		case ADD_DEFENSE:
			command = new AddDefenseCommand(Integer.parseInt(keyword.getAttribute(VALUE)), Target.fromString(keyword.getAttribute(TARGET)), Float.parseFloat(keyword.getAttribute(CHANCE)));
			break;
		case ADD_SPECIAL_ATTACK:
			command = new AddSpecialAttackCommand(Integer.parseInt(keyword.getAttribute(VALUE)), Target.fromString(keyword.getAttribute(TARGET)), Float.parseFloat(keyword.getAttribute(CHANCE)));
			break;
		case ADD_SPECIAL_DEFENSE:
			command = new AddSpecialDefenseCommand(Integer.parseInt(keyword.getAttribute(VALUE)), Target.fromString(keyword.getAttribute(TARGET)), Float.parseFloat(keyword.getAttribute(CHANCE)));
			break;
		case ADD_SPEED:
			command = new AddSpeedCommand(Integer.parseInt(keyword.getAttribute(VALUE)), Target.fromString(keyword.getAttribute(TARGET)), Float.parseFloat(keyword.getAttribute(CHANCE)));
			break;
		case BURN:
			command = new BurnCommand(Target.fromString(keyword.getAttribute(TARGET)), Float.parseFloat(keyword.getAttribute(CHANCE)));
			break;
		case FREEZE:
			command = new FreezeCommand(Target.fromString(keyword.getAttribute(TARGET)), Float.parseFloat(keyword.getAttribute(CHANCE)));
			break;
		case HEAVY_POISON:
			command = new HeavyPoisonCommand(Target.fromString(keyword.getAttribute(TARGET)), Float.parseFloat(keyword.getAttribute(CHANCE)));
			break;
		case POISON:
			command = new PoisonCommand(Target.fromString(keyword.getAttribute(TARGET)), Float.parseFloat(keyword.getAttribute(CHANCE)));
			break;
		case SLEEP:
			command = new SleepCommand(Target.fromString(keyword.getAttribute(TARGET)), Float.parseFloat(keyword.getAttribute(CHANCE)));
			break;
		case PARALYSIS:
			command = new ParalysisCommand(Target.fromString(keyword.getAttribute(TARGET)), Float.parseFloat(keyword.getAttribute(CHANCE)));
			break;
		}
		return command;
	}

}
