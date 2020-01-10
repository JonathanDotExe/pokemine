package at.jojokobi.pokemine.trainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.SerializableMap;
import at.jojokobi.mcutil.gui.InventoryGUIHandler;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.gui.MoveLearnGUI;
import at.jojokobi.pokemine.pokemon.PlacedPokemon;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.PokemonSpecies;
import at.jojokobi.pokemine.pokemon.PokemonType;

public abstract class Trainer implements ConfigurationSerializable{
	
	private static long lastIDTime = System.currentTimeMillis();
	private static Set<Long> usedIDs = new HashSet<>();
	
	private static UUID generatePokemonID () {
		long time = System.currentTimeMillis();
		if (lastIDTime != time) {
			usedIDs.clear();
			lastIDTime = time;
		}
		long leastSigBits;
		Random random = new Random();
		while (usedIDs.contains(leastSigBits = random.nextLong())) {}
		return new UUID(time, leastSigBits);
	}
	
	public static final int PARTY_SIZE = 6;
	
	public static final String TRAINER_ELEMENT = "trainer";
	public static final String TRAINER_NAME_ELEMENT = "name";
	public static final String TRAINER_RANK_ELEMENT = "rank";
	public static final String TRAINER_POKEMON_ELEMENT = "pokemon";
	public static final String TRAINER_PARTY_ELEMENT = "party";
	public static final String TRAINER_PC_ELEMENT = "pc";
	public static final String TRAINER_PLACED_POKEMON_ELEMENT = "placedPokemon";
	public static final String TRAINER_BADGES_ELEMENT = "badges";
	public static final String TRAINER_POKEDEX_ELEMENT = "pokedex";
	public static final String TRAINER_MONEY_ELEMENT = "money";
	public static final String TRAINER_ELITE_FOUR_LEVEL_ELEMENT = "eliteFourLevel";
	public static final String TRAINER_DEFEATED_CHAMP_ELEMENT = "defeatedChamp";
	
	public static final int CHAMP_LEVEL = 5;
	
	private String name = "Trainer";
	private TrainerRank rank = new TrainerRank(PokeminePlugin.POKEMINE_NAMESPACE, "trainer");
//	private Pokemon[] party = new Pokemon[6];
//	private List<Pokemon> pokemon = new ArrayList<Pokemon>();
	private Map<UUID, Pokemon> pokemon = new HashMap<>();
	private List<UUID> party = new ArrayList<>();
	private List<UUID> pc = new ArrayList<>();
	private List<PlacedPokemon> placedPokemon = new ArrayList<>();
	private List<NamespacedEntry> pokedex = new ArrayList<>();
	private int money = 0;
	private int eliteFourLevel = 0;
	private int eliteFourDefeatLevel = 0;
	private boolean defeatedChamp = false;
	
	private Map<PokemonType, Integer> badges = new HashMap<>();
	
	public abstract Entity getEntity ();
	
	private void givePokemon (Pokemon pokemon, UUID id) {
		if (!hasPokemon(pokemon) && pokemon.isWild()) {
			pokemon.setOwner(this);
			this.pokemon.put(id, pokemon);
			if (party.size() < PARTY_SIZE) {
				party.add(id);
			}
			else {
				pc.add(id);
			}
		}
//		for (int i = 0; i < party.length && !finished; i++) {
//			if (party[i] == null) {
//				party[i] = pokemon;
//				finished = true;
//			}
//		}
//		if (!finished) {
//			this.pokemon.add(pokemon);
//		}
		addToPokedex(pokemon.getSpecies());
	}
	
	public void givePokemon (Pokemon pokemon) {
		givePokemon(pokemon, generatePokemonID());
	}
	
	public void removePokemon (Pokemon pokemon) {
//		boolean finished = false;
		UUID id = getPokemonUUID(pokemon);
		if (id != null) {
			pokemon.setOwner(new WildPokemonTrainer());
			this.pokemon.remove(id);
			party.remove(id);
			pc.remove(id);
		}
		
//		for (int i = 0; i < party.length && !finished; i++) {
//			if (party[i] == pokemon) {
//				party[i] = null;
//				finished = true;
//			}
//		}
//		if (!finished) {
//			this.pokemon.remove(pokemon);
//		}
	}
	
