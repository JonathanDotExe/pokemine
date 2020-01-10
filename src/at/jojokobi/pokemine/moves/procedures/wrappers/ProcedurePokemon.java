package at.jojokobi.pokemine.moves.procedures.wrappers;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.inventory.ItemStack;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.BattleAction;
import at.jojokobi.pokemine.battle.MoveAction;
import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.battle.RestAction;
import at.jojokobi.pokemine.battle.SwitchAction;
import at.jojokobi.pokemine.moves.DamageClass;
import at.jojokobi.pokemine.moves.Move;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.pokemon.CausedDamage;
import at.jojokobi.pokemine.pokemon.MathUtil;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonType;
import at.jojokobi.pokemine.pokemon.status.PrimStatChange;
import at.jojokobi.pokemine.pokemon.status.SecStatChange;

public class ProcedurePokemon {

	private PokemonContainer pokemon;
	private Battle battle;
	private ProcedureTrainer trainer;
	

	public ProcedurePokemon(PokemonContainer pokemon, Battle battle) {
		super();
		this.pokemon = pokemon;
		this.battle = battle;
		this.trainer = new ProcedureTrainer(pokemon.getPokemon().getOwner());
	}
	
	public void setItem (ItemStack item) {
		pokemon.setItem(item);
	}
	
	public ItemStack getItem () {
		return pokemon.getItem();
	}
	
	public boolean isWild () {
		return pokemon.getPokemon().isWild();
	}
	
	public String getName () {
		return pokemon.getPokemon().getName();
	}
	
	public boolean representsPokemon (Pokemon pokemon) {
		return this.pokemon.getPokemon() == pokemon;
	}
	
	public boolean belongsTo (ProcedureTrainer trainer) {
		return trainer.representsTrainer(this.pokemon.getPokemon().getOwner());
	}
	
	public ProcedureTrainer getOwner () {
		return trainer;
	}

	public byte getLevel() {
		return pokemon.getPokemon().getLevel();
	}

	public int getHealth() {
		return pokemon.getPokemon().getHealth();
	}

	public float getHealthInPercent() {
		return pokemon.getPokemon().getHealthInPercent();
	}

	public int getMaxHealth() {
		return pokemon.getPokemon().getMaxHealth();
	}

//	public boolean hasStatus(String status) {
//		return pokemon.get().getPrimStatChange() != null && pokemon.get().getPrimStatChange().getScriptName().equals(status);
//	}

	public boolean hasType(PokemonType type) {
		return pokemon.getPokemon().getSpecies().hasType(type);
	}

	public void setHealth(int health) {
		pokemon.getPokemon().setHealth(health);
	}

	public void heal(int health) {
		setHealth(getHealth() + health);
	}

	public int getAttackLevel() {
		return pokemon.getAttackLevel();
	}

	public int getDefenseLevel() {
		return pokemon.getDefenseLevel();
	}

	public int getSpecialAttackLevel() {
		return pokemon.getSpecialAttackLevel();
	}

	public int getSpecialDefenseLevel() {
		return pokemon.getSpecialDefenseLevel();
	}

	public int getSpeedLevel() {
		return pokemon.getSpeedLevel();
	}

	public int getAccuracyLevel() {
		return pokemon.getAccuracyLevel();
	}

	public int getEvasionLevel() {
		return pokemon.getEvasionLevel();
	}
	
	public boolean addPrimStatChange (PrimStatChange stat) {
		if (pokemon.getPokemon().getPrimStatChange() == null) {
			pokemon.getPokemon().setPrimStatChange(stat);
			battle.sendBattleMessage(stat.getAddMessage(pokemon.getPokemon()));
			return true;
		}
		return false;
	}
	
