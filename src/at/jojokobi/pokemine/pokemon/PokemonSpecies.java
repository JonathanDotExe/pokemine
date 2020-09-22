package at.jojokobi.pokemine.pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.mcutil.Identifiable;
import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.mcutil.loot.LootInventory;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.moves.Move;
import at.jojokobi.pokemine.moves.MoveLearnCondition;
import at.jojokobi.pokemine.pokemon.entity.ability.PokemonEntityAbilityType;
import at.jojokobi.pokemine.pokemon.evolution.EvolutionCause;
import at.jojokobi.pokemine.spawning.EnvironmentSituation;
import at.jojokobi.pokemine.spawning.ISpawnChance;

public class PokemonSpecies implements Identifiable, ConfigurationSerializable{
	
	
	public static final Material POKEMON_MATERIAL = Material.DIAMOND_SHOVEL;
	public static final Material POKEMON_FORM_MATERIAL = Material.DIAMOND_PICKAXE;
	
	public static final String POKEDEX_ELEMENT = "pokedex";
	public static final String POKEMON_SPECIES_NAME_ELEMENT = "name";
	public static final String POKEMON_IDENTIFIER_ELEMENT = "identifier";
	public static final String POKEMON_INDEX_ELEMENT = "index";
	public static final String POKEMON_TYPES_ELEMENT = "types";
//	public static final String POKEMON_TYPE_ELEMENT = "type";
	public static final String POKEMON_MALE_CHANCE_ELEMENT = "maleChance";
	public static final String POKEMON_EGG_GROUPS_ELEMENT = "eggGroups";
	public static final String POKEMON_EGG_CYCLES_ELEMENT = "eggCycles";
	public static final String POKEMON_CATEGORY_ELEMENT = "category";
	public static final String POKEMON_SIZE_ELEMENT = "size";
	public static final String POKEMON_WEIGHT_ELEMENT = "weight";
	public static final String POKEMON_COLOR_ELEMENT = "color";
	public static final String POKEMON_DESCRIPTION_ELEMENT = "description";
	public static final String POKEMON_LEGENDARY_ELEMENT = "legendary";
	public static final String POKEMON_ALTERNATIVE_FORM_ELEMENT = "formOf";
	
	public static final String BASE_VALUES_ELEMENT = "baseValues";
	public static final String POKEMON_HEALTH_ELEMENT = "health";
	public static final String POKEMON_ATTACK_ELEMENT = "attack";
	public static final String POKEMON_DEFENSE_ELEMENT = "defense";
	public static final String POKEMON_SPECIAL_ATTACK_ELEMENT = "specialAttack";
	public static final String POKEMON_SPECIAL_DEFENSE_ELEMENT = "specialDefense";
	public static final String POKEMON_SPEED_ELEMENT = "speed";
	public static final String POKEMON_FRIENDSHIP_ELEMENT = "initialFriendship";
	public static final String POKEMON_CATCH_RATE_ELEMENT = "catchRate";
	public static final String POKEMON_LEVEL_EP_ELEMENT = "levelSpeed";
	public static final String POKEMON_WILD_LEVEL_ELEMENT = "wildLevel";
	public static final String POKEMON_MIN_LIKE_EQUALITY_ELEMENT = "minLikeEquality";
	public static final String POKEMON_MAX_DISLIKE_EQUALITY_ELEMENT = "maxDislikeEquality";
	
	public static final String MOVES_ELEMENT = "moves";
//	public static final String MOVE_ELEMENT = "move";
//	public static final String MOVE_LEVEL_ELEMENT = "level";
	