	public boolean hasPokemon (Pokemon pokemon) {
		return getPokemonUUID(pokemon) != null;
	}
	
	public UUID getPokemonUUID(Pokemon pokemon) {
		Map.Entry<UUID, Pokemon> entry = this.pokemon.entrySet().stream().filter(e -> e.getValue().equals(pokemon)).findFirst().orElseGet(() -> null);
		return entry == null ? null : entry.getKey();
	}

	public void swapPokemon (Pokemon pokemon1, Pokemon pokemon2) {
		if (pokemon1 != null && pokemon2 != null) {
			UUID id1 = getPokemonUUID(pokemon1);
			UUID id2 = getPokemonUUID(pokemon2);
			if (id1 != null && id2 != null) {
				PokemonSectionController list1 = getContainingList(id1);
				PokemonSectionController list2 = getContainingList(id2);
				if (list1 != null && list2 != null && list1.canSet() && list2.canSet()) {
					int index1 = list1.indexOf(id1);
					int index2 = list2.indexOf(id2);
					
					list1.set(index1, id2);
					list2.set(index2, id1);
				}
			}
//			int index1 = -1;
//			int index2 = -1;
//			boolean pc1 = false;
//			boolean pc2 = false;
//			HashSet<Pokemon> pokecopy= new HashSet<>(pokemon);
//			//Pokemon1
//			boolean swap = false;
//			if (Arrays.asList(party).contains(pokemon1)) {
//				index1 = Arrays.asList(party).indexOf(pokemon1);
////				party[index] = pokemon2;
//				swap = true;
//			}
//			else if (pokemon.contains(pokemon1)) {
//				index1 = pokemon.indexOf(pokemon1);
////				pokemon.set(index, pokemon2);
//				pc1 = true;
//				swap = true;
//			}
//			if (swap) {
//				//Pokemon2
//				if (Arrays.asList(party).contains(pokemon2)) {
//					index2 = Arrays.asList(party).indexOf(pokemon2);
////					party[Arrays.asList(party).indexOf(pokemon2)] = pokemon1;
//				}
//				else if (pokemon.contains(pokemon2)) {
//					index2 = pokemon.indexOf(pokemon2);
//					pc2 = true;
////					pokemon.set(pokemon.indexOf(pokemon2), pokemon1);
//				}
//				//Swap
//				if (pc1) {
//					pokemon.set(index1, pokemon2);
//				}
//				else {
//					party[index1] = pokemon2;
//				}
//				if (pc2) {
//					pokemon.set(index2, pokemon1);
//				}
//				else {
//					party[index2] = pokemon1;
//				}
//			}
//			message(pokecopy.equals(new HashSet<Pokemon>(pokemon)) + "");
		}
	}
	
	private PokemonSectionController getContainingList (UUID id) {
		PokemonSectionController controller = null;
		PlacedPokemonController placedController = new PlacedPokemonController(getEntity(), placedPokemon);
		if (party.contains(id)) {
			controller = new PartyPCController(party);
		}
		else if (pc.contains(id)) {
			controller = new PartyPCController(pc);
		}
		else if (placedController.contains(id)) {
			controller = placedController;
		}
		return controller;
	}
	
	public byte getLevel () {
		int sum = 0;
		int count = 0;
		for (Pokemon pokemon : getParty()) {
			sum += pokemon.getLevel();
			count++;
		}
		return count == 0 ? (byte) 1 : (byte) (sum/count);
	}
	public abstract void message(String message);

	public void moveToPc (Pokemon pokemon) {
//		try {
//			int index = Arrays.asList(party).indexOf(pokemon);
//			party[index] = null;
//			this.pokemon.add(pokemon);
//		}
//		catch (ArrayIndexOutOfBoundsException e) {
//			e.printStackTrace();
//		}
		UUID id = getPokemonUUID(pokemon);
		if (id != null) {
			moveToPc(id);
		}
	}
	
