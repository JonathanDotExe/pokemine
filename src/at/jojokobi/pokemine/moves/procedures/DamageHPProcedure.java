package at.jojokobi.pokemine.moves.procedures;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.DamageClass;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.suppliers.FixedNumberSupplier;
import at.jojokobi.pokemine.moves.procedures.suppliers.MovePowerSupplier;
import at.jojokobi.pokemine.moves.procedures.suppliers.NumberSupplier;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;

public class DamageHPProcedure extends SingleUserProcedure {
//
	private NumberSupplier hp;
	private DamageClass defenseDamageClass;

	public DamageHPProcedure(boolean performer, NumberSupplier hp, DamageClass defenseDamageClass) {
		super(performer);
		if (hp == null) {
			hp = new MovePowerSupplier();
		}
		this.hp = hp;
		this.defenseDamageClass = defenseDamageClass;
	}

	@Override
	public void singlePerform(Battle battle, MoveInstance instance, MoveInitializer init, ProcedurePokemon defender,
			ProcedurePokemon performer, int round) {
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
		
		defender.damageHP(performer, hp.get(battle, instance, performer, defender).intValue(), defenseDamageClass == null ? instance.getMove().getDamageClass() : defenseDamageClass);
//		performer.setHealth(performer.getHealth() - Math.round(damage * recoil));
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		save(map);
		map.put("hp", hp);
		if (defenseDamageClass != null) {
			map.put("defenseDamageClass", defenseDamageClass + "");
		}
		return map;
	}
	
	public static DamageHPProcedure deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		DamageHPProcedure proc = new DamageHPProcedure(false, tMap.get("hp", NumberSupplier.class, new FixedNumberSupplier(0)), map.containsKey("defenseDamageClass") ? tMap.getEnum("defenseDamageClass", DamageClass.class, null) : null);
		proc.load(map);
		return proc;
	}

	@Override
	public String toString() {
		return "DamageHPProcedure [hp=" + hp + ", defenseDamageClass=" + defenseDamageClass + "]";
	}
	
	

}
