package at.jojokobi.pokemine.pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.moves.DamageClass;
import at.jojokobi.pokemine.moves.Move;
import at.jojokobi.pokemine.moves.MoveHandler;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.MoveLearnCondition;
import at.jojokobi.pokemine.moves.MoveUtil;
import at.jojokobi.pokemine.pokemon.evolution.EvolutionCause;
import at.jojokobi.pokemine.pokemon.status.PrimStatChange;
import at.jojokobi.pokemine.trainer.Trainer;
import at.jojokobi.pokemine.trainer.WildPokemonTrainer;

public class Pokemon implements ConfigurationSerializable {

	public static final String POKEMON_SPECIES_ELEMENT = "species";
	public static final String POKEMON_NAME_ELEMENT = "name";
	public static final String POKEMON_LEVEL_ELEMENT = "level";
	public static final String POKEMON_EP_ELEMENT = "xp";
	public static final String POKEMON_HEALTH_ELEMENT = "health";
	public static final String POKEMON_FRIENDSHIP_ELEMENT = "friendship";
	public static final String POKEMON_DVS_ELEMENT = "dv";
	public static final String POKEMON_EVS_ELEMENT = "ev";
	public static final String POKEMON_PRIM_STAT_ELEMENT = "primStatChange";
	public static final String POKEMON_SHINY_ELEMENT = "shiny";
	public static final String POKEMON_WALKED_DISTANCE_ELEMENT = "walkedDistance";
	public static final String POKEMON_SELECTED_MOVE_ELEMENT = "selectedMove";
	public static final String POKEMON_ITEM_ELEMENT = "item";
	public static final String POKEMON_PERSONALITY_BYTE_ELEMENT = "personalityByte";

	public static final String POKEMON_MOVES_ELEMENT = "moves";
	public static final String POKEMON_TO_LEARN_ELEMENT = "toLearn";

	public static final int MAX_LEVEL = 100;
	public static final int MIN_LEVEL = 1;

	public static final int MIN_EV = 0;
	public static final int MAX_EV = 252;

	public static final int MIN_TOTAL_EV = 0;
	public static final int MAX_TOTAL_EV = 510;

	public static final int MIN_DV = 0;
	public static final int MAX_DV = 31;

	public static final int MIN_STAT_LEVEL = -6;
	public static final int MAX_STAT_LEVEL = 6;

	public static final int MIN_FRIENDSHIP = 0;
	public static final int MAX_FRIENDSHIP = 255;

	public static final int MAX_MOVES = 4;

	// General Values
	private PokemonSpecies species;
	private String name = "Pokemon";
	private byte level = 1;
	private int ep = 0;
	private int friendship = 70;
	private int health = 0;
	private boolean shiny = Math.random() < (1.0 / 4096.0);
	private PrimStatChange primStatChange = null;
//	private List<SecStatChange> statChanges = new ArrayList<>();
	private Trainer owner;
	private double walkedDistance = 0.0;
	private int selectedMoveIndex = 0;
	private ItemStack item = null;
	private byte characterByte;
	// DVs
	private int healthDV = 0;
	private int attackDV = 0;
	private int defenseDV = 0;
	private int specialAttackDV = 0;
	private int specialDefenseDV = 0;
	private int speedDV = 0;
	// Temporary Stats
//	private int attackLevel = 0;
//	private int defenseLevel = 0;
//	private int specialAttackLevel = 0;
//	private int specialDefenseLevel = 0;
//	private int speedLevel = 0;
//	private int accuracyLevel = 0;
//	private int evasionLevel = 0;
//	private CausedDamage lastDamage;
	// FP
	private int healthEV = 0;
	private int attackEV = 0;
	private int defenseEV = 0;
	private int specialAttackEV = 0;
	private int specialDefenseEV = 0;
	private int speedEV = 0;
	// Moves
	private MoveInstance[] moves;
	private List<Move> toLearn = new ArrayList<>();

	public Pokemon(PokemonSpecies species) {
		this(species, MathUtil.calcPokemonLevel(species));
	}

	public Pokemon(PokemonSpecies species, byte level, Trainer trainer) {
		this(species, level, trainer, new Random());
	}

	public Pokemon(PokemonSpecies species, byte level) {
		this(species, level, new WildPokemonTrainer());
		owner.givePokemon(this);
	}

	public Pokemon(PokemonSpecies species, byte level, Trainer owner, Random random) {
		this.species = species;
		this.owner = owner;
		setName(species.getName());
		setLevel(level);
		setHealthDV(random.nextInt(MAX_DV - MIN_DV + 1) + MIN_DV);
		setAttackDV(random.nextInt(MAX_DV - MIN_DV + 1) + MIN_DV);
		setDefenseDV(random.nextInt(MAX_DV - MIN_DV + 1) + MIN_DV);
		setSpecialAttackDV(random.nextInt(MAX_DV - MIN_DV + 1) + MIN_DV);
		setSpecialDefenseDV(random.nextInt(MAX_DV - MIN_DV + 1) + MIN_DV);
		setSpeedDV(random.nextInt(MAX_DV - MIN_DV + 1) + MIN_DV);
		setHealth(getMaxHealth());
		setFriendship(species.getFriendship());
		moves = MoveUtil.generateRandomMoveset(this);
		setShiny(random.nextDouble() < (1.0 / 4096.0));
		characterByte = (byte) random.nextInt();
	}

