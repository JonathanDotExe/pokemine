package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.DamageClass;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.suppliers.MovePowerSupplier;
import at.jojokobi.pokemine.moves.procedures.suppliers.NumberSupplier;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class DamageProcedure extends SingleUserProcedure implements NumberSupplier{

	private float recoil = 0;
	private DamageClass defenseDamageClass;
	private NumberSupplier power;

	public DamageProcedure(boolean performer, float recoil, NumberSupplier power, DamageClass defenseDamageClass) {
		super(performer);
		this.recoil = recoil;
		this.defenseDamageClass = defenseDamageClass;
		if (power == null) {
			power = new MovePowerSupplier(); 
		}
		this.power = power;
	}

	@Override
	public void singlePerform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon defender, ProcedurePokemon performer, int round) {
//		// Damage
//		float damage = 0;
//		Move move = instance.getMove();
//		// Stats and Effectivity
//		int attack = move.getDamageClass() == DamageClass.PHYSICAL ? performer.getAttack() : performer.getSpecialAttack();
//		int defense;
//		if (defenseDamageClass == null) {
//			defense = move.getDamageClass() == DamageClass.PHYSICAL ? defender.getDefense() : defender.getSpecialDefense();
//		}
//		else {
//			defense = defenseDamageClass == DamageClass.PHYSICAL ? defender.getDefense() : defender.getSpecialDefense();
//		}
//		boolean stab = performer.hasType(move.getType());
//		boolean critical = Math.random() < Move.getCriticalChance(move.getCriticalRate());
//		float effectivity = 1.0f;
//		for (PokemonType type : defender.getSpecies().getTypes()) {
//			effectivity *= type.getEffectivityMultiplier(move.getType());
//		}
//		// Factor 1
//		float f1 = 1;
//		if (performer.getPrimStatChange() != null) {
//			if (move.getDamageClass() == DamageClass.PHYSICAL) {
//				f1 = performer.getPrimStatChange().getPhysicalDamageModifier();
//			} else {
//				f1 = performer.getPrimStatChange().getSpecialDamageModifier();
//			}
//		}
//		// Damage
//		damage = MathUtil.calcDamage(performer.getLevel(), move.getPower() * multiplier, attack, defense) * (critical ? 1.5f : 1) * f1
//				* (stab ? 1.5f : 1) * effectivity * 1;
//		
//		// Message
//		if (effectivity > 1) {
//			battle.sendBattleMessage("It's super effective!");
//		} else if (effectivity < 1) {
//			battle.sendBattleMessage("It's not very effective effective!");
//		}
//		if (critical) {
//			battle.sendBattleMessage("A critical hit!");
//		}
//		if (stab) {
//			battle.sendBattleMessage(performer.getName() + " did more damage because of the STAB bonus!");
//		}
//		battle.sendBattleMessage("Effectivity: " + effectivity);
//
//		int damageInt = Math.round(damage);
//		defender.setLastDamage(new CausedDamage(damageInt, move.getDamageClass()));
//		defender.setHealth(defender.getHealth() - damageInt);
//		performer.setHealth(defender.getHealth() - Math.round(damage * recoil));
		
		get(battle, instance, performer, defender);
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		save(map);
		map.put("recoil", recoil);
		map.put("power", power);
		if (defenseDamageClass != null) {
			map.put("defenseDamageClass", defenseDamageClass + "");
		}
		return map;
	}
	
	public static DamageProcedure deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		DamageProcedure proc =  new DamageProcedure(false, tMap.getFloat("recoil"), tMap.get("power", NumberSupplier.class, null), map.containsKey("defenseDamageClass") ? tMap.getEnum("defenseDamageClass", DamageClass.class, null) : null);
		proc.load(map);
		return proc;
	}

	@Override
	public String toString() {
		return "DamageProcedure [recoil=" + recoil + ", defenseDamageClass=" + defenseDamageClass + ", power=" + power
				+ "]";
	}

	@Override
	public Number get(Battle battle, MoveInstance instance, ProcedurePokemon performer, ProcedurePokemon defender) {
		if (isPerformer()) {
			defender = performer;
		}
		int damage = defender.damage(performer, power.get(battle, instance, performer, defender).intValue(), instance.getMove().getType(), instance.getMove().getCriticalRate(), instance.getMove().getDamageClass(), defenseDamageClass != null ? defenseDamageClass : instance.getMove().getDamageClass());
		performer.setHealth(performer.getHealth() - Math.round(damage * recoil));
		return damage;
	}
	
	

}
