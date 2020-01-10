package at.jojokobi.pokemine.moves.movescript;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.effects.DestinyBond;
import at.jojokobi.pokemine.battle.effects.Safeguard;
import at.jojokobi.pokemine.moves.DamageClass;
import at.jojokobi.pokemine.moves.Move;
import at.jojokobi.pokemine.moves.MoveInstance;
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

public class MoveExecution {
	
	public static final String POWER_DAMAGE = "power_damage";
	public static final String HP = "hp";
	public static final String PERCENT = "percent";
	public static final String WHOLE_PERCENT = "whole_percent";
	
	public static final String POWER = "power";
	
	public static final String ATTACK = "attack";
	public static final String DEFENSE = "defense";
	public static final String SPECIAL_ATTACK = "special_attack";
	public static final String SPECIAL_DEFENSE = "special_defense";
	public static final String SPEED = "speed";
	public static final String ACCURACY = "accuracy";
	public static final String EVASION = "evasion";
	
	private Battle battle;
	private MoveInstance move;
	private ProcedurePokemon performer;
	private ProcedurePokemon defender;
	private int round = 0;
	
	public MoveExecution(Battle battle, MoveInstance move, ProcedurePokemon performer, ProcedurePokemon defender, int round) {
		this.battle = battle;
		this.move = move;
		this.performer = performer;
		this.defender = defender;
		this.round = round;
	}
	
	public void message (String text) {
		battle.sendBattleMessage(text);
	}
	
	public void addStatus (ScriptPokemon sPokemon, String status, float chance) {
		ProcedurePokemon pokemon = sPokemon.getPokemon();
		if (Math.random() < chance && !pokemon.hasSecStatChangeLegacy(status)) {
			switch (status) {
			case Flinched.SCRIPT_NAME:
				pokemon.addSecStatChange(new Flinched());
				battle.sendBattleMessage(pokemon.getName() + " is flinched!");
				break;
			case Confusion.SCRIPT_NAME:
				pokemon.addSecStatChange(new Confusion());
				battle.sendBattleMessage(pokemon.getName() + " is confused!");
				break;
			case Drowsiness.SCRIPT_NAME:
				pokemon.addSecStatChange(new Drowsiness());
				battle.sendBattleMessage(pokemon.getName() + " became drowsy!");
				break;
			case Wrapped.SCRIPT_NAME:
				pokemon.addSecStatChange(new Wrapped(performer));
				battle.sendBattleMessage(pokemon.getName() + " was wrapped!");
				break;
			case SwitchBlock.SCRIPT_NAME:
				pokemon.addSecStatChange(new SwitchBlock());
				battle.sendBattleMessage(pokemon.getName() + " can't escape anymore!");
				break;
			case Cursed.SCRIPT_NAME:
				pokemon.addSecStatChange(new Cursed());
				battle.sendBattleMessage(pokemon.getName() + " was cursed!");
				break;
			case LeechSeed.SCRIPT_NAME:
				pokemon.addSecStatChange(new LeechSeed(performer));
				battle.sendBattleMessage(pokemon.getName() + " was planted!");
				break;
			case Safeguard.SCRIPT_NAME:
				battle.addBattleEffect(new Safeguard(battle, pokemon.getOwner()));
				if (pokemon.isWild()) {
					battle.sendBattleMessage(pokemon.getName() + " is now protected from status changes!");
				}
				else {
					battle.sendBattleMessage(pokemon.getOwner().getName() + "'s team is now protected from status changes!");
				}
				break;
			case DestinyBond.SCRIPT_NAME:
				battle.addBattleEffect(new DestinyBond(battle, pokemon));
				battle.sendBattleMessage(pokemon.getName() + " is trying to take its oponents with it!");
				break;
			default:
				break;
			}
		}
	}
	
	public void addStatus (ScriptPokemon sPokemon, String status) {
		addStatus(sPokemon, status, 1);
	}
	
