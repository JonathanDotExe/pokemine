package at.jojokobi.pokemine.moves.movescriptold;

import static at.jojokobi.pokemine.moves.MoveHandler.*;

import at.jojokobi.kiwiscript.NumberUtils;
import at.jojokobi.kiwiscript.Value;
import at.jojokobi.kiwiscript.VariableHandler;
import at.jojokobi.kiwiscript.commands.FunctionCommand;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.DamageClass;
import at.jojokobi.pokemine.moves.Move;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.pokemon.MathUtil;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonType;
@Deprecated
public class DamageCommand extends FunctionCommand{

	public static final String NAME = "damage";
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Object execute(Value[] args, VariableHandler handler) {
		Pokemon performer = (Pokemon) handler.getVariable(PERFORMER).getValue();
		Move move = ((MoveInstance) handler.getVariable(MOVE).getValue()).getMove();
		Battle battle = (Battle) handler.getVariable(BATTLE).getValue();
		Pokemon defender = (Pokemon) handler.getVariable(DEFENDER).getValue();
		float power = move.getPower();
		int damageType = POWER;
		//Defender
		if (args.length > 0 && 	args[0].getValue() instanceof Pokemon) {
			defender = (Pokemon) args[0].getValue();
		}
		//Power
		if (args.length > 1 && NumberUtils.isInteger(args[1].getValue().toString())) {
			power = Integer.parseInt(args[1].getValue().toString());
		}
		//Damage Type
		if (args.length > 2 && NumberUtils.isInteger(args[2].getValue().toString())) {
			damageType = Integer.parseInt(args[2].getValue().toString());
		}
		//Damage
		float damage = 0;
		switch (damageType) {
		case POWER:
			//Stats and Effectivity
			int attack = move.getDamageClass() == DamageClass.PHYSICAL ? performer.getAttack() : performer.getSpecialAttack();
			int defense = move.getDamageClass() == DamageClass.PHYSICAL ? defender.getDefense() : defender.getSpecialDefense();
			boolean stab = performer.getSpecies().hasType(move.getType());
			boolean critical = Math.random() < Move.getCriticalChance(move.getCriticalRate());
			float effectivity = 1.0f;
			for (PokemonType type : defender.getSpecies().getTypes()) {
				effectivity *= type.getEffectivityMultiplier(move.getType());
			}
			//Factor 1
			float f1 = 1;
			if (performer.getPrimStatChange() != null) {
				if (move.getDamageClass() == DamageClass.PHYSICAL) {
					f1 = performer.getPrimStatChange().getPhysicalDamageModifier();
				}
				else {
					f1 = performer.getPrimStatChange().getSpecialDamageModifier();
				}
			}
			//Damage
			damage = MathUtil.calcDamage(performer.getLevel(), power, attack, defense) * (critical ? 1.5f : 1) * f1 * (stab ? 1.5f : 1) * effectivity * 1;
			//Message
			if (effectivity > 1) {
				battle.sendBattleMessage("It's super effective!");
			}
			else if (effectivity < 1) {
				battle.sendBattleMessage("It's not very effective effective!");
			}
			if (critical) {
				battle.sendBattleMessage("A critical hit!");
			}
			if (stab) {
				battle.sendBattleMessage(performer.getName() + " did more damage because of the STAB bonus!");
			}
			battle.sendBattleMessage("Effectivity: " + effectivity);
			break;
		case HP:
			damage = power;
			break;
		case PERCENT:
			damage = defender.getHealth()*power;
			break;
		case WHOLE_PERCENT:
			damage = defender.getMaxHealth()*power;
			break;
		}
		defender.setHealth(defender.getHealth() - Math.round(damage));
		return damage;
	}

}