	public boolean isWild() {
		return owner == null || owner instanceof WildPokemonTrainer;
	}

	public void heal() {
		setHealth(getMaxHealth());
		setPrimStatChange(null);
//		clearStatChanges();
		for (MoveInstance move : getMoves()) {
			if (move != null) {
				move.setPp(move.getMaxPP());
			}
		}
	}

	public boolean evolve(PokemonHandler handler, ItemStack item, boolean trade) {
		boolean evolved = false;
		List<EvolutionCause> evos = getSpecies().getEvolutions();
		for (int i = 0; i < evos.size() && !evolved; i++) {
//			String held = EvolutionCause.NO_ITEM;
//			if (this.item != null) {
//				held = this.item.getType() + "";
//			}
			if (evos.get(i).canEvolve(this, item, trade)) {
				String oldname = species.getName();
				species = handler.getItem(evos.get(i).getSpecies());
				evolved = true;
				for (MoveLearnCondition condtion : species.getLearnableMoves()) {
					if (condtion.getLevel() == MoveLearnCondition.EVOLUTION_LEVEL) {
						toLearn.add(condtion.getRealMove());
					}
				}
				if (oldname.equals(getName())) {
					setName(species.getName());
				}
			}
		}
		return evolved;
	}

	public boolean learnMove(Move move) {
		int index = -1;
		for (int i = 0; i < getMoves().length && index == -1; i++) {
			if (getMoves()[i] == null) {
				index = i;
			}
		}
		if (index != -1) {
			getMoves()[index] = new MoveInstance(move);
		}
		return index != -1;
	}

	public byte getCharacterByte() {
		return characterByte;
	}
	
	private int generateEqualityByte (byte other) {
		int byte1 = Byte.toUnsignedInt(characterByte);
//		System.out.println("1 " + byte1);
		int byte2 = Byte.toUnsignedInt(other);
//		System.out.println("2 " + byte2);
		
//		System.out.println((byte1 ^ byte2));
		
		int equality = (~(byte1 ^ byte2)) & 0b11111111;
//		System.out.println(equality);
		return equality;
	}
	
	public boolean likes (Pokemon other) {
		int equality = generateEqualityByte(other.getCharacterByte());
		int minLikeEquality = Math.round(species.getMinLikeEquality() * 255);
		
		return equality >= minLikeEquality;
	}
	
	public boolean dislikes (Pokemon other) {
		int equality = generateEqualityByte(other.getCharacterByte());
		int maxHateEquality = Math.round(species.getMaxDislikeEquality() * 255);
		
		return equality <= maxHateEquality;
	}

	public float getDVPercent() {
		return (float) (getHealthDV() + getAttackDV() + getDefenseDV() + getSpecialAttackDV() + getSpecialDefenseDV()
				+ getSpeedDV()) / (MAX_DV * 6.0f);
	}

	public int getMaxHealth() {
		return Math.round(((2f * getSpecies().getHealth() + getHealthDV() + getHealthEV() / 4f) * getLevel()) / 100f
				+ getLevel() + 10);
	}

	public int getTotalAttack() {
		return clacStatValue(getSpecies().getAttack(), getAttackDV(), getAttackEV());
	}

	public int getTotalDefense() {
		return clacStatValue(getSpecies().getDefense(), getDefenseDV(), getDefenseEV());
	}

	public int getTotalSpecialAttack() {
		return clacStatValue(getSpecies().getSpecialAttack(), getSpecialAttackDV(), getSpecialAttackEV());
	}

	public int getTotalSpecialDefense() {
		return clacStatValue(getSpecies().getSpecialDefense(), getSpecialDefenseDV(), getSpecialDefenseEV());
	}

