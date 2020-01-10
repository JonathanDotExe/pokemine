package at.jojokobi.pokemine.battle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.item.CustomItem;
import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.gui.BattleGUI;
import at.jojokobi.pokemine.items.CarriableItem;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.pokemon.CausedDamage;
import at.jojokobi.pokemine.pokemon.MathUtil;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.status.SecStatChange;
import at.jojokobi.pokemine.pokemon.status.StatChange;

public class PokemonContainer {
	
	private Pokemon pokemon;
	private BattleAction nextAction = null;
	private BattleAction currentAction = null;
	private List<SecStatChange> statChanges = new ArrayList<>();
	private ItemStack item = null;
	private int attackLevel = 0;
	private int defenseLevel = 0;
	private int specialAttackLevel = 0;
	private int specialDefenseLevel = 0;
	private int speedLevel = 0;
	private int accuracyLevel = 0;
	private int evasionLevel = 0;
	private CausedDamage lastDamage;
	
	public PokemonContainer(Pokemon pokemon) {
		super();
		this.pokemon = pokemon;
		item = pokemon.getItem();
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
		lastDamage = null;
		attackLevel = 0;
		defenseLevel = 0;
		specialAttackLevel = 0;
		specialDefenseLevel = 0;
		speedLevel = 0;
		accuracyLevel = 0;
		evasionLevel = 0;
		item = pokemon.getItem();
		statChanges.clear();
		nextAction = null;
		currentAction = null;
	}
	
	public void heal () {
		pokemon.heal();
		statChanges.clear();
	}
	
	public int getAttack() {
		float statMuliplier = 1;
		for (SecStatChange statChange : statChanges) {
			statMuliplier *= statChange.getAttackModifier();
		}
		return Math.round(pokemon.getTotalAttack() * MathUtil.getStatMultiplier(getAttackLevel())
				* (pokemon.getPrimStatChange() != null ? pokemon.getPrimStatChange().getAttackModifier() : 1) * statMuliplier);
	}

	public int getDefense() {
		float statMuliplier = 1;
		for (SecStatChange statChange : statChanges) {
			statMuliplier *= statChange.getDefenseModifier();
		}
		return Math.round(pokemon.getTotalDefense() * MathUtil.getStatMultiplier(getDefenseLevel())
				* (pokemon.getPrimStatChange() != null ? pokemon.getPrimStatChange().getDefenseModifier() : 1) * statMuliplier);
	}

	public int getSpecialAttack() {
		float statMuliplier = 1;
		for (SecStatChange statChange : statChanges) {
			statMuliplier *= statChange.getSpecialAttackModifier();
		}
		return Math.round(pokemon.getTotalSpecialAttack() * MathUtil.getStatMultiplier(getSpecialAttackLevel())
				* (pokemon.getPrimStatChange() != null ? pokemon.getPrimStatChange().getSpecialAttackModifier() : 1) * statMuliplier);
	}
 
	public int getSpecialDefense() {
		float statMuliplier = 1;
		for (SecStatChange statChange : statChanges) {
			statMuliplier *= statChange.getSpecialDefenseModifier();
		}
		return Math.round(pokemon.getTotalSpecialDefense() * MathUtil.getStatMultiplier(getSpecialDefenseLevel())
				* (pokemon.getPrimStatChange() != null ? pokemon.getPrimStatChange().getSpecialDefenseModifier() : 1) * statMuliplier);
	}

	public int getSpeed() {
		float statMuliplier = 1;
		for (SecStatChange statChange : statChanges) {
			statMuliplier *= statChange.getSpeedModifier();
		}
		return Math.round(pokemon.getTotalSpeed() * MathUtil.getStatMultiplier(getSpeedLevel())
				* (pokemon.getPrimStatChange() != null ? pokemon.getPrimStatChange().getSpeedModifier() : 1) * statMuliplier);
	}
	
	public void requestNextAction (Battle battle) {
		if (pokemon.getOwner().getEntity() instanceof Player) {
			Player player = (Player) pokemon.getOwner().getEntity();
			if (battle.getBattleHandler().getPlugin().getGUIHandler().getPlayersGUI(player.getPlayer()) == null) {
				BattleGUI gui = new BattleGUI(player, pokemon.getOwner(), battle, this, battle.getBattleHandler().getPlugin());
				battle.getBattleHandler().getPlugin().getGUIHandler().addGUI(gui);
				gui.show();
			}
		}
		else {
			setNextAction(chooseRandomAction(battle));
		}
	}
	
