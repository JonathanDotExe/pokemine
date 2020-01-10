package at.jojokobi.pokemine.pokemon.entity;

import java.io.File;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.mcutil.entity.ai.AttackTask;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonSpecies;
import at.jojokobi.pokemine.trainer.Trainer;

@Deprecated
public class PokemonEntityHandler extends EntityHandler<PokemonEntity>{
	
	private PokeminePlugin plugin;
	public static final String POKEMON_ENTITIES_FOLDER = "pokemine" + File.separator + "pokemon";
	
	public PokemonEntityHandler (PokeminePlugin plugin) {
		super(plugin, plugin.getGUIHandler(), POKEMON_ENTITIES_FOLDER);
		this.plugin = plugin;
		getHandler().addItem(new PokemonEntityType(plugin));
	}
	
//	public PokemonEntity getPokemonForStand (ArmorStand stand) {
//		PokemonEntity entity = null;
//		for (int i = 0; i < getEntities().size() && entity == null; i++) {
//			if (getEntities().get(i).getEntity().equals(stand)) {
//				entity = getEntities().get(i);
//			}
//		}
//		return entity;
//	}
	
	public PokemonEntity getEntityForPokemon (Pokemon pokemon) {
		PokemonEntity entity = null;
		for (int i = 0; i < getEntities().size() && entity == null; i++) {
			if (getEntities().get(i).getPokemon() == pokemon){
				entity = getEntities().get(i);
			}
		}
		return entity;
	}
	