	public int getTotalSpeed() {
		return clacStatValue(getSpecies().getSpeed(), getSpeedDV(), getSpeedEV());
	}

//	public int getAttack() {
//		float statMuliplier = 1;
//		for (SecStatChange statChange : statChanges) {
//			statMuliplier *= statChange.getAttackModifier();
//		}
//		return Math.round(getTotalAttack() * MathUtil.getStatMultiplier(getAttackLevel())
//				* (getPrimStatChange() != null ? getPrimStatChange().getAttackModifier() : 1) * statMuliplier);
//	}
//
//	public int getDefense() {
//		float statMuliplier = 1;
//		for (SecStatChange statChange : statChanges) {
//			statMuliplier *= statChange.getDefenseModifier();
//		}
//		return Math.round(getTotalDefense() * MathUtil.getStatMultiplier(getDefenseLevel())
//				* (getPrimStatChange() != null ? getPrimStatChange().getDefenseModifier() : 1) * statMuliplier);
//	}
//
//	public int getSpecialAttack() {
//		float statMuliplier = 1;
//		for (SecStatChange statChange : statChanges) {
//			statMuliplier *= statChange.getSpecialAttackModifier();
//		}
//		return Math.round(getTotalSpecialAttack() * MathUtil.getStatMultiplier(getSpecialAttackLevel())
//				* (getPrimStatChange() != null ? getPrimStatChange().getSpecialAttackModifier() : 1) * statMuliplier);
//	}
// 
//	public int getSpecialDefense() {
//		float statMuliplier = 1;
//		for (SecStatChange statChange : statChanges) {
//			statMuliplier *= statChange.getSpecialDefenseModifier();
//		}
//		return Math.round(getTotalSpecialDefense() * MathUtil.getStatMultiplier(getSpecialDefenseLevel())
//				* (getPrimStatChange() != null ? getPrimStatChange().getSpecialDefenseModifier() : 1) * statMuliplier);
//	}
//
//	public int getSpeed() {
//		float statMuliplier = 1;
//		for (SecStatChange statChange : statChanges) {
//			statMuliplier *= statChange.getSpeedModifier();
//		}
//		return Math.round(getTotalSpeed() * MathUtil.getStatMultiplier(getSpeedLevel())
//				* (getPrimStatChange() != null ? getPrimStatChange().getSpeedModifier() : 1) * statMuliplier);
//	}

//	public float getAccuracy(int evasionLevel) {
//		return MathUtil.getAccuracyMultiplier(accuracyLevel - evasionLevel);
//	}

//	public int getAttackLevel() {
//		return attackLevel;
//	}
//
//	public void setAttackLevel(int attackLevel) {
//		this.attackLevel = Math.max(Math.min(attackLevel, MAX_STAT_LEVEL), MIN_STAT_LEVEL);
//	}
//
//	public int getDefenseLevel() {
//		return defenseLevel;
//	}
//
//	public void setDefenseLevel(int defenseLevel) {
//		this.defenseLevel = Math.max(Math.min(defenseLevel, MAX_STAT_LEVEL), MIN_STAT_LEVEL);
//	}
//
//	public int getSpecialAttackLevel() {
//		return specialAttackLevel;
//	}
//
//	public void setSpecialAttackLevel(int specialAttackLevel) {
//		this.specialAttackLevel = Math.max(Math.min(specialAttackLevel, MAX_STAT_LEVEL), MIN_STAT_LEVEL);
//	}
//
//	public int getSpecialDefenseLevel() {
//		return specialDefenseLevel;
//	}
//
//	public void setSpecialDefenseLevel(int specialDefenseLevel) {
//		this.specialDefenseLevel = Math.max(Math.min(specialDefenseLevel, MAX_STAT_LEVEL), MIN_STAT_LEVEL);
//	}
//
//	public int getSpeedLevel() {
//		return speedLevel;
//	}
//
//	public void setSpeedLevel(int speedLevel) {
//		this.speedLevel = Math.max(Math.min(speedLevel, MAX_STAT_LEVEL), MIN_STAT_LEVEL);
//	}
//
//	public int getAccuracyLevel() {
//		return accuracyLevel;
//	}
//
//	public void setAccuracyLevel(int accuracyLevel) {
//		this.accuracyLevel = accuracyLevel;
//	}
//
//	public int getEvasionLevel() {
//		return evasionLevel;
//	}
//
//	public void setEvasionLevel(int evasionLevel) {
//		this.evasionLevel = evasionLevel;
//	}

//	public void clearStatValueChanges() {
//		setAttackLevel(0);
//		setDefenseLevel(0);
//		setSpecialAttackLevel(0);
//		setSpecialDefenseLevel(0);
//		setSpeedLevel(0);
//		setAccuracyLevel(0);
//		setEvasionLevel(0);
//	}

//	public void clearAllTempStats() {
//		clearStatValueChanges();
//		clearStatChanges();
//		setLastDamage(null);
//	}

	private int clacStatValue(int value, int dv, int fp) {
		return Math.round((((2f * value + dv + fp / 4f) * getLevel()) / 100f + 5));
	}

	public int getHealth() {
		return health;
	}

	public float getHealthInPercent() {
		return health / (float) getMaxHealth();
	}

	public void setHealth(int health) {
		if (health > getMaxHealth()) {
			this.health = getMaxHealth();
		} else if (health < 0) {
			this.health = 0;
		} else {
			this.health = health;
		}
	}

	public int getEp() {
		return ep;
	}

	public void setEp(int ep) {
		this.ep = ep;
	}