	public static final String EVOLUTIONS_ELEMENT = "evolutions";
//	public static final String EVOLUTION_SPECIES_ELEMENT = "species";
//	public static final String EVOLUTION_ITEM_ELEMENT = "item";
//	public static final String EVOLUTION_TRADE_ELEMENT = "trade";
//	public static final String EVOLUTION_FRIENDSHIP_ELEMENT = "friendship";
//	public static final String EVOLUTION_HELD_ITEM_ELEMENT = "held_item";
	
//	public static final String POKEMON_FIELDMOVES_ELEMENT = "fieldmoves";
	public static final String POKEMON_WALK_ELEMENT = "walkSpeed";
	public static final String POKEMON_SURF_ELEMENT = "surfSpeed";
	public static final String POKEMON_FLY_ELEMENT = "flySpeed";
	public static final String POKEMON_LAVA_SURF_ELEMENT = "lavaSurfSpeed";
	public static final String POKEMON_JUMP_ELEMENT = "jumpSpeed";
	public static final String POKEMON_CLIMB_ELEMENT = "climbSpeed";
	
	public static final String POKEMON_LOOT_ELEMENT = "loot";
	
	public static final String POKEMON_WIN_EVS_ELEMENT = "winEvs";
	public static final String POKEMON_WIN_EP_ELEMENT = "ep";
	
	public static final String POKEMON_SPAWNS_ELEMENT = "spawns";
//	public static final String POKEMON_SPAWN_ELEMENT = "spawn";
//	public static final String POKEMON_BIOME_ELEMENT = "biome";
//	public static final String POKEMON_CHANCE_ELEMENT = "chance";
//	public static final String POKEMON_DAY_ELEMENT = "day";
//	public static final String POKEMON_RAIN_ELEMENT = "rain";
	
	//Pokedex Values
	private String name = "Missingno.";
	private final String identifier;
	private final String namespace;
	private int index = 1562;
	private List<PokemonType> types = new ArrayList<>(Arrays.asList(PokemonType.NORMAL));
//	private PokemonType secondaryType = PokemonType.NORMAL;
	private float maleChance = 0.5f;
	private List<String> eggGroups = new ArrayList<>();
	private int eggCycles = 0;
	private String category = "Error";
	private float size = 1.0f;
	private float weight = 10.0f;
	private String color = "Grey";
	private String description = "Error";
	private boolean legendary;
	private NamespacedEntry formOf = null;
	private float minLikeEquality = 0;
	private float maxDislikeEquality = 0;
	private PokemonEntityAbilityType entityAbility = PokemonEntityAbilityType.HEAL_PLAYER;
	
	//Base Values
//	private int health = 33;
//	private int attack = 136;
//	private int defense = 0;
//	private int specialAttack = 6;
//	private int specialDefense = 6;
//	private int speed = 29;
	private PokemonValueSet baseValues = new PokemonValueSet(33, 136, 0, 6, 6, 29);
	private int friendship = 100;
	private int catchRate = 3;
	private LevelSpeed levelSpeed = LevelSpeed.MIDDLE_FAST;
	private int wildLevel = 50;
	
	//Moves
	private List<MoveLearnCondition> moves = new ArrayList<MoveLearnCondition>(Arrays.asList(new MoveLearnCondition(new NamespacedEntry(PokeminePlugin.POKEMINE_NAMESPACE, "pound"), 1)));
	//Evolutions
	private List<EvolutionCause> evolutions = new ArrayList<>();
	
	//Field Moves
	private float walkSpeed = 1;
	private float surfSpeed = 0;
	private float flySpeed = 0;
	private float lavaSurfSpeed = 0;
	private float climbSpeed = 0;
	private float jumpSpeed = 0.5f;
	
	//Loot
	private LootInventory loot;
	
	//Win
	private int winEp = 0;
	private PokemonValueSet winEvs = new PokemonValueSet();
//	private int healthFp = 0;
//	private int attackFp = 0;
//	private int defenseFp = 0;
//	private int specialAttackFp = 0;
//	private int specialDefenseFp = 0;
//	private int speedFp = 0;
	