	public float getAccuracy(int evasionLevel) {
		return MathUtil.getAccuracyMultiplier(accuracyLevel - evasionLevel);
	}
	
	public void clearStatValueChanges() {
		setAttackLevel(0);
		setDefenseLevel(0);
		setSpecialAttackLevel(0);
		setSpecialDefenseLevel(0);
		setSpeedLevel(0);
		setAccuracyLevel(0);
		setEvasionLevel(0);
	}
	
	public void addSecStatChange(SecStatChange change) {
		statChanges.add(change);
	}

	public boolean removeSecStatChange(SecStatChange change) {
		return statChanges.remove(change);
	}

	public boolean removeSecStatChange(String stat) {
		boolean deleted = false;
		for (SecStatChange s : new ArrayList<>(statChanges)) {
			if (s.getScriptName().equals(stat)) {
				removeSecStatChange(s);
				deleted = true;
			}
		}
		return deleted;
	}

	public boolean removeSecStatChange(Class<?> stat) {
		boolean deleted = false;
		for (SecStatChange s : new ArrayList<>(statChanges)) {
			if (s.getClass().equals(stat)) {
				removeSecStatChange(s);
				deleted = true;
			}
		}
		return deleted;
	}

	public void clearSecStatChanges() {
		statChanges.clear();
	}
	
	public float getPhysicalDamageModifier() {
		float statMuliplier = 1;
		CustomItem item = ItemHandler.getCustomItem(this.item);
		if (item != null && item instanceof CarriableItem) {
			statMuliplier = ((CarriableItem) item).getPhysicalDamageModifier();
		}
		for (SecStatChange statChange : statChanges) {
			statMuliplier *= statChange.getPhysicalDamageModifier();
		}
		return (pokemon.getPrimStatChange() == null ? 1 : pokemon.getPrimStatChange().getPhysicalDamageModifier()) * statMuliplier;
	}

	public float getSpecialDamageModifier() {
		float statMuliplier = 1;
		CustomItem item = ItemHandler.getCustomItem(this.item);
		if (item != null && item instanceof CarriableItem) {
			statMuliplier = ((CarriableItem) item).getSpecialDamageModifier();
		}
		for (SecStatChange statChange : statChanges) {
			statMuliplier *= statChange.getSpecialDamageModifier();
		}
		return (pokemon.getPrimStatChange() == null ? 1 : pokemon.getPrimStatChange().getSpecialDamageModifier()) * statMuliplier;
	}

	public boolean hasSecStatChange(Class<? extends SecStatChange> stat) {
		return statChanges.stream().anyMatch((s) -> s.getClass().equals(stat));
	}

	public boolean hasSecStatChange(String stat) {
		return statChanges.stream().anyMatch((s) -> s.getScriptName().equals(stat));
	}
	
	public void sendStatChangeMessages (Battle battle) {
		for (SecStatChange secStatChange : statChanges) {
			battle.sendBattleMessage(secStatChange.getMessage(pokemon));
		}
	}

	public boolean canAttack() {
		return (pokemon.getPrimStatChange() == null ? true : pokemon.getPrimStatChange().canAttack(this))
				&& new ArrayList<>(statChanges).stream().allMatch((stat) -> stat.canAttack(this));
	}

	public boolean canSwitch() {
		return (pokemon.getPrimStatChange() == null ? true : pokemon.getPrimStatChange().canSwitch(this))
				&& new ArrayList<>(statChanges).stream().allMatch((stat) -> stat.canSwitch(this));
	}
	
	public void startRound() {
		currentAction = nextAction;
		nextAction = null;
		if (pokemon.getPrimStatChange() != null && pokemon.getPrimStatChange().startRound(this)) {
			pokemon.setPrimStatChange(null);
		}
		new ArrayList<>(statChanges).forEach((stat) ->{if (stat.startRound(this)) removeSecStatChange(stat);});
	}

	public void startTurn() {
		if (pokemon.getPrimStatChange() != null && pokemon.getPrimStatChange().startTurn(this)) {
			pokemon.setPrimStatChange(null);;
		}
		new ArrayList<>(statChanges).forEach((stat) ->{if (stat.startTurn(this)) removeSecStatChange(stat);});
	}