	/**
	 * 
	 * @param add
	 * @return Wheter the pokemon has leveled up or not
	 */
	public boolean gainEp(int add) {
		int oldlevel = getLevel();
		int ep = getEp() + add;
		while (ep >= getSpecies().getLevelEP().getEpToLevelUp(getLevel() + 1)) {
			ep -= getSpecies().getLevelEP().getEpToLevelUp(getLevel() + 1);
			setLevel((byte) (getLevel() + 1));
		}
		setEp(ep);

		boolean leveledUp = oldlevel < getLevel();
		if (leveledUp) {
			for (MoveLearnCondition condition : getSpecies().getLearnableMoves()) {
				if (oldlevel < condition.getLevel() && getLevel() >= condition.getLevel()) {
					toLearn.add(condition.getRealMove());
				}
			}
		}

		return leveledUp;
	}

	public byte getLevel() {
		return level;
	}

	public void setLevel(byte level) {
		float health = getHealthInPercent();
		if (level > MAX_LEVEL) {
			this.level = MAX_LEVEL;
		} else if (level < MIN_LEVEL) {
			this.level = MIN_LEVEL;
		} else {
			this.level = level;
		}
		setHealth((int) (health * getMaxHealth()));
	}

	public int getFriendship() {
		return friendship;
	}

	public void setFriendship(int friendship) {
		if (friendship > MAX_FRIENDSHIP) {
			this.friendship = MAX_FRIENDSHIP;
		} else if (friendship < MIN_FRIENDSHIP) {
			this.friendship = MIN_FRIENDSHIP;
		} else {
			this.friendship = friendship;
		}
	}

	public int getHealthDV() {
		return healthDV;
	}

	public void setHealthDV(int healthDV) {
		if (healthDV > MAX_DV) {
			this.healthDV = MAX_DV;
		} else if (healthDV < MIN_DV) {
			this.healthDV = MIN_DV;
		} else {
			this.healthDV = healthDV;
		}
	}

	public int getAttackDV() {
		return attackDV;
	}

	public void setAttackDV(int attackDV) {
		if (attackDV > MAX_DV) {
			this.attackDV = MAX_DV;
		} else if (attackDV < MIN_DV) {
			this.attackDV = MIN_DV;
		} else {
			this.attackDV = attackDV;
		}
	}

	public int getDefenseDV() {
		return defenseDV;
	}

	public void setDefenseDV(int defenseDV) {
		if (defenseDV > MAX_DV) {
			this.defenseDV = MAX_DV;
		} else if (defenseDV < MIN_DV) {
			this.defenseDV = MIN_DV;
		} else {
			this.defenseDV = defenseDV;
		}
	}

	public int getSpecialAttackDV() {
		return specialAttackDV;
	}

	public void setSpecialAttackDV(int specialAttackDV) {
		if (specialAttackDV > MAX_DV) {
			this.specialAttackDV = MAX_DV;
		} else if (specialAttackDV < MIN_DV) {
			this.specialAttackDV = MIN_DV;
		} else {
			this.specialAttackDV = specialAttackDV;
		}
	}

	public int getSpecialDefenseDV() {
		return specialDefenseDV;
	}

	public void setSpecialDefenseDV(int specialDefenseDV) {
		if (specialDefenseDV > MAX_DV) {
			this.specialDefenseDV = MAX_DV;
		} else if (specialDefenseDV < MIN_DV) {
			this.specialDefenseDV = MIN_DV;
		} else {
			this.specialDefenseDV = specialDefenseDV;
		}
	}

	public int getSpeedDV() {
		return speedDV;
	}

	public void setSpeedDV(int speedDV) {
		if (speedDV > MAX_DV) {
			this.speedDV = MAX_DV;
		} else if (speedDV < MIN_DV) {
			this.speedDV = MIN_DV;
		} else {
			this.speedDV = speedDV;
		}
	}

	public int getHealthEV() {
		return healthEV;
	}

	public void setHealthEV(int healthEV) {
		if (healthEV > MAX_EV) {
			healthEV = MAX_EV;
		} else if (healthEV < MIN_EV) {
			healthEV = MIN_EV;
		}
		this.healthEV = Math.min(healthEV, MAX_TOTAL_EV - getEVSum());
	}

	public int getAttackEV() {
		return attackEV;
	}

	public void setAttackEV(int attackEV) {
		if (attackEV > MAX_EV) {
			attackEV = MAX_EV;
		} else if (attackEV < MIN_EV) {
			attackEV = MIN_EV;
		}
		this.attackEV = Math.min(attackEV, MAX_TOTAL_EV - getEVSum());
	}

	public int getDefenseEV() {
		return defenseEV;
	}

	public void setDefenseEV(int defenseEV) {
		if (defenseEV > MAX_EV) {
			defenseEV = MAX_EV;
		} else if (defenseEV < MIN_EV) {
			defenseEV = MIN_EV;
		}
		this.defenseEV = Math.min(defenseEV, MAX_TOTAL_EV - getEVSum());
	}

	public int getSpecialAttackEV() {
		return specialAttackEV;
	}