	//Spawn Chance
//	private Map<EnvironmentSituation, Integer> spawns = new HashMap<>();
	private List<ISpawnChance> spawns = new ArrayList<>();
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		TypedMap tMap = new TypedMap(map);
		map.put(POKEMON_SPECIES_NAME_ELEMENT, name);
		map.put(POKEMON_IDENTIFIER_ELEMENT, new NamespacedEntry(namespace, identifier));
		map.put(POKEMON_INDEX_ELEMENT, index);
		tMap.putEnumList(POKEMON_TYPES_ELEMENT, types);
		map.put(POKEMON_MALE_CHANCE_ELEMENT, maleChance);
		map.put(POKEMON_EGG_GROUPS_ELEMENT, eggGroups);
		map.put(POKEMON_EGG_CYCLES_ELEMENT, eggCycles);
		map.put(POKEMON_CATEGORY_ELEMENT, category);
		map.put(POKEMON_SIZE_ELEMENT, size);
		map.put(POKEMON_WEIGHT_ELEMENT, weight);
		map.put(POKEMON_COLOR_ELEMENT, color);
		map.put(POKEMON_DESCRIPTION_ELEMENT, description);
		map.put(POKEMON_LEGENDARY_ELEMENT, legendary);
		map.put(POKEMON_ALTERNATIVE_FORM_ELEMENT, formOf);
		
		map.put(BASE_VALUES_ELEMENT, baseValues);
		map.put(POKEMON_FRIENDSHIP_ELEMENT, friendship);
		map.put(POKEMON_CATCH_RATE_ELEMENT, catchRate);
		map.put(POKEMON_LEVEL_EP_ELEMENT, levelSpeed + "");
		map.put(POKEMON_WILD_LEVEL_ELEMENT, wildLevel);
		map.put(POKEMON_MIN_LIKE_EQUALITY_ELEMENT, minLikeEquality);
		map.put(POKEMON_MAX_DISLIKE_EQUALITY_ELEMENT, maxDislikeEquality);
		
		map.put(MOVES_ELEMENT, moves);
		map.put(EVOLUTIONS_ELEMENT, evolutions);
		
		map.put(POKEMON_WALK_ELEMENT, walkSpeed);
		map.put(POKEMON_SURF_ELEMENT, surfSpeed);
		map.put(POKEMON_FLY_ELEMENT, flySpeed);
		map.put(POKEMON_LAVA_SURF_ELEMENT, lavaSurfSpeed);
		map.put(POKEMON_JUMP_ELEMENT, jumpSpeed);
		map.put(POKEMON_CLIMB_ELEMENT, climbSpeed);
		
		map.put(POKEMON_LOOT_ELEMENT, loot);
		
		map.put(POKEMON_WIN_EP_ELEMENT, winEp);
		map.put(POKEMON_WIN_EVS_ELEMENT, winEvs);
		
		map.put(POKEMON_SPAWNS_ELEMENT, spawns);
		