	public void clearPrimStatChange () {
		if (pokemon.getPokemon().getPrimStatChange() != null) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s " + pokemon.getPokemon().getPrimStatChange().getClass().getSimpleName().toLowerCase() + " was cured!");
			pokemon.getPokemon().setPrimStatChange(null);
		}
	}
	
	public boolean hasPrimStatChange (Class<?> clazz) {
		return pokemon.getPokemon().hasPrimStatChange(clazz);
	}
	
	public boolean addSecStatChange (SecStatChange stat) {
		if (!pokemon.hasSecStatChange(stat.getClass())) {
			pokemon.addSecStatChange(stat);
			battle.sendBattleMessage(stat.getAddMessage(pokemon.getPokemon()));
			return true;
		}
		return false;
	}
	
	public void clearSecStatChanges () {
		pokemon.clearSecStatChanges();
		battle.sendBattleMessage(getName() + "'s status changes were cleared!");
	}
	
	public boolean hasSecStatChange (Class<? extends SecStatChange> clazz) {
		return pokemon.hasSecStatChange(clazz);
	}
	
	public boolean hasSecStatChangeLegacy (String stat) {
		return pokemon.hasSecStatChange(stat);
	}
	
	public boolean removeSecStatChange (Class<?> clazz) {
		return pokemon.removeSecStatChange(clazz);
	}
	
	public boolean removeSecStatChangeLegacy (String stat) {
		return pokemon.removeSecStatChange(stat);
	}

	public int addAttackLevel(int add) {
//		Pokemon pokemon = this.pokemon.getPokemon();
		int original = pokemon.getAttackLevel();
		pokemon.setAttackLevel(original + add);
		int realAdd = pokemon.getAttackLevel() - original;
		if (realAdd > 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s attack rose sharply!");
		} else if (realAdd > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s attack rose!");
		} else if (realAdd < 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s attack decreased sharply!");
		} else if (realAdd < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s attack decreased!");
		} else if (add > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s attack couldn't rise any higher!");
		} else if (add < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s attack couldn't decrease any higher!");
		}

		return realAdd;
	}

	public int addDefenseLevel(int add) {
		int original = pokemon.getDefenseLevel();
		pokemon.setDefenseLevel(original + add);
		int realAdd = pokemon.getDefenseLevel() - original;
		if (realAdd > 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s defense rose sharply!");
		} else if (realAdd > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s defense rose!");
		} else if (realAdd < 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s defense decreased sharply!");
		} else if (realAdd < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s defense decreased!");
		} else if (add > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s defense couldn't rise any higher!");
		} else if (add < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s defense couldn't decrease any higher!");
		}

		return realAdd;
	}

	public int addSpecialAttackLevel(int add) {
		int original = pokemon.getSpecialAttackLevel();
		pokemon.setSpecialAttackLevel(original + add);
		int realAdd = pokemon.getSpecialAttackLevel() - original;
		if (realAdd > 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special attack rose sharply!");
		} else if (realAdd > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special attack rose!");
		} else if (realAdd < 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special attack decreased sharply!");
		} else if (realAdd < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special attack decreased!");
		} else if (add > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special attack couldn't rise any higher!");
		} else if (add < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special attack couldn't decrease any higher!");
		}

		return realAdd;
	}

	public int addSpecialDefenseLevel(int add) {
		int original = pokemon.getSpecialDefenseLevel();
		pokemon.setSpecialDefenseLevel(original + add);
		int realAdd = pokemon.getSpecialDefenseLevel() - original;
		if (realAdd > 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special defense rose sharply!");
		} else if (realAdd > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special defense rose!");
		} else if (realAdd < 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special defense decreased sharply!");
		} else if (realAdd < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special defense decreased!");
		} else if (add > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special defense couldn't rise any higher!");
		} else if (add < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special defense couldn't decrease any higher!");
		}

		return realAdd;
	}

	public int addSpeedLevel(int add) {
		int original = pokemon.getSpeedLevel();
		pokemon.setSpeedLevel(original + add);
		int realAdd = pokemon.getSpeedLevel() - original;
		if (realAdd > 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s speed rose sharply!");
		} else if (realAdd > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s speed rose!");
		} else if (realAdd < 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s speed decreased sharply!");
		} else if (realAdd < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s speed decreased!");
		} else if (add > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s speed couldn't rise any higher!");
		} else if (add < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s speed couldn't decrease any higher!");
		}

		return realAdd;
	}

	public int addEvasionLevel(int add) {
		int original = pokemon.getEvasionLevel();
		pokemon.setEvasionLevel(original + add);
		int realAdd = pokemon.getEvasionLevel() - original;
		if (realAdd > 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s evasion rose sharply!");
		} else if (realAdd > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s evasion rose!");
		} else if (realAdd < 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s evasion decreased sharply!");
		} else if (realAdd < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s evasion decreased!");
		} else if (add > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s evasion couldn't rise any higher!");
		} else if (add < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s evasion couldn't decrease any higher!");
		}

		return realAdd;
	}

	public int addAccuracyLevel(int add) {
		int original = pokemon.getAccuracyLevel();
		pokemon.setAccuracyLevel(original + add);
		int realAdd = pokemon.getAccuracyLevel() - original;
		if (realAdd > 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s accuracy rose sharply!");
		} else if (realAdd > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s accuracy rose!");
		} else if (realAdd < 1) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s accuracy decreased sharply!");
		} else if (realAdd < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s accuracy decreased!");
		} else if (add > 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s accuracy couldn't rise any higher!");
		} else if (add < 0) {
			battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s accuracy couldn't decrease any higher!");
		}

		return realAdd;
	}
	
	public int damage (ProcedurePokemon attacker, Move move) {
		return damage(attacker, move, move.getDamageClass());
	}
	
	public int damage (ProcedurePokemon attacker, Move move, DamageClass defenseClass) {
		return damage(attacker, move.getPower(), move.getType(), move.getCriticalRate(), move.getDamageClass(), defenseClass);
	}
	
	public int damage (ProcedurePokemon attacker, int power, PokemonType type, DamageClass damageClass) {
		return damage(attacker, power, type, 1, damageClass, damageClass);
	}

	public int damage(ProcedurePokemon attacker, int power, PokemonType type, int criticalRate, DamageClass attackDamageClass, DamageClass defenseDamageClass) {
		// Damage
		PokemonContainer performer = attacker.pokemon;
//		Pokemon defender = this.pokemon.getPokemon();
		float damage = 0;
		// Stats and Effectivity
		int attack = attackDamageClass == DamageClass.PHYSICAL ? performer.getAttack()
				: performer.getSpecialAttack();
		int defense;
		if (defenseDamageClass == null) {
			defense = attackDamageClass == DamageClass.PHYSICAL ? getDefense()
					: getSpecialDefense();
		} else {
			defense = defenseDamageClass == DamageClass.PHYSICAL ? getDefense() : getSpecialDefense();
		}
		boolean stab = performer.getPokemon().getSpecies().hasType(type);
		boolean critical = Math.random() < Move.getCriticalChance(criticalRate);
		float effectivity = 1.0f;
		for (PokemonType t : pokemon.getPokemon().getSpecies().getTypes()) {
			effectivity *= t.getEffectivityMultiplier(type);
		}
		// Factor 1
		float f1 = 1;
		if (attackDamageClass == DamageClass.PHYSICAL) {
			f1 = performer.getPhysicalDamageModifier();
		} else {
			f1 = performer.getSpecialDamageModifier();
		}
		// Damage
		damage = MathUtil.calcDamage(performer.getPokemon().getLevel(), power, attack, defense)
				* (critical ? 1.5f : 1) * f1 * (stab ? 1.5f : 1) * effectivity * 1;

		// Message
		if (effectivity > 1) {
			battle.sendBattleMessage("It's super effective!");
		} else if (effectivity < 1) {
			battle.sendBattleMessage("It's not very effective effective!");
		}
		if (critical) {
			battle.sendBattleMessage("A critical hit!");
		}
		if (stab) {
			battle.sendBattleMessage(performer.getPokemon().getName() + " did more damage because of the STAB bonus!");
		}
		battle.sendBattleMessage("Effectivity: " + effectivity);

		int damageInt = Math.round(damage);
		pokemon.setLastDamage(new CausedDamage(damageInt, defenseDamageClass));
		setHealth(getHealth() - damageInt);
		return damageInt;
	}
	
	public int damageHP (ProcedurePokemon attacker, int damage, DamageClass damageClass) {
		setHealth(getHealth() - damage);
		pokemon.setLastDamage(new CausedDamage(damage, damageClass));
		return damage;
	}
	
	public int damagePercent (ProcedurePokemon attacker, float percent, DamageClass damageClass) {
		int damage = Math.round(getHealth() * percent);
		setHealth(getHealth() - damage);
		pokemon.setLastDamage(new CausedDamage(damage, damageClass));
		return damage;
	}
	
	public int damageWholePercent (ProcedurePokemon attacker, float percent, DamageClass damageClass) {
		int damage = Math.round(getMaxHealth() * percent);
		setHealth(getHealth() - damage);
		pokemon.setLastDamage(new CausedDamage(damage, damageClass));
		return damage;
	}

	public void setAttackLevel(int attack) {
		pokemon.setAttackLevel(attack);
		battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s attack was set to " + pokemon.getAttackLevel());
	}

	public void setDefenseLevel(int defense) {
		pokemon.setDefenseLevel(defense);
		battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s defense was set to " + pokemon.getDefenseLevel());
	}

	public void setSpecialAttackLevel(int specialAttack) {
		pokemon.setSpecialAttackLevel(specialAttack);
		battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special attack was set to " + pokemon.getSpecialAttackLevel());
	}

	public void setSpecialDefenseLevel(int specialDefense) {
		pokemon.setSpecialDefenseLevel(specialDefense);
		battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s special defense was set to " + pokemon.getSpecialDefense());
	}

	public void setSpeedLevel(int speed) {
		pokemon.setSpeedLevel(speed);
		battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s speed was set to " + pokemon.getSpeedLevel());
	}

	public void setAccuracyLevel(int accuracy) {
		pokemon.setAccuracyLevel(accuracy);
		battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s accuracy was set to " + pokemon.getAccuracyLevel());
	}

	public void setEvasionLevel(int evasion) {
		pokemon.setEvasionLevel(evasion);
		battle.sendBattleMessage(pokemon.getPokemon().getName() + "'s evasion was set to " + pokemon.getEvasionLevel());
	}
	
	public int getAttack () {
		return pokemon.getAttack();
	}
	
	public int getDefense () {
		return pokemon.getDefense();
	}
	
	public int getSpecialAttack () {
		return pokemon.getSpecialAttack();
	}
	
	public int getSpecialDefense () {
		return pokemon.getSpecialDefense();
	}
	
	public int getSpeed () {
		return pokemon.getSpeed();
	}
	
	public CausedDamage getLastDamage () {
		return pokemon.getLastDamage();
	}
	
	public boolean willSwitch() {
		return pokemon.getCurrentAction() instanceof SwitchAction;
	}
	
	public boolean willDamage() {
		BattleAction action = pokemon.getCurrentAction();
		return action instanceof MoveAction && ((MoveAction) action).getMove().getMove().getPower() != 0;
	}
	
	public boolean hasPrimStatChangeLegacy (String stat) {
		return pokemon.getPokemon().getPrimStatChange() != null && pokemon.getPokemon().getPrimStatChange().getScriptName().equals(stat);
	}

	public void repeatMove (MoveInstance move, boolean takePP, int round, ProcedurePokemon defender) {
		pokemon.setNextAction(new MoveAction(battle, move, pokemon, defender.pokemon, round + 1, takePP));
	}
	
	public void rest (ProcedurePokemon defender) {
		pokemon.setNextAction(new RestAction(battle, pokemon, pokemon));
	}
	
	public void switchRandom (boolean force) {
		battle.switchPokemon(pokemon, chooseSwitchPokemon(pokemon.getPokemon()), force);
	}
	
	public void messageOwner (String message) {
		pokemon.getPokemon().getOwner().message(message);
	}
	
	private Pokemon chooseSwitchPokemon (Pokemon old) {
		List<Pokemon> possible = new ArrayList<>();
		for (Pokemon poke : old.getOwner().getParty()) {
			if (poke != null && poke != old) {
				possible.add(poke);
			}
		}
		if (possible.isEmpty()) {
			possible.add(old);
		}
		return possible.get(new Random().nextInt(possible.size()));
	}
	
}
