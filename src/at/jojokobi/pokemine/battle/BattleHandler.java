package at.jojokobi.pokemine.battle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.lang.Runnable;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.trainer.Trainer;

public class BattleHandler implements Listener{
	
	private List<Battle> battles = new ArrayList<>();
	private PokeminePlugin plugin;
	
	public BattleHandler(PokeminePlugin plugin) {
		this.plugin = plugin;
		//Battle Loop
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			@Override
			public void run() {
				for (Battle battle : new ArrayList<>(battles)) {
					battle.loop();
				}
			}
		}, 1L, 1L);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit (PlayerQuitEvent event) {
		Trainer trainer = plugin.getPlayerTrainerHandler().getTrainer(event.getPlayer());
		for (int i = 0; i < battles.size(); i++) {
			if (Arrays.stream(battles.get(i).getPokemon()).anyMatch((Pokemon pokemon) -> pokemon.getOwner() == trainer)) {
				battles.get(i).end();
			}
		}
	}
	
	public List<Battle> getBattles() {
		return Collections.unmodifiableList(battles);
	}
	
	protected void startBattle (Battle battle) {
		battles.add(battle);
	}
	
	public void endBattle (Battle battle) {
		battles.remove(battle);
	}
	
	public Battle getPokemonsBattle (Pokemon pokemon) {
		Battle battle = null;
		for (int i = 0; i < battles.size() && battle == null; i++) {
			if (Arrays.stream(battles.get(i).getPokemon()).anyMatch((Pokemon poke) -> poke == pokemon)) {
				battle = battles.get(i);
			}
		}
		return battle;
	}
	
	public boolean isBattling (Pokemon pokemon) {
		return getPokemonsBattle(pokemon) != null;
	}
	
	public Battle getTrainersBattle (Trainer trainer) {
		Battle battle = null;
		for (int i = 0; i < battles.size() && battle == null; i++) {
			if (Arrays.stream(battles.get(i).getPokemon()).anyMatch((Pokemon pokemon) -> pokemon.getOwner() == trainer)) {
				battle = battles.get(i);
			}
		}
		return battle;
	}
	
	public PokeminePlugin getPlugin () {
		return plugin;
	}
	
}