	public void removeStatus (ScriptPokemon sPokemon, String status, float chance) {
		ProcedurePokemon pokemon = sPokemon.getPokemon();
		if (Math.random() < chance && pokemon.hasSecStatChangeLegacy(status)) {
			pokemon.removeSecStatChangeLegacy(status);
			switch (status) {
			case Flinched.SCRIPT_NAME:
				battle.sendBattleMessage(pokemon.getName() + " is no longer flinched!");
				break;
			case Confusion.SCRIPT_NAME:
				battle.sendBattleMessage(pokemon.getName() + " is no longer confused!");
				break;
			case Drowsiness.SCRIPT_NAME:
				battle.sendBattleMessage(pokemon.getName() + " is no longer drowsy!");
				break;
			case Wrapped.SCRIPT_NAME:
				battle.sendBattleMessage(pokemon.getName() + " is free from the wrap!");
				break;
			case SwitchBlock.SCRIPT_NAME:
				battle.sendBattleMessage(pokemon.getName() + " can escape now!");
				break;
			case Cursed.SCRIPT_NAME:
				battle.sendBattleMessage(pokemon.getName() + " isn't cursed anymore!");
				break;
			case LeechSeed.SCRIPT_NAME:
					battle.sendBattleMessage(pokemon.getName() + " isn't planted anymore!");
					break;
			case Safeguard.SCRIPT_NAME:
				battle.removeBattleEffect(status);
				if (pokemon.isWild()) {
					battle.sendBattleMessage(pokemon.getName() + " is no longer protected from status changes!");
				}
				else {
					battle.sendBattleMessage(pokemon.getOwner().getName() + "'s team is no longer protected from status changes!");
				}
				break;
			case DestinyBond.SCRIPT_NAME:
				battle.removeBattleEffect(status);
				battle.sendBattleMessage(pokemon.getName() + "'s destiny bound was prevented!");
				break;
			default:
				break;
			}
		}
	}
	
	public void removeStatus (ScriptPokemon sPokemon, String status) {
		removeStatus(sPokemon, status, 1);
	}
	
	public float damage(ScriptPokemon sDefender, float power, String damageType, DamageClass damageClass) {
		//Damage
		ProcedurePokemon defender = sDefender.getPokemon();
		float damage = 0;
		Move move = this.move.getMove();
		switch (damageType) {
		case POWER_DAMAGE:
//			//Stats and Effectivity
//			int attack = move.getDamageClass() == DamageClass.PHYSICAL ? performer.getPokemon().getAttack() : performer.getPokemon().getSpecialAttack();
//			int defense = damageClass == DamageClass.PHYSICAL ? defender.getDefense() : defender.getSpecialDefense();
//			boolean stab = performer.getPokemon().getSpecies().hasType(move.getType());
//			boolean critical = Math.random() < Move.getCriticalChance(move.getCriticalRate());
//			float effectivity = 1.0f;
//			for (PokemonType type : defender.getSpecies().getTypes()) {
//				effectivity *= type.getEffectivityMultiplier(move.getType());
//			}
//			//Factor 1
//			float f1 = 1;
//			if (performer.getPokemon().getPrimStatChange() != null) {
//				if (move.getDamageClass() == DamageClass.PHYSICAL) {
//					f1 = performer.getPokemon().getPrimStatChange().getPhysicalDamageModifier();
//				}
//				else {
//					f1 = performer.getPokemon().getPrimStatChange().getSpecialDamageModifier();
//				}
//			}
//			//Damage
//			damage = MathUtil.calcDamage(performer.getPokemon().getLevel(), power, attack, defense) * (critical ? 1.5f : 1) * f1 * (stab ? 1.5f : 1) * effectivity * 1;
//			//Message
//			if (effectivity > 1) {
//				battle.sendBattleMessage("It's super effective!");
//			}
//			else if (effectivity < 1) {
//				battle.sendBattleMessage("It's not very effective effective!");
//			}
//			if (critical) {
//				battle.sendBattleMessage("A critical hit!");
//			}
//			if (stab) {
//				battle.sendBattleMessage(performer.getPokemon().getName() + " did more damage because of the STAB bonus!");
//			}
//			battle.sendBattleMessage("Effectivity: " + effectivity);
			damage = defender.damage(performer, move.getPower(), move.getType(), move.getCriticalRate(), damageClass, damageClass);
			break;
		case HP:
			damage = power;
			break;
		case PERCENT:
			damage = defender.getHealth() * power;
			break;
		case WHOLE_PERCENT:
			damage = defender.getMaxHealth() * power;
			break;
		}
		return damage;
	}
	