	private void moveToPc (UUID id) {
		if (!pc.contains(id)) {
			PokemonSectionController list = getContainingList(id);
			if (list != null) {
				list.remove(id);
			}
			pc.add(id);
		}
	}
	
	private boolean makePlaced (UUID id) {
		PlacedPokemonController controller = new PlacedPokemonController(getEntity(), placedPokemon);
		if (!controller.contains(id) && controller.canAdd() && placedPokemon.size() < getHighestBadge()) {
			PokemonSectionController list = getContainingList(id);
			if (list != null) {
				list.remove(id);
			}
			boolean added = controller.add(id);
			System.out.println(placedPokemon);
			return added;
		}
		return false;
	}
	
	public boolean makePlaced (Pokemon pokemon) {
		UUID id = getPokemonUUID(pokemon);
		if (id != null) {
			return makePlaced(id);
		}
		return false;
	}
	
	public void moveToParty (Pokemon pokemon) {
//		if (getPokemon().contains(pokemon)) {
//			boolean full = true;
//			for (int i = 0; i < party.length && full; i++) {
//				if (party[i] == null) {
//					party[i] = pokemon;
//					full = false;
//				}
//			}
//			if (!full) {
//				this.pokemon.remove(pokemon);
//			}
//		}
		UUID id = getPokemonUUID(pokemon);
		if (id != null) {
			moveToParty(id);
		}
	}
	
	private void moveToParty (UUID id) {
		if (!party.contains(id) && party.size() < PARTY_SIZE) {
			PokemonSectionController list = getContainingList(id);
			if (list != null) {
				list.remove(id);
			}
			party.add(id);
		}
	}
	
	public Pokemon getNextUsablePokemon () {
		Pokemon pokemon = null;
//		for (int i = 0; i < party.length && pokemon == null; i++) {
//			if (party[i] != null && party[i].getHealth() > 0) {
//				pokemon = party[i];
//			}
//		}
		for (Iterator<Pokemon> iterator = getParty().iterator(); iterator.hasNext() && pokemon == null;) {
			Pokemon poke = iterator.next();
			if (poke.getHealth() > 0) {
				pokemon = poke;
			}
		}
		return pokemon;
	}
	