	public void endTurn() {
		if (pokemon.getPrimStatChange() != null && pokemon.getPrimStatChange().endTurn(this)) {
			pokemon.setPrimStatChange(null);
		}
		new ArrayList<>(statChanges).forEach((stat) ->{if (stat.endTurn(this)) removeSecStatChange(stat);});
	}

	public void endRound() {
		if (pokemon.getPrimStatChange() != null && pokemon.getPrimStatChange().endRound(this)) {
			pokemon.setPrimStatChange(null);
		}
		new ArrayList<>(statChanges).forEach((stat) ->{if (stat.endRound(this)) removeSecStatChange(stat);});
	}

	public void switchPokemon(PokemonContainer oldPokemon, Pokemon newPokemon) {
		if (pokemon.getPrimStatChange() != null) {
			pokemon.getPrimStatChange().switchPokemon(this, oldPokemon, newPokemon);
		}
		new ArrayList<>(statChanges).forEach((stat) ->{if (stat.switchPokemon(this, oldPokemon, newPokemon)) removeSecStatChange(stat);});
	}

	public List<SecStatChange> getStatChanges() {
		return new ArrayList<>(statChanges);
	}

	public void removeStatChange(StatChange statChange) {
		if (statChange == pokemon.getPrimStatChange()) {
			pokemon.setPrimStatChange(null);
		} else {
			statChanges.remove(statChange);
		}
	}
	
	public BattleAction getNextAction(Battle battle) {
		return nextAction != null && battle == nextAction.getBattle() ? nextAction : null;
	}

	public void setNextAction(BattleAction nextAction) {
		this.nextAction = nextAction;
	}

	public boolean hasNextAction(Battle battle) {
		return getNextAction(battle) != null;
	}
	
	private BattleAction chooseRandomAction(Battle battle) {
		List<MoveInstance> cleanMoves = new ArrayList<>(Arrays.asList(pokemon.getMoves()));
		cleanMoves.removeAll(Collections.singleton(null));
		Random random = new Random();
		return cleanMoves.isEmpty() ? new RunAction(battle, this, getOppent(battle.getPokemonContainers()))
				: new MoveAction(battle, cleanMoves.get(random.nextInt(cleanMoves.size())), this,
						getOppent(battle.getPokemonContainers()));
	}

	public PokemonContainer getOppent(PokemonContainer[] opponents) {
		PokemonContainer opponent = null;
		for (int i = 0; i < opponents.length && opponent == null; i++) {
			if (opponents[i].getPokemon() != this.getPokemon()) {
				opponent = opponents[i];
			}
		}
		return opponent;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public int getAttackLevel() {
		return attackLevel;
	}

	public void setAttackLevel(int attackLevel) {
		this.attackLevel = attackLevel;
	}

	public int getDefenseLevel() {
		return defenseLevel;
	}

	public void setDefenseLevel(int defenseLevel) {
		this.defenseLevel = defenseLevel;
	}

	public int getSpecialAttackLevel() {
		return specialAttackLevel;
	}

	public void setSpecialAttackLevel(int specialAttackLevel) {
		this.specialAttackLevel = specialAttackLevel;
	}

	public int getSpecialDefenseLevel() {
		return specialDefenseLevel;
	}

	public void setSpecialDefenseLevel(int specialDefenseLevel) {
		this.specialDefenseLevel = specialDefenseLevel;
	}

	public int getSpeedLevel() {
		return speedLevel;
	}

	public void setSpeedLevel(int speedLevel) {
		this.speedLevel = speedLevel;
	}

	public int getAccuracyLevel() {
		return accuracyLevel;
	}

	public void setAccuracyLevel(int accuracyLevel) {
		this.accuracyLevel = accuracyLevel;
	}

	public int getEvasionLevel() {
		return evasionLevel;
	}

	public void setEvasionLevel(int evasionLevel) {
		this.evasionLevel = evasionLevel;
	}

	public CausedDamage getLastDamage() {
		return lastDamage;
	}

	public void setLastDamage(CausedDamage lastDamage) {
		this.lastDamage = lastDamage;
	}

	public BattleAction getCurrentAction() {
		return currentAction;
	}
	
}