	public float damage (ScriptPokemon defender, float power, String damageType) {
		return damage(defender, power, damageType, move.getMove().getDamageClass());
	}
	
	public float damage (ScriptPokemon defender, float power) {
		return damage(defender, power, POWER_DAMAGE);
	}
	
	public float damage (ScriptPokemon defender) {
		return damage(defender, move.getMove().getPower(), POWER_DAMAGE);
	}
	
	public float damage () {
		return damage(new ScriptPokemon(defender), 40, POWER_DAMAGE);
	}
	
	
	
	public void addValue (ScriptPokemon sPokemon, int amount, String status, float chance) {
		ProcedurePokemon pokemon = sPokemon.getPokemon();
		if (Math.random() < chance) {
			switch (status) {
			case ATTACK:
				pokemon.addAttackLevel(amount);
				break;
			case DEFENSE:
				pokemon.addDefenseLevel(amount);
				break;
			case SPECIAL_ATTACK:
				pokemon.addSpecialAttackLevel(amount);
				break;
			case SPECIAL_DEFENSE:
				pokemon.addSpecialDefenseLevel(amount);
				break;
			case SPEED:
				pokemon.addSpeedLevel(amount);
				break;
			case ACCURACY:
				pokemon.addAccuracyLevel(amount);
				break;
			case EVASION:
				pokemon.addEvasionLevel(amount);
				break;
			default:
				break;
			}
		}
	}
	
	public void addValue (ScriptPokemon sPokemon, int amount, String status) {
		addValue(sPokemon, amount, status, 1);
	}
		
	public void repeat (boolean takePP) {
		performer.repeatMove(move, takePP, round, defender);
	}
	
	public void repeat () {
		repeat(true);
	}
	
	
	
	
	public void setStatus (ScriptPokemon sPokemon, String status, float chance) {
		ProcedurePokemon pokemon = sPokemon.getPokemon();
		if (chance > Math.random()) {
			if (status.equals("")) {
				pokemon.clearPrimStatChange();
			}
			else {
				switch (status) {
				case Burn.NAME:
					pokemon.addPrimStatChange(new Burn());
					break;
				case Freeze.NAME:
					pokemon.addPrimStatChange(new Freeze());
					break;
				case HeavyPoison.NAME:
					pokemon.addPrimStatChange(new HeavyPoison());
					break;
				case Paralysis.NAME:
					pokemon.addPrimStatChange(new Paralysis());
					break;
				case Poison.NAME:
					pokemon.addPrimStatChange(new Poison());
					break;
				case Sleep.NAME:
					pokemon.addPrimStatChange(new Sleep());
					break;
				default:
					break;
				}
			}
		}
	}
	
	public void setStatus (ScriptPokemon sPokemon, String status) {
		setStatus(sPokemon, status, 1);
	}
	
	public boolean setValue (ScriptPokemon sPokemon, int amount, String status, float chance) {
		ProcedurePokemon pokemon = sPokemon.getPokemon();
		boolean success = false;
		if (chance > Math.random()) {
			success = true;
			switch (status) {
			case ATTACK:
				pokemon.setAttackLevel(amount);
				break;
			case DEFENSE:
				pokemon.setDefenseLevel(amount);
				break;
			case SPECIAL_ATTACK:
				pokemon.setSpecialAttackLevel(amount);
				break;
			case SPECIAL_DEFENSE:
				pokemon.setSpecialDefenseLevel(amount);
				break;
			case SPEED:
				pokemon.setSpeedLevel(amount);
				break;
			case ACCURACY:
				pokemon.setAccuracyLevel(amount);
				break;
			case EVASION:
				pokemon.setEvasionLevel(amount);
				break;
			default:
				success = false;
				break;
			}
		}
		return success;
	}
	
	public boolean setValue (ScriptPokemon sPokemon, int amount, String status) {
		return setValue(sPokemon, amount, status, 1);
	}
	
	public void switchPokemon (ScriptPokemon oldPokemon, boolean force) {
		oldPokemon.getPokemon().switchRandom(force);
	}
	
	public void switchPokemon (ScriptPokemon oldPokemon) {
		switchPokemon(oldPokemon, true);
	}

}