	public void setSpecialAttackEV(int specialAttackEV) {
		if (specialAttackEV > MAX_EV) {
			specialAttackEV = MAX_EV;
		} else if (specialAttackEV < MIN_EV) {
			specialAttackEV = MIN_EV;
		}
		this.specialAttackEV = Math.min(specialAttackEV, MAX_TOTAL_EV - getEVSum());
	}

	public int getSpecialDefenseEV() {
		return specialDefenseEV;
	}

	public void setSpecialDefenseEV(int specialDefenseEV) {
		if (specialDefenseEV > MAX_EV) {
			specialDefenseEV = MAX_EV;
		} else if (specialDefenseEV < MIN_EV) {
			specialDefenseEV = MIN_EV;
		}
		this.specialDefenseEV = Math.min(specialDefenseEV, MAX_TOTAL_EV - getEVSum());
	}

	public int getSpeedEV() {
		return speedEV;
	}

	public void setSpeedEV(int speedEV) {
		if (speedEV > MAX_EV) {
			speedEV = MAX_EV;
		} else if (speedEV < MIN_EV) {
			speedEV = MIN_EV;
		}
		this.speedEV = Math.min(speedEV, MAX_TOTAL_EV - getEVSum());
	}

	public int getStatValueSum() {
		return getMaxHealth() + getTotalAttack() + getTotalDefense() + getTotalSpecialAttack() + getTotalSpecialDefense() + getTotalSpeed();
	}

	public int getEVSum() {
		return healthEV + attackEV + defenseEV + specialAttackEV + specialDefenseEV + speedEV;
	}

	public int getCP() {
		return (getStatValueSum() * getLevel() * 6) / 100;
	}

	public double getMobDamage() {
		int power = 0;
		int attack = 0;
		Move move = getSelectedTerrainMove();
		if (move != null) {
			power = move.getPower();
			attack = move.getDamageClass() == DamageClass.PHYSICAL ? getSpecies().getAttack()
					: getSpecies().getSpecialAttack();
		}
		return power * attack / 1500;
	}

	public double getMobDefense() {
		return 5 * ((getSpecies().getDefense() + getSpecies().getSpecialDefense()) / 2) / 255;
	}

	public void takeMobDamage(double damage) {
		damage = Math.max(1, damage - getMobDefense());
		damage *= 100.0 / 20.0;
		double percent = damage / species.getHealth();
		setHealth((int) Math.round(getHealth() - getMaxHealth() * percent));
	}

	public int getMobAttackDelay() {
//		return 255/getSpecies().getSpeed();
		return (255 - getSpecies().getSpeed()) / 35;
	}

	public PokemonSpecies getSpecies() {
		return species;
	}

	public MoveInstance[] getMoves() {
		return moves;
	}

	public void setMoves(MoveInstance[] moves) {
		this.moves = moves;
	}

	public Trainer getOwner() {
		return owner;
	}

