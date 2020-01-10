package at.jojokobi.pokemine.trainer;

import at.jojokobi.mcutil.Identifiable;
import at.jojokobi.pokemine.pokemon.PokemonSpecies;
import at.jojokobi.pokemine.pokemon.PokemonType;

public class TrainerRank implements Identifiable{

	private String name = "Trainer";
	private final String namespace;
	private final String identifier;
	private int id = 1;
	private boolean spawnable = false;
	private PokemonType[] types = {};
	private String[] names = {"Red"};
	private PokemonSpecies[] usedPokemon = {};
	private String[] startMessages = {"..."};
	private String[] winMessages = {"..."};
	private String[] loseMessages = {"..."};
	private PokemonType badge = null;
	private int basePrizeMoney = 0;
	
	public TrainerRank(String namespace, String identifier) {
		this.namespace = namespace;
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSpawnable() {
		return spawnable;
	}

	public void setSpawnable(boolean spawnable) {
		this.spawnable = spawnable;
	}

	public PokemonType[] getTypes() {
		return types;
	}

	public void setTypes(PokemonType[] types) {
		this.types = types;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public String getRandomStartMessage () {
		return startMessages[(int) (Math.random() * startMessages.length)];
	}
	
	public String[] getStartMessages() {
		return startMessages;
	}

	public void setStartMessages(String[] startMessages) {
		this.startMessages = startMessages;
	}
	
	public String getRandomWinMessage () {
		return winMessages[(int) (Math.random() * winMessages.length)];
	}

	public String[] getWinMessages() {
		return winMessages;
	}

	public void setWinMessages(String[] winMessages) {
		this.winMessages = winMessages;
	}
	
	public String getRandomLoseMessage () {
		return loseMessages[(int) (Math.random() * loseMessages.length)];
	}

	public String[] getLoseMessages() {
		return loseMessages;
	}

	public void setLoseMessages(String[] loseMessages) {
		this.loseMessages = loseMessages;
	}

	public PokemonSpecies[] getUsedPokemon() {
		return usedPokemon;
	}

	public void setUsedPokemon(PokemonSpecies[] usedPokemon) {
		this.usedPokemon = usedPokemon;
	}

	public PokemonType getBadge() {
		return badge;
	}

	public void setBadge(PokemonType badge) {
		this.badge = badge;
	}

	public int getBasePrizeMoney() {
		return basePrizeMoney;
	}

	public void setBasePrizeMoney(int basePrizeMoney) {
		this.basePrizeMoney = basePrizeMoney;
	}

	@Override
	public String getNamespace() {
		return namespace;
	}

}
