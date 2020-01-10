package at.jojokobi.pokemine.pokemon.entity;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.ai.EntityAI;
import at.jojokobi.mcutil.entity.ai.LegacyAttackTask;
import at.jojokobi.mcutil.entity.ai.LegacyGoalTask;
import at.jojokobi.pokemine.PokeminePlugin;

public class AggressiveStationaryAI implements EntityAI{
	
	public static final AggressiveStationaryAI INSTANCE = new AggressiveStationaryAI();

	public AggressiveStationaryAI() {
		
	}

	@Override
	public String getIdentifier() {
		return "stationary_aggressive";
	}

	@Override
	public String getNamespace() {
		return PokeminePlugin.POKEMINE_NAMESPACE;
	}

	@Override
	public void changeAI(CustomEntity<?> entity) {
		if (entity.getTask() == null) {
			entity.setTask(new LegacyGoalTask(entity.getSpawnPoint()));
		}
		else if (!(entity.getTask() instanceof LegacyAttackTask)) {
			//Get enemies
			List<Entity> nearby = entity.getEntity().getNearbyEntities(10, 10, 10);
			Player attack = null;
			for (Entity e : nearby) {
				if (e instanceof Player && (((Player) e).getGameMode() == GameMode.SURVIVAL || ((Player) e).getGameMode() == GameMode.ADVENTURE)) {
					attack = (Player) e;
				}
			}
			//Attack player
			if (attack != null) {
				entity.setTask(new LegacyAttackTask(attack));
			}
		}
	}
	
}