	public void setOwner(Trainer owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isShiny() {
		return shiny;
	}

	public void setShiny(boolean shiny) {
		this.shiny = shiny;
	}

	public double getWalkedDistance() {
		return walkedDistance;
	}

	public void setWalkedDistance(double walkedDistance) {
		this.walkedDistance = walkedDistance;
	}

	public void addWalkedDistance(double walkedDistance) {
		double oldDistance = getWalkedDistance();
		setWalkedDistance(oldDistance + walkedDistance);
		if ((int) oldDistance / 256 < (int) getWalkedDistance() / 256 && Math.random() < 45) {
			if (getFriendship() <= 200) {
				addFriedship(2);
			} else {
				addFriedship(1);
			}
		}
	}

	private void addFriedship(int friendship) {
		setFriendship(getFriendship() + friendship);
	}

//	@Deprecated
//	public boolean isBattling() {
//		return battling;
//	}
//
//	@Deprecated
//	public void setBattling(boolean battling) {
//		this.battling = battling;
//	}

	public PrimStatChange getPrimStatChange() {
		return primStatChange;
	}

	public void setPrimStatChange(PrimStatChange primStatChange) {
		this.primStatChange = primStatChange;
	}

//	public void addSecStatChange(SecStatChange change) {
//		statChanges.add(change);
//	}
//
//	public boolean removeSecStatChange(SecStatChange change) {
//		return statChanges.remove(change);
//	}
//
//	public boolean removeSecStatChange(String stat) {
//		boolean deleted = false;
//		for (SecStatChange s : new ArrayList<>(statChanges)) {
//			if (s.getScriptName().equals(stat)) {
//				removeSecStatChange(s);
//				deleted = true;
//			}
//		}
//		return deleted;
//	}
//
//	public boolean removeSecStatChange(Class<? extends SecStatChange> stat) {
//		boolean deleted = false;
//		for (SecStatChange s : new ArrayList<>(statChanges)) {
//			if (s.getClass().equals(stat)) {
//				removeSecStatChange(s);
//				deleted = true;
//			}
//		}
//		return deleted;
//	}
//
//	public void clearStatChanges() {
//		statChanges.clear();
//	}
	
	public boolean hasPrimStatChange(Class<?> stat) {
		return primStatChange != null && primStatChange.getClass().equals(stat);
	}
	
	
	
//
//	public boolean hasSecStatChange(Class<? extends SecStatChange> stat) {
//		return statChanges.stream().anyMatch((s) -> s.getClass().equals(stat));
//	}
//
//	public boolean hasSecStatChange(String stat) {
//		return statChanges.stream().anyMatch((s) -> s.getScriptName().equals(stat));
//	}
//
//	public boolean canAttack() {
//		return (getPrimStatChange() == null ? true : getPrimStatChange().canAttack(this))
//				&& new ArrayList<>(statChanges).stream().allMatch((stat) -> stat.canAttack(this));
//	}
//
//	public boolean canSwitch() {
//		return (getPrimStatChange() == null ? true : getPrimStatChange().canSwitch(this))
//				&& new ArrayList<>(statChanges).stream().allMatch((stat) -> stat.canSwitch(this));
//	}

//	public float getPhysicalDamageModifier() {
//		float statMuliplier = 1;
//		CustomItem item = ItemHandler.getCustomItem(this.item);
//		if (item != null && item instanceof CarriableItem) {
//			statMuliplier = ((CarriableItem) item).getPhysicalDamageModifier();
//		}
//		for (SecStatChange statChange : statChanges) {
//			statMuliplier *= statChange.getPhysicalDamageModifier();
//		}
//		return (getPrimStatChange() == null ? 1 : getPrimStatChange().getPhysicalDamageModifier()) * statMuliplier;
//	}
//
//	public float getSpecialDamageModifier() {
//		float statMuliplier = 1;
//		CustomItem item = ItemHandler.getCustomItem(this.item);
//		if (item != null && item instanceof CarriableItem) {
//			statMuliplier = ((CarriableItem) item).getSpecialDamageModifier();
//		}
//		for (SecStatChange statChange : statChanges) {
//			statMuliplier *= statChange.getSpecialDamageModifier();
//		}
//		return (getPrimStatChange() == null ? 1 : getPrimStatChange().getSpecialDamageModifier()) * statMuliplier;
//	}

//	public void startRound() {
//		if (getPrimStatChange() != null && getPrimStatChange().startRound(this)) {
//			setPrimStatChange(null);
//		}
//		new ArrayList<>(statChanges).forEach((stat) ->{if (stat.startRound(this)) removeSecStatChange(stat);});
//	}
//
//	public void startTurn() {
//		if (getPrimStatChange() != null && getPrimStatChange().startTurn(this)) {
//			setPrimStatChange(null);;
//		}
//		new ArrayList<>(statChanges).forEach((stat) ->{if (stat.startTurn(this)) removeSecStatChange(stat);});
//	}
//
//	public void endTurn() {
//		if (getPrimStatChange() != null && getPrimStatChange().endTurn(this)) {
//			setPrimStatChange(null);
//		}
//		new ArrayList<>(statChanges).forEach((stat) ->{if (stat.endTurn(this)) removeSecStatChange(stat);});
//	}
//
//	public void endRound() {
//		if (getPrimStatChange() != null && getPrimStatChange().endRound(this)) {
//			setPrimStatChange(null);
//		}
//		new ArrayList<>(statChanges).forEach((stat) ->{if (stat.endRound(this)) removeSecStatChange(stat);});
//	}
//
//	public void switchPokemon(Pokemon oldPokemon, Pokemon newPokemon) {
//		if (getPrimStatChange() != null) {
//			getPrimStatChange().switchPokemon(this, oldPokemon, newPokemon);
//		}
//		new ArrayList<>(statChanges).forEach((stat) ->{if (stat.switchPokemon(this, oldPokemon, newPokemon)) removeSecStatChange(stat);});
//	}
//
//	public List<SecStatChange> getStatChanges() {
//		return new ArrayList<>(statChanges);
//	}
//
//	public void removeStatChange(StatChange statChange) {
//		if (statChange == getPrimStatChange()) {
//			setPrimStatChange(null);
//		} else {
//			statChanges.remove(statChange);
//		}
//	}

	public List<Move> getToLearn() {
		return new ArrayList<>(toLearn);
	}

	public Move fetchMoveToLearn () {
		Move move = null;
		if (toLearn.size() > 0) {
			move = toLearn.get(0);
			toLearn.remove(0);
		}
		return move;
	}
	
	@Override
	public String toString() {
		return (isWild() ? "" : getOwner().getName() + "'s ") + getName() + " [Level " + getLevel() + "]";
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}
//
//	public CausedDamage getLastDamage() {
//		return lastDamage;
//	}
//
//	public void setLastDamage(CausedDamage lastDamage) {
//		this.lastDamage = lastDamage;
//	}

	public int getSelectedMoveIndex() {
		return selectedMoveIndex;
	}

	public void setSelectedMoveIndex(int selectedMoveIndex) {
		this.selectedMoveIndex = Math.max(0, Math.min(selectedMoveIndex, moves.length - 1));
	}

	public Move getSelectedTerrainMove() {
		return getMoves()[getSelectedMoveIndex()] != null ? getMoves()[getSelectedMoveIndex()].getMove() : null;
	}

	public void selectStrongestMove() {
		int index = 0;
		int power = 0;
		for (int i = 0; i < moves.length; i++) {
			if (moves[i] != null && moves[i].getMove().getPower() > power) {
				index = i;
				power = moves[i].getMove().getPower();
			}
		}
		setSelectedMoveIndex(index);
	}

	public ItemStack toItemStack() {
		ItemStack item = getSpecies().toItemStack();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(getName());
		List<String> lore = new ArrayList<String>();
		lore.add("Level " + getLevel());
		lore.add("Experience " + getEp() + "/" + getSpecies().getLevelEP().getEpToLevelUp(getLevel() + 1));
		lore.add("Status " + (getPrimStatChange() != null ? getPrimStatChange().getName() : "OK"));
		lore.add("HP " + getHealth() + "/" + getMaxHealth());
		lore.add("Attack " + getTotalAttack());
		lore.add("Defense " + getTotalDefense());
		lore.add("Special Attack " + getTotalSpecialAttack());
		lore.add("Special Defense " + getTotalSpecialDefense());
		lore.add("Speed " + getTotalSpeed());
		lore.add("Friendship " + getFriendship());
		lore.add("Distance " + getWalkedDistance());
		lore.add("Personality " + Byte.toUnsignedInt(characterByte));
		lore.add("");
		lore.add("Moves:");
		for (MoveInstance move : getMoves()) {
			if (move != null) {
				lore.add(move.toString());
			}
		}
		lore.add("");
		lore.add("DVs: " + Math.round(getDVPercent() * 10000) / 100);
		lore.add("CP: " + getCP());
		lore.add("Trainer: " + getOwner().getName());
		meta.setLore(lore);
		item.setItemMeta(meta);

		if (isShiny()) {
			item.addUnsafeEnchantment(Enchantment.LUCK, 1);
		}
		return item;
	}

	public void learnMoves(List<Move> moves) {
//		MoveLearnGUI rootGui = null;
//		MoveLearnGUI lastGui = null;
//		for (Move move : moves) {
//			if (!learnMove(move) && getOwner().getEntity() instanceof Player) {
//				MoveLearnGUI gui = new MoveLearnGUI((Player) getOwner().getEntity(), this, move);
//				if (lastGui != null) {
//					lastGui.setNext(gui);
//				} else {
//					rootGui = gui;
//				}
//				lastGui = gui;
//			}
//		}
//		return rootGui;
		toLearn.addAll(moves);
	}

	/**
	 * 
	 * @param moves
	 * @param guiHandler
	 * @return true if a GUI was shown, false otherwise
	 */
//	public boolean learnMoves(List<Move> moves, InventoryGUIHandler guiHandler) {
//		MoveLearnGUI gui = learnMoves(moves);
//		if (gui != null) {
//			guiHandler.addGUI(gui);
//			gui.show();
//		}
//		return gui != null;
//	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put(POKEMON_SPECIES_ELEMENT, new NamespacedEntry(species.getNamespace(), species.getIdentifier()));
		map.put(POKEMON_NAME_ELEMENT, name);
		map.put(POKEMON_LEVEL_ELEMENT, level);
		map.put(POKEMON_EP_ELEMENT, ep);
		map.put(POKEMON_HEALTH_ELEMENT, health);
		map.put(POKEMON_FRIENDSHIP_ELEMENT, friendship);
		map.put(POKEMON_DVS_ELEMENT,
				new PokemonValueSet(healthDV, attackDV, defenseDV, specialAttackDV, specialDefenseDV, speedDV));
		map.put(POKEMON_EVS_ELEMENT,
				new PokemonValueSet(healthEV, attackEV, defenseEV, specialAttackEV, specialDefenseEV, speedEV));
		if (primStatChange != null) {
			map.put(POKEMON_PRIM_STAT_ELEMENT, primStatChange);
		}
		map.put(POKEMON_SHINY_ELEMENT, shiny);
		map.put(POKEMON_WALKED_DISTANCE_ELEMENT, walkedDistance);
		if (item != null) {
			map.put(POKEMON_ITEM_ELEMENT, item);
		}
		map.put(POKEMON_SELECTED_MOVE_ELEMENT, selectedMoveIndex);
		List<MoveInstance> moves = new ArrayList<>(Arrays.asList(this.moves));
		moves.removeAll(Collections.singleton(null));
		map.put(POKEMON_MOVES_ELEMENT, moves);
		List<NamespacedEntry> toLearn = new ArrayList<>();
		for (Move move : this.toLearn) {
			toLearn.add(move.toNamespacedEntry());
		}
		map.put(POKEMON_PERSONALITY_BYTE_ELEMENT, characterByte);

		return map;
	}