		return map;
	}
	
	public static PokemonSpecies deserialize (Map<String, Object> map) {
		TypedMap tMap = new TypedMap(map);
		NamespacedEntry entry = tMap.get(POKEMON_IDENTIFIER_ELEMENT, NamespacedEntry.class, new NamespacedEntry(PokeminePlugin.POKEMINE_NAMESPACE, "missingno"));
		PokemonSpecies species = new PokemonSpecies(entry.getNamespace(), entry.getIdentifier());
		species.setName(tMap.getString(POKEMON_SPECIES_NAME_ELEMENT));
		species.setIndex(tMap.getInt(POKEMON_INDEX_ELEMENT));
		species.setTypes(tMap.getEnumList(POKEMON_TYPES_ELEMENT, PokemonType.class));
		species.setMaleChance(tMap.getFloat(POKEMON_MALE_CHANCE_ELEMENT));
		species.setEggGroups(tMap.getList(POKEMON_EGG_GROUPS_ELEMENT, String.class));
		species.setEggCycles(tMap.getInt(POKEMON_EGG_CYCLES_ELEMENT));
		species.setCategory(tMap.getString(POKEMON_CATEGORY_ELEMENT));
		species.setSize(tMap.getFloat(POKEMON_SIZE_ELEMENT));
		species.setWeight(tMap.getFloat(POKEMON_WEIGHT_ELEMENT));
		species.setColor(tMap.getString(POKEMON_COLOR_ELEMENT));
		species.setDescription(tMap.getString(POKEMON_DESCRIPTION_ELEMENT));
		species.setLegendary(tMap.getBoolean(POKEMON_LEGENDARY_ELEMENT));
		species.setFormOf(tMap.get(POKEMON_ALTERNATIVE_FORM_ELEMENT, NamespacedEntry.class, null));
		
		species.setBaseValues(tMap.get(BASE_VALUES_ELEMENT, PokemonValueSet.class, species.getBaseValues()));
		species.setFriendship(tMap.getInt(POKEMON_FRIENDSHIP_ELEMENT));
		species.setCatchRate(tMap.getInt(POKEMON_CATCH_RATE_ELEMENT));
		species.setLevelEP(tMap.getEnum(POKEMON_LEVEL_EP_ELEMENT, LevelSpeed.class, LevelSpeed.MIDDLE_FAST));
		species.setWildLevel(tMap.getInt(POKEMON_WILD_LEVEL_ELEMENT));
		species.setMinLikeEquality(tMap.getFloat(POKEMON_MIN_LIKE_EQUALITY_ELEMENT));
		species.setMaxDislikeEquality(tMap.getFloat(POKEMON_MAX_DISLIKE_EQUALITY_ELEMENT));
		
		species.setMoves(tMap.getList(MOVES_ELEMENT, MoveLearnCondition.class));
		species.setEvolutions(tMap.getList(EVOLUTIONS_ELEMENT, EvolutionCause.class));
		
		species.setWalkSpeed(tMap.getFloat(POKEMON_WALK_ELEMENT));
		species.setSurfSpeed(tMap.getFloat(POKEMON_SURF_ELEMENT));
		species.setFlySpeed(tMap.getFloat(POKEMON_FLY_ELEMENT));
		species.setLavaSurfSpeed(tMap.getFloat(POKEMON_LAVA_SURF_ELEMENT));
		species.setJumpSpeed(tMap.getFloat(POKEMON_JUMP_ELEMENT));
		species.setClimbSpeed(tMap.getFloat(POKEMON_CLIMB_ELEMENT));
		
		species.setLoot(tMap.get(POKEMON_LOOT_ELEMENT, LootInventory.class, new LootInventory()));
		
		species.setWinXp(tMap.getInt(POKEMON_WIN_EP_ELEMENT));
		species.setWinEvs(tMap.get(POKEMON_WIN_EVS_ELEMENT, PokemonValueSet.class, new PokemonValueSet ()));
		
		species.setSpawns(tMap.getList(POKEMON_SPAWNS_ELEMENT, ISpawnChance.class));
		
		return species;
	}

	public PokemonSpecies(String namespace, String identifier) {
		this.namespace = namespace;
		this.identifier = identifier;
	}
	
	public boolean hasType (PokemonType type) {
		return types.stream().anyMatch(t -> t == type);
	}
	
	public boolean canLearn (Move move) {
		boolean can = false;
		for (int i = 0; i < moves.size() && !can; i++) {
			can = move.toNamespacedEntry().equals(moves.get(i).getMove());
		}
		return can;
	}
	
//	@Override
//	public String toString() {
//		StringBuilder types = new StringBuilder();
//		PokemonType[] types
//		for (int i = 0; i < getTypes().length; i++) {
//			types.append(getTypes()[i]);
//			if (i < getTypes().length - 1) {
//				types.append("/");
//			}
//		}
//		return getName() + "[" + getIndex() + "] ist ein " + types + " Pokemon";
//	}

	public String getName() {
		return name;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	public int getIndex() {
		return index;
	}

	public List<PokemonType> getTypes() {
		return types;
	}

//	public PokemonType getSecondaryType() {
//		return secondaryType;
//	}

	public float getMaleChance() {
		return maleChance;
	}

	public List<String> getEggGroups() {
		return eggGroups;
	}

	public int getEggCycles() {
		return eggCycles;
	}

	public String getCategory() {
		return category;
	}

	public float getSize() {
		return size;
	}

	public float getWeight() {
		return weight;
	}

	public String getColor() {
		return color;
	}
	
	public String getDescrition() {
		return description;
	}

	public int getHealth() {
		return baseValues.getHealth();
	}

	public int getAttack() {
		return baseValues.getAttack();
	}

	public int getDefense() {
		return baseValues.getDefense();
	}

	public int getSpecialAttack() {
		return baseValues.getSpecialAttack();
	}

	public int getSpecialDefense() {
		return baseValues.getSpecialDefense();
	}

	public int getSpeed() {
		return baseValues.getSpeed();
	}

	public int getFriendship() {
		return friendship;
	}

	public int getCatchRate() {
		return catchRate;
	}

	public LevelSpeed getLevelEP() {
		return levelSpeed;
	}

	public void setName(String name) {
		this.name = name;
	}
//	
//	@Override
//	public void setIdentifier(String identifier) {
//		this.identifier = identifier;
//	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setTypes(List<PokemonType> types) {
		this.types = types;
	}

