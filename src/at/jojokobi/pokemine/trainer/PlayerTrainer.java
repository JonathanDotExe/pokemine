package at.jojokobi.pokemine.trainer;

import java.util.Arrays;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.items.ExpShare;

public class PlayerTrainer extends Trainer{
	
	private Player player;
//	private List<PokemonSpecies> pokedex = new ArrayList<>();
//	private int money = 0;
	
	public PlayerTrainer (Player player) {
		this.player = player;
	}
	
	
	/*
	 * Use {@link PlayerTrainer#getEntity()}
	 * @return
	 */
//	public Player getPlayer() {
//		return player;
//	}

	void setPlayer(Player player) {
		if (this.player == null) {
			this.player = player;
		}
	}

	@Override
	public void message(String message) {
		player.sendMessage(message);
	}

	@Override
	public Entity getEntity() {
		return player;
	}


	@Override
	public boolean hasExpShare() {
		return Arrays.stream(player.getInventory().getContents()).anyMatch((item) -> ItemHandler.getCustomItem(PokeminePlugin.POKEMINE_NAMESPACE, ExpShare.IDENTIFIER).isItem(item));
	}
	
//	public void addToPokedex(PokemonSpecies species) {
//		if (!hasInPokedex(species)) {
//			pokedex.add(species);
//		}
//	}
//	
//	public List<PokemonSpecies> getPokedex () {
//		return new ArrayList<>(pokedex);
//	}
//	
//	public boolean hasInPokedex (PokemonSpecies species) {
//		return pokedex.contains(species);
//	}
//	
//	public int getMoney() {
//		return money;
//	}
//
//	public void setMoney(int money) {
//		this.money = money;
//	}
//	
//	public void addMoney(int money) {
//		this.money += money;
//	}
//
//	@Override
//	public void givePokemon(Pokemon pokemon) {
//		super.givePokemon(pokemon);
//		addToPokedex(pokemon.getSpecies());
//	}
//	
//	@Override
//	protected void setParty(Pokemon[] party) {
//		super.setParty(party);
//		for (Pokemon poke : party) {
//			if (poke != null) {
//				addToPokedex(poke.getSpecies());
//			}
//		}
//	}
//	
//	@Override
//	protected void setPokemon(List<Pokemon> pokemon) {
//		super.setPokemon(pokemon);
//		for (Pokemon poke : pokemon) {
//			addToPokedex(poke.getSpecies());
//		}
//	}
	
	public static PlayerTrainer deserialize (Map<String, Object> map) {
		PlayerTrainer trainer = new PlayerTrainer(null);
		trainer.load(map);
		return trainer;
	}
	
}