	public boolean hasNextUsablePokemon () {
		return getNextUsablePokemon() != null;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public TrainerRank getRank() {
		return rank;
	}
	
	public void setRank(TrainerRank rank) {
		this.rank = rank;
	}
	
	public List<Pokemon> getParty() {
		return Arrays.asList(party.stream().map(id -> pokemon.get(id)).toArray(Pokemon[]::new));
	}
	
	public List<Pokemon> getPlacedPokemon() {
		return Arrays.asList(placedPokemon.stream().map(id -> pokemon.get(id.getPokemon())).toArray(Pokemon[]::new));
	}
	
	public List<PlacedPokemon> getPlacedPokemonLocations() {
		return Arrays.asList(placedPokemon.toArray(PlacedPokemon[]::new));
	}
	
	public Pokemon getPokemon (UUID id) {
		return pokemon.get(id);
	}
	
//	protected void setParty(Pokemon[] party) {
//		this.party = party;
//		
//		for (Pokemon poke : party) {
//			if (poke != null) {
//				addToPokedex(poke.getSpecies());
//			}
//		}
//	}
	
	public List<Pokemon> getPokemon() {
		return Arrays.asList(pc.stream().map(id -> pokemon.get(id)).toArray(Pokemon[]::new));
	}
	
	public void recieveBadge (PokemonType type, int level) {
		if (badges.get(type) == null || badges.get(type) < level) {
			badges.put(type, level);
		}
	}
	
	public int getBadge (PokemonType type) {
		return badges.get(type) == null ? 0 : badges.get(type);
	}
	
	public Map<PokemonType, Integer> getBadges () {
		return Collections.unmodifiableMap(badges);
	}
	
//	protected void setPokemon(List<Pokemon> pokemon) {
//		this.pokemon = pokemon;
//		for (Pokemon poke : pokemon) {
//			addToPokedex(poke.getSpecies());
//		}
//	}
	
	public void trade (Pokemon pokemon, Pokemon otherPokemon, PokemonHandler handler, InventoryGUIHandler guiHandler) {
		Trainer owner = otherPokemon.getOwner();
		removePokemon(pokemon);
		owner.removePokemon(otherPokemon);
		owner.givePokemon(pokemon);
		givePokemon(otherPokemon);
		boolean evolved1 = pokemon.evolve(handler, null, true);
		boolean evolved2 = otherPokemon.evolve(handler, null, true);
		if (evolved1) {
			if (pokemon.getOwner().getEntity() instanceof Player) {
				MoveLearnGUI.learnMoves(guiHandler, pokemon);
			}
		}
		if (evolved2) {
			if (otherPokemon.getOwner().getEntity() instanceof Player) {
				MoveLearnGUI.learnMoves(guiHandler, otherPokemon);
			}
		}
//		if (moves1 != null) {
//			pokemon.learnMoves(moves1, guiHandler);
//		}
//		if (moves2 != null) {
//			otherPokemon.learnMoves(moves2, guiHandler);
//		}
	}
	
	public void addToPokedex(NamespacedEntry species) {
		if (!hasInPokedex(species)) {
			pokedex.add(species);
		}
	}
	
	public void addToPokedex(PokemonSpecies species) {
		addToPokedex(species.toNamespacedEntry());
	}
	
	public List<NamespacedEntry> getPokedex () {
		return new ArrayList<>(pokedex);
	}
	
	public List<PokemonSpecies> getPokedexSpecies () {
		return Arrays.asList(pokedex.stream().map(e -> PokemonHandler.getInstance().getItem(e)).toArray(PokemonSpecies[]::new));
	}
	
	public boolean hasInPokedex (PokemonSpecies species) {
		return hasInPokedex(species.toNamespacedEntry());
	}
	
	public boolean hasInPokedex (NamespacedEntry species) {
		return pokedex.contains(species);
	}
	
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	public void addMoney(int money) {
		this.money += money;
	}
	
	public abstract boolean hasExpShare ();
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put(TRAINER_NAME_ELEMENT, name);
		map.put(TRAINER_RANK_ELEMENT, rank.toNamespacedEntry());
		//Pokemon
		Map<String, Pokemon> pokemon = new HashMap<>();
		for (UUID id : this.pokemon.keySet()) {
			pokemon.put(id + "", this.pokemon.get(id));
		}
//		List<Pokemon> party = new ArrayList<Pokemon>(Arrays.asList(this.party));
//		party.removeAll(Collections.singleton(null));
		map.put(TRAINER_POKEMON_ELEMENT, new SerializableMap(pokemon));
		map.put(TRAINER_PARTY_ELEMENT, Arrays.asList(party.stream().map(id -> id + "").toArray()));
		map.put(TRAINER_PC_ELEMENT, Arrays.asList(pc.stream().map(id -> id + "").toArray()));
		map.put(TRAINER_PLACED_POKEMON_ELEMENT, placedPokemon);
		//Badges
		Map<String, Integer> badges = new HashMap<>();
		for (PokemonType type : this.badges.keySet()) {
			badges.put(type.toString().toLowerCase(), this.badges.get(type));
		}
		map.put(TRAINER_BADGES_ELEMENT, new SerializableMap(badges));
		//Pokedex
		map.put(TRAINER_POKEDEX_ELEMENT, pokedex);
		//Money
		map.put(TRAINER_MONEY_ELEMENT, money);
		//Elite Four Level
		map.put(TRAINER_ELITE_FOUR_LEVEL_ELEMENT, eliteFourLevel);
		//Defeated Champ
		map.put(TRAINER_DEFEATED_CHAMP_ELEMENT, defeatedChamp);
		return map;
	}
	