	public static Pokemon deserialize(Map<String, Object> map) {
		// Species
		NamespacedEntry entry = null;
		if (map.get(POKEMON_SPECIES_ELEMENT) instanceof NamespacedEntry) {
			entry = (NamespacedEntry) map.get(POKEMON_SPECIES_ELEMENT);
		} else {
			entry = new NamespacedEntry(PokeminePlugin.POKEMINE_NAMESPACE, "missingno");
		}
		Pokemon pokemon = new Pokemon(PokemonHandler.getInstance().getItem(entry));
		// Data
		pokemon.setName(map.get(POKEMON_NAME_ELEMENT) + "");
		// Level
		try {
			pokemon.setLevel((byte) Integer.parseInt(map.get(POKEMON_LEVEL_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		// EP
		try {
			pokemon.setEp(Integer.parseInt(map.get(POKEMON_EP_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		// Health
		try {
			pokemon.setHealth(Integer.parseInt(map.get(POKEMON_HEALTH_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		// Friendship
		try {
			pokemon.setFriendship(Integer.parseInt(map.get(POKEMON_FRIENDSHIP_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		// DVs
		if (map.get(POKEMON_DVS_ELEMENT) instanceof PokemonValueSet) {
			PokemonValueSet dvs = (PokemonValueSet) map.get(POKEMON_DVS_ELEMENT);
			pokemon.setHealthDV(dvs.getHealth());
			pokemon.setAttackDV(dvs.getAttack());
			pokemon.setDefenseDV(dvs.getDefense());
			pokemon.setSpecialAttackDV(dvs.getSpecialAttack());
			pokemon.setSpecialDefenseDV(dvs.getSpecialDefense());
			pokemon.setSpeedDV(dvs.getSpeed());
		}
		// EVs
		if (map.get(POKEMON_EVS_ELEMENT) instanceof PokemonValueSet) {
			PokemonValueSet evs = (PokemonValueSet) map.get(POKEMON_EVS_ELEMENT);
			pokemon.setHealthEV(evs.getHealth());
			pokemon.setAttackEV(evs.getAttack());
			pokemon.setDefenseEV(evs.getDefense());
			pokemon.setSpecialAttackEV(evs.getSpecialAttack());
			pokemon.setSpecialDefenseEV(evs.getSpecialDefense());
			pokemon.setSpeedEV(evs.getSpeed());
		}
		//Prim Stat Change
		if (map.get(POKEMON_PRIM_STAT_ELEMENT) instanceof PrimStatChange) {
			pokemon.setPrimStatChange((PrimStatChange) map.get(POKEMON_PRIM_STAT_ELEMENT));
		}
		//Shiny
		pokemon.setShiny(Boolean.parseBoolean(map.get(POKEMON_SHINY_ELEMENT) + ""));
		//Walked Distance
		try {
			pokemon.setWalkedDistance(Double.parseDouble(map.get(POKEMON_WALKED_DISTANCE_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		//Item Stack
		if (map.get(POKEMON_ITEM_ELEMENT) instanceof ItemStack) {
			pokemon.setItem((ItemStack) map.get(POKEMON_ITEM_ELEMENT));
		}
		//Selected Move Index
		try {
			pokemon.setSelectedMoveIndex(Integer.parseInt(map.get(POKEMON_SELECTED_MOVE_ELEMENT) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		//Moves
		if (map.get(POKEMON_MOVES_ELEMENT) instanceof List<?>) {
			List<?> moves = (List<?>) map.get(POKEMON_MOVES_ELEMENT);
			MoveInstance[] moveArr = new MoveInstance[MAX_MOVES];
			int index = 0;
			for (Iterator<?> iterator = moves.iterator(); iterator.hasNext()  && index < moveArr.length;) {
				Object object = iterator.next();
				if (object instanceof MoveInstance) {
					MoveInstance move = (MoveInstance) object;
					moveArr[index] = move;
					index++;
				}
			}
			pokemon.setMoves(moveArr);
		}
		//To Learn
		TypedMap tMap = new TypedMap(map);
		if (tMap.get(POKEMON_TO_LEARN_ELEMENT) instanceof List<?>) {
			List<NamespacedEntry> toLearn = tMap.getList(POKEMON_TO_LEARN_ELEMENT, NamespacedEntry.class);
			for (NamespacedEntry e : toLearn) {
				pokemon.toLearn.add (MoveHandler.getInstance().getItem(e));
			}
		}
		//Personality
		if (map.containsKey(POKEMON_PERSONALITY_BYTE_ELEMENT)) {
			pokemon.characterByte = tMap.getByte(POKEMON_PERSONALITY_BYTE_ELEMENT);
		}
	
		return pokemon;
	}

}