	@Override
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event) {
		super.onChunkUnload(event);
	}
	
	@Override
	@EventHandler
	public void onPluginDisabled(PluginDisableEvent event) {
		super.onPluginDisabled(event);
	}
	
	@Override
	@EventHandler
	public void onWorldUnload(WorldUnloadEvent event) {
		super.onWorldUnload(event);
	}
	
	@Override
	@EventHandler
	public void onArmorStandManipulate (PlayerArmorStandManipulateEvent event) {
		super.onArmorStandManipulate(event);
		clickPokemon(event);
	}
	
	@Override
	@EventHandler
	public void onPlayerInteractEntity (PlayerInteractAtEntityEvent event) {
		super.onPlayerInteractEntity(event);
		clickPokemon(event);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerQuit (PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Trainer trainer = plugin.getPlayerTrainerHandler().getTrainer(player);
		for (PokemonEntity pokemon : getEntities()) {
			if (pokemon.getPokemon().getOwner() == trainer) {
				pokemon.delete();
			}
		}
		
	}
	
	@Override
	@EventHandler
	public void onEntityDamage (EntityDamageEvent event) {
		super.onEntityDamage(event);
		//Fall damage
		Entity entity = event.getEntity();
		if (event.getCause() == DamageCause.FALL) {
			if (entity.getVehicle() != null) {
				PokemonEntity pokemon = getCustomEntityForEntity(entity.getVehicle());
				if (pokemon != null) {
					event.setDamage(event.getDamage() * getDamageModifier(pokemon.getEntity().getLocation()));
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity (EntityDamageByEntityEvent event) {
		//Carry
		if (event.getDamager() instanceof Player) {
			Trainer trainer = plugin.getPlayerTrainerHandler().getTrainer((Player) event.getDamager());
			PokemonEntity hitPokemon = getCustomEntityForEntity(event.getEntity());
			if (hitPokemon != null) {
				if (trainer == hitPokemon.getPokemon().getOwner()) {
					if (hitPokemon.getEntity().getVehicle() != null) {		
						hitPokemon.getEntity().getVehicle().eject();
						trainer.message("Your " + hitPokemon.getPokemon().getName() + " left your head!");
					}
					else {
						event.getDamager().addPassenger(hitPokemon.getEntity());
						trainer.message("Your " + hitPokemon.getPokemon().getName() + " is now sitting on your head!");
					}
				}
			}
			//Hunt
			else {
				for (Pokemon poke : trainer.getParty()) {
					if (poke != null) {
						PokemonEntity entity = getEntityForPokemon(poke);
						if (entity != null) {
							entity.setTask(new AttackTask(event.getEntity()));
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDismount (EntityDismountEvent event) {
		if (event.getDismounted() instanceof ArmorStand && event.getEntity() instanceof Player) {
			ArmorStand stand = (ArmorStand) event.getDismounted();
			Player player = (Player) event.getEntity();
			if (getCustomEntityForEntity(stand) != null && !player.isSneaking()) {
				if (stand.getLocation().getBlock().getType() == Material.WATER) {
					stand.addPassenger(player);
				}
			}
		}
	}
	
	private static double getDamageModifier (Location place) {
		double modifier = 1;
		float pitch = place.getPitch();
		if (pitch < 10) {
			modifier = 0;
		}
		else if (pitch < 20) {
			modifier = 0.25;
		}
		else if (pitch < 5) {
			modifier = 0.5;
		}
		else if (pitch > 80) {
			modifier = 20;
		}
		return modifier;
	}
	
//	@EventHandler
//	public void onPlayerClick (PlayerInteractEvent event) {
////		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
//			Player player = event.getPlayer();
//			walkControl(player);
////		}
//	}
	
//	private void removePokemon (List<Entity> entities) {
//		for (Entity entity : entities) {
//			if (entity.getType() == EntityType.ARMOR_STAND) {
//				for (PokemonEntity pokemon : getEntities()) {
//					if (entity.equals(pokemon.getEntity())) {
//						pokemon.delete();
//					}
//				}
//			}
//		}
//	}
	
	@EventHandler
	@Override
	public void onPluginEnabled(PluginEnableEvent event) {
		super.onPluginEnabled(event);
	}
	
	@Override
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
		super.onChunkLoad(event);
	}
	
	private void clickPokemon (PlayerInteractEntityEvent event) {
		for (PokemonEntity entity : getEntities()) {
			if (event.getRightClicked().equals(entity.getEntity())) {
				Pokemon pokemon = entity.getPokemon();
				if (plugin.getPlayerTrainerHandler().getTrainer(event.getPlayer()) == pokemon.getOwner()){
					Player player = event.getPlayer();
					if (player.isSneaking() && !plugin.getBattleHandler().isBattling(pokemon)) {
						pokemon.getOwner().message("Come back " + pokemon.getName() + "! You did enough!");
						entity.delete();
					}
					else {
						entity.getEntity().addPassenger(player);
						pokemon.getOwner().message("Let's take a walk! "+ pokemon.getName() + "!");
					}
				}
				event.setCancelled(true);
			}
		}
	}

	@Override
	public PokemonEntity getStandardInstance(Chunk chunk) {
		return new PokemonEntity(chunk.getBlock(0, 255, 0).getLocation(), new Pokemon(new PokemonSpecies(PokeminePlugin.POKEMINE_NAMESPACE, "missingno")), this, plugin);
	}

	public PokeminePlugin getPlugin() {
		return plugin;
	}
	
	public void switchPokemon (Pokemon oldPoke, Pokemon newPoke) {
		Location place = null;
		PokemonEntity entity = getEntityForPokemon(oldPoke);
		if (entity == null) {
			place = newPoke.getOwner().getEntity() != null ? newPoke.getOwner().getEntity().getLocation() : null;
		}
		else {
			place = entity.getEntity().getLocation();
			entity.delete();
		}
		if (place != null) {
			addEntity(new PokemonEntity(place, newPoke, this, getPlugin()));
		}
	}
	
//	private void walkControl (Player player) {
//		if (player.getVehicle() instanceof ArmorStand) {
//			ArmorStand stand = (ArmorStand) player.getVehicle();
//			PokemonEntity entity = plugin.getPokemonEntityHandler().getPokemonForStand(stand);
//			if (entity != null) {
//				Location place = stand.getLocation();
//				place.setDirection(player.getLocation().getDirection());
//				stand.teleport(place);
//				stand.setVelocity(place.getDirection().normalize());
//			}
//		}
//	}
	
	
}