	public void load (Map<String, Object> map) {
		setName (map.get(TRAINER_NAME_ELEMENT) + "");
		//Rank
		if (map.get(TRAINER_RANK_ELEMENT) instanceof NamespacedEntry) {
			setRank(TrainerRankHandler.getInstance().getItem((NamespacedEntry) map.get(TRAINER_RANK_ELEMENT)));
		}
		//Pokemon
		if (map.get(TRAINER_POKEMON_ELEMENT) instanceof SerializableMap) {
			for (Map.Entry<String, ?> entry : ((SerializableMap) map.get(TRAINER_POKEMON_ELEMENT)).getData().entrySet()) {
				if (entry.getValue() instanceof Pokemon) {
					UUID id = null;
					try {
						id = UUID.fromString(entry.getKey());
					}
					catch (IllegalArgumentException e) {
						e.printStackTrace();
						id = generatePokemonID();
					}
					givePokemon((Pokemon) entry.getValue(), id);
				}
			}
			if (this instanceof PlayerTrainer) {
				System.out.println(pokemon);
			}
			//Party
			if (map.get(TRAINER_PARTY_ELEMENT) instanceof List<?>) {
				party.clear();
				for (Object obj : (List<?>) map.get(TRAINER_PARTY_ELEMENT)) {
					try {
						moveToParty(UUID.fromString(obj + ""));
					}
					catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
			//PC
			if (map.get(TRAINER_PC_ELEMENT) instanceof List<?>) {
				pc.clear();
				for (Object obj : (List<?>) map.get(TRAINER_PC_ELEMENT)) {
					try {
						moveToPc(UUID.fromString(obj + ""));
					}
					catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
			//Placed Pokemon
			if (map.get(TRAINER_PLACED_POKEMON_ELEMENT) instanceof List<?>) {
				for (Object obj : (List<?>) map.get(TRAINER_PLACED_POKEMON_ELEMENT)) {
					if (obj instanceof PlacedPokemon) {
						placedPokemon.add((PlacedPokemon) obj);
					}
				}
			}
		}
		//Legacy deserialization
		else {
			//Party
			if (map.get(TRAINER_PARTY_ELEMENT) instanceof List<?>) {
				for (Object obj : (List<?>) map.get(TRAINER_PARTY_ELEMENT)) {
					if (obj instanceof Pokemon) {
						Pokemon pokemon = (Pokemon) obj;
						givePokemon(pokemon);
					}
				}
			}
			//PC
			if (map.get(TRAINER_PC_ELEMENT) instanceof List<?>) {
				for (Object obj : (List<?>) map.get(TRAINER_PC_ELEMENT)) {
					if (obj instanceof Pokemon) {
						Pokemon poke = (Pokemon) obj;
						givePokemon(poke);
						moveToPc(poke);
					}
				}
			}
		}
		//Badges
		if (map.get(TRAINER_BADGES_ELEMENT) instanceof SerializableMap) {
			Map<PokemonType, Integer> badges = new HashMap<>();
			for (Map.Entry<String, ?> entry : ((SerializableMap) map.get(TRAINER_BADGES_ELEMENT)).getData().entrySet()) {
				try {
					badges.put(PokemonType.stringToType(entry.getKey().toUpperCase()), Integer.parseInt(entry.getValue() + ""));
				}
				catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			this.badges = badges;
		}
		//Pokedex
		if (map.get(TRAINER_POKEDEX_ELEMENT) instanceof List<?>) {
			for (Object obj : (List<?>) map.get(TRAINER_POKEDEX_ELEMENT)) {
				if (obj instanceof NamespacedEntry) {
					addToPokedex((NamespacedEntry) obj);
				}
			}
		}
		//Money
		try {
			this.money = Integer.parseInt(map.get(TRAINER_MONEY_ELEMENT) + "");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		//Elite Four Level
		try {
			this.eliteFourLevel = Integer.parseInt(map.get(TRAINER_ELITE_FOUR_LEVEL_ELEMENT) + "");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		//Defeated Champ
		try {
			this.defeatedChamp = Boolean.parseBoolean(map.get(TRAINER_DEFEATED_CHAMP_ELEMENT) + "");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public int getHighestBadge () {
		int level = 0;
		for (PokemonType badge : badges.keySet()) {
			if (level < badges.get(badge)) {
				level = badges.get(badge);
			}
		}
		return level;
	}
	
	public void heal () {
		for (Pokemon pokemon : getParty()) {
			if (pokemon != null) {
				pokemon.heal();
			}
		}
	}

	public int getEliteFourLevel() {
		return eliteFourLevel;
	}

	public void setEliteFourLevel(int eliteFourLevel) {
		this.eliteFourLevel = eliteFourLevel;
	}

	public int getEliteFourDefeatLevel() {
		return eliteFourDefeatLevel;
	}

	public void setEliteFourDefeatLevel(int eliteFourDefeatLevel) {
		this.eliteFourDefeatLevel = eliteFourDefeatLevel;
	}

	public boolean isDefeatedChamp() {
		return defeatedChamp;
	}

	public void setDefeatedChamp(boolean defeatedChamp) {
		this.defeatedChamp = defeatedChamp;
	}
	
	public boolean isChamp () {
		return eliteFourLevel >= CHAMP_LEVEL;
	}
	
}

interface PokemonSectionController {
	
	public boolean add (UUID pokemon);
	
	public void remove (UUID pokemon);
	
	public int indexOf (UUID pokemon);
	
	public default boolean contains (UUID pokemon) {
		return indexOf(pokemon) != -1;
	}
	
	public default boolean canAdd () {
		return true;
	}
	
	public default boolean canSet () {
		return true;
	}
	
	public boolean set (int i, UUID pokemon);
	
	public UUID get (int i);
	
}

class PartyPCController implements PokemonSectionController {
	
	private List<UUID> list;

	public PartyPCController(List<UUID> list) {
		super();
		this.list = list;
	}

	@Override
	public boolean add(UUID pokemon) {
		return list.add(pokemon);
	}

	@Override
	public void remove(UUID pokemon) {
		list.remove(pokemon);
	}

	@Override
	public int indexOf(UUID pokemon) {
		return list.indexOf(pokemon);
	}

	@Override
	public boolean set(int i, UUID pokemon) {
		list.set(i, pokemon);
		return true;
	}

	@Override
	public UUID get(int i) {
		return list.get(i);
	}
	
}

class PlacedPokemonController implements PokemonSectionController {
	
	private Entity entity;
	private List<PlacedPokemon> list;

	public PlacedPokemonController(Entity entity, List<PlacedPokemon> list) {
		super();
		this.entity = entity;
		this.list = list;
	}

	@Override
	public boolean add(UUID pokemon) {
		if (entity != null) {
			return list.add(new PlacedPokemon(pokemon, entity.getLocation()));
		}
		return false;
	}

	@Override
	public void remove(UUID pokemon) {
		list.removeIf(p -> pokemon.equals(p.getPokemon()));
	}

	@Override
	public int indexOf(UUID pokemon) {
		int index = -1;
		int i = 0;
		for (Iterator<PlacedPokemon> iterator = list.iterator(); iterator.hasNext() && index == -1;) {
			PlacedPokemon p = iterator.next();
			if (pokemon.equals(p.getPokemon())) {
				index = i;
			}
		}
		return index;
	}

	@Override
	public boolean set(int i, UUID pokemon) {
		if (entity != null) {
			list.set(i, new PlacedPokemon(pokemon, entity.getLocation()));
			return true;
		}
		return false;
	}

	@Override
	public UUID get(int i) {
		return list.get(i).getPokemon();
	}
	
	@Override
	public boolean canSet() {
		return entity != null;
	}
	
	@Override
	public boolean canAdd() {
		return entity != null;
	}
	
}
