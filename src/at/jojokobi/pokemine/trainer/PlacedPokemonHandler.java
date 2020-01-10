package at.jojokobi.pokemine.trainer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.pokemine.pokemon.PlacedPokemon;
import at.jojokobi.pokemine.pokemon.entity.PokemonBehaviorType;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;

public class PlacedPokemonHandler implements Listener{

	private PlayerTrainerHandler handler;
	private EntityHandler entityHandler;
	
	public PlacedPokemonHandler(PlayerTrainerHandler handler, EntityHandler entityHandler) {
		super();
		this.handler = handler;
		this.entityHandler = entityHandler;
	}

	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event) {
		Trainer trainer = handler.getTrainer(event.getPlayer());
		for (PlacedPokemon poke : trainer.getPlacedPokemonLocations()) {
			if (poke.getLocation() != null && poke.getLocation().isWorldLoaded() && poke.getLocation().getWorld().isChunkLoaded(poke.getLocation().getBlockX()/16, poke.getLocation().getBlockZ()/16)) {
				PokemonEntity entity = new PokemonEntity (poke.getLocation(), trainer.getPokemon(poke.getPokemon()), entityHandler);
				entity.setBehaviorType(PokemonBehaviorType.PLACED_POKEMON);
				entity.setDespawnTicks(-1);
				entityHandler.addEntity(entity);
			}
		}
	}
	
	@EventHandler
	public void onChunkLoad (ChunkLoadEvent event) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			Trainer trainer = handler.getTrainer(player);
			for (PlacedPokemon poke : trainer.getPlacedPokemonLocations()) {
				if (poke.getLocation() != null && event.getChunk().getX() == poke.getLocation().getBlockX()/16 && event.getChunk().getZ() == poke.getLocation().getBlockZ()/16) {
					PokemonEntity entity = new PokemonEntity (poke.getLocation(), trainer.getPokemon(poke.getPokemon()), entityHandler);
					entity.setBehaviorType(PokemonBehaviorType.PLACED_POKEMON);
					entity.setDespawnTicks(-1);
					entityHandler.addEntity(entity);
				}
			}
		}
	}

}
