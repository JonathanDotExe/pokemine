package at.jojokobi.pokemine.trainer.entity;

import java.io.File;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.mcutil.entity.NMSEntityUtil;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntityHandler;
import at.jojokobi.pokemine.trainer.NPCTrainer;
import at.jojokobi.pokemine.trainer.Trainer;
import at.jojokobi.pokemine.trainer.TrainerRank;

@Deprecated
public class NPCTrainerHandler extends EntityHandler {

	private PokeminePlugin plugin;
	public static final String TRAINER_ENTTIES_FILE = "pokemine" + File.separator + "trainers";
	
	public NPCTrainerHandler(PokeminePlugin plugin) {
		super(plugin, plugin.getGUIHandler(), TRAINER_ENTTIES_FILE);
		this.plugin = plugin;
		getHandler().addItem(new TrainerEntityType(plugin));
//		for (NPCTrainerEntity trainer : getEntities()) {
//			System.out.println(trainer.getEntity().isDead() + "/" + trainer.getEntity().getLocation());
//		}
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
		clickTrainer(event);
	}
	
	@Override
	@EventHandler
	public void onPlayerInteractEntity (PlayerInteractAtEntityEvent event) {
		super.onPlayerInteractEntity(event);
		clickTrainer(event);
	}
	
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
	
	private void clickTrainer (PlayerInteractEntityEvent event) {
		NPCTrainerEntity trainer = getCustomEntityForEntity(event.getRightClicked());
		if (trainer != null) {
			Trainer player = plugin.getPlayerTrainerHandler().getTrainer(event.getPlayer());
			if (plugin.getBattleHandler().getTrainersBattle(player) == null && player.getNextUsablePokemon() != null && plugin.getBattleHandler().getTrainersBattle(trainer.getTrainer()) == null && trainer.getTrainer().getNextUsablePokemon() != null) {
				//Look at player
				NMSEntityUtil.rotateVehicle(trainer.getEntity(), event.getPlayer().getLocation().toVector().subtract(trainer.getEntity().getLocation().toVector()));
				//Start battle
				Battle battle = new Battle(plugin.getBattleHandler(), player.getNextUsablePokemon(), trainer.getTrainer().getNextUsablePokemon());
				//Spawn Pokemon
				PokemonEntityHandler entityHandler = plugin.getPokemonEntityHandler();
				entityHandler.addEntity(new PokemonEntity(event.getPlayer().getLocation(), player.getNextUsablePokemon(), entityHandler, plugin));
				PokemonEntity pokeEntity = new PokemonEntity(trainer.getEntity().getLocation().add(trainer.getEntity().getLocation().getDirection()), trainer.getTrainer().getNextUsablePokemon(), entityHandler, plugin);
				entityHandler.addEntity(pokeEntity);
//				trainer.getEntity().setVelocity(new Vector(0, 0, 1));
				battle.start();
			}
			event.setCancelled(true);
		}
	}

//	@Override
	public NPCTrainerEntity getStandardInstance(Chunk chunk) {
		return new NPCTrainerEntity(new NPCTrainer(new TrainerRank(PokeminePlugin.POKEMINE_NAMESPACE, "trainer"),
				(byte) 50,
				plugin.getPokemonHandler()),
				chunk.getBlock(0, 255, 0).getLocation(),
				this, plugin);
	}

	public PokeminePlugin getPlugin() {
		return plugin;
	}

}