//	public void setSecondaryType(PokemonType secondaryType) {
//		this.secondaryType = secondaryType;
//	}

	public void setEggCycles(int eggCycles) {
		this.eggCycles = eggCycles;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

//	public void setHealth(int health) {
//		this.health = health;
//	}
//
//	public void setAttack(int attack) {
//		this.attack = attack;
//	}
//
//	public void setDefense(int defense) {
//		this.defense = defense;
//	}
//
//	public void setSpecialAttack(int specialAttack) {
//		this.specialAttack = specialAttack;
//	}
//
//	public void setSpecialDefense(int specialDefense) {
//		this.specialDefense = specialDefense;
//	}
//
//	public void setSpeed(int speed) {
//		this.speed = speed;
//	}

	public void setFriendship(int friendship) {
		this.friendship = friendship;
	}

	public void setCatchRate(int catchRate) {
		this.catchRate = catchRate;
	}

	public void setLevelEP(LevelSpeed levelEP) {
		this.levelSpeed = levelEP;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void addMove (MoveLearnCondition move) {
		moves.add(move);
	}
	
	public List<MoveLearnCondition> getLearnableMoves () {
		return Collections.unmodifiableList(moves);
	}
	
	public void clearMoves() {
		moves.clear();
	}
	
	public List<EvolutionCause> getEvolutions () {
		return Collections.unmodifiableList(evolutions);
	}
	
	public void addEvolution (EvolutionCause cause) {
		evolutions.add(cause);
	}
	
	public void setMaleChance(float maleChance) {
		this.maleChance = maleChance;
	}

	public void setEggGroups(List<String> eggGroups) {
		this.eggGroups = eggGroups;
	}

	public float getWalkSpeed() {
		return walkSpeed;
	}
	
	public boolean canWalk () {
		return getWalkSpeed() > 0.0001;
	}

	public void setWalkSpeed(float walkSpeed) {
		this.walkSpeed = walkSpeed;
	}

	public float getSurfSpeed() {
		return surfSpeed;
	}
	
	public boolean canSurf () {
		return getSurfSpeed() > 0.0001;
	}
	
	public void setSurfSpeed(float surfSpeed) {
		this.surfSpeed = surfSpeed;
	}

	public float getFlySpeed() {
		return flySpeed;
	}
	
	public boolean canFly () {
		return getFlySpeed() > 0.0001;
	}

	public void setFlySpeed(float flySpeed) {
		this.flySpeed = flySpeed;
	}

	public float getLavaSurfSpeed() {
		return lavaSurfSpeed;
	}

	public void setLavaSurfSpeed(float lavaSurfSpeed) {
		this.lavaSurfSpeed = lavaSurfSpeed;
	}
	
	public boolean canLavaSurf () {
		return getLavaSurfSpeed() > 0.0001;
	}

	public float getClimbSpeed() {
		return climbSpeed;
	}

	public void setClimbSpeed(float climbSpeed) {
		this.climbSpeed = climbSpeed;
	}
	
	public boolean canClimb () {
		return getClimbSpeed() > 0.0001;
	}

	public float getJumpSpeed() {
		return jumpSpeed;
	}

	public void setJumpSpeed(float jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}
	
	public boolean canJump () {
		return getJumpSpeed() > 0.0001;
	}

	public int getWinXp() {
		return winEp;
	}

	public void setWinXp(int winXp) {
		this.winEp = winXp;
	}

	public int getHealthFp() {
		return winEvs.getHealth();
	}

	public int getAttackFp() {
		return winEvs.getAttack();
	}
	
	public int getDefenseFp() {
		return winEvs.getDefense();
	}

	public int getSpecialAttackFp() {
		return winEvs.getSpecialAttack();
	}

	public int getSpecialDefenseFp() {
		return winEvs.getSpecialDefense();
	}

	public int getSpeedFp() {
		return winEvs.getSpeed();
	}

	public PokemonValueSet getWinEvs() {
		return winEvs;
	}

	public void setWinEvs(PokemonValueSet winEvs) {
		this.winEvs = winEvs;
	}

	public int getWildLevel() {
		return wildLevel;
	}

	public void setWildLevel(int wildLevel) {
		this.wildLevel = wildLevel;
	}
	
	public List<ISpawnChance> getSpawns() {
		return spawns;
	}

	public void setSpawns(List<ISpawnChance> spawns) {
		this.spawns = spawns;
	}
	
	public void addSpawn (ISpawnChance chance) {
		this.spawns.add(chance);
	}

	public int getSpawnChance (EnvironmentSituation env) {
		int chance = 0;
		for (ISpawnChance spawn : spawns) {
			int c = spawn.getSpawnChance(env);
			if (chance < c) {
				chance = c;
			}
		}
		return chance;
	}

	public LootInventory getLoot() {
		return loot;
	}

	public void setLoot(LootInventory loot) {
		this.loot = loot;
	}

	@Override
	public String getNamespace() {
		return namespace;
	}

	public boolean isLegendary() {
		return legendary;
	}

	public void setLegendary(boolean legendary) {
		this.legendary = legendary;
	}
	
	public boolean isAlternativeForm() {
		return formOf != null;
	}

	public NamespacedEntry getFormOf() {
		return formOf;
	}

	public void setFormOf(NamespacedEntry formOf) {
		this.formOf = formOf;
	}

	public PokemonValueSet getBaseValues() {
		return baseValues;
	}

	public void setBaseValues(PokemonValueSet baseValues) {
		this.baseValues = baseValues;
	}

	public List<MoveLearnCondition> getMoves() {
		return moves;
	}

	public void setMoves(List<MoveLearnCondition> moves) {
		this.moves = moves;
	}

	public void setEvolutions(List<EvolutionCause> evolutions) {
		this.evolutions = evolutions;
	}

	public float getMinLikeEquality() {
		return minLikeEquality;
	}

	public void setMinLikeEquality(float minLikeEquality) {
		this.minLikeEquality = minLikeEquality;
	}

	public float getMaxDislikeEquality() {
		return maxDislikeEquality;
	}

	public void setMaxDislikeEquality(float maxDislikeEquality) {
		this.maxDislikeEquality = maxDislikeEquality;
	}

	public PokemonEntityAbilityType getEntityAbility() {
		return entityAbility;
	}

	public void setEntityAbility(PokemonEntityAbilityType entityAbility) {
		this.entityAbility = entityAbility;
	}

	public ItemStack toItemStack () {
		ItemStack item  = new ItemStack(isAlternativeForm() ? POKEMON_FORM_MATERIAL : POKEMON_MATERIAL);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(getIndex());
		meta.setUnbreakable(true);
		meta.setDisplayName(getIndex() + ". " + getName());
		List<String> lore = new ArrayList<String>();
		//Types to String
		StringBuilder types = new StringBuilder();
		int i = 0;
		for (PokemonType type : this.types) {
			types.append(type.getColor() + type.toString());
			if (i < this.types.size() - 1) {
				types.append("/");
			}
			i++;
		}
		
		lore.add(types.toString());
		lore.add("Size: " + getSize());
		lore.add("Weight: " + getWeight());
		lore.add(getDescription());
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
}
