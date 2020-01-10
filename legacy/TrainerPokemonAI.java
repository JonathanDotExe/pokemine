package at.jojokobi.pokemine.pokemon.entity;

import java.util.List;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;

import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.Ownable;
import at.jojokobi.mcutil.entity.ai.EntityAI;
import at.jojokobi.mcutil.entity.ai.LegacyAttackTask;
import at.jojokobi.mcutil.entity.ai.LegacyFollowTask;
import at.jojokobi.mcutil.entity.ai.LegacyRidingTask;
import at.jojokobi.pokemine.PokeminePlugin;

public class TrainerPokemonAI implements EntityAI{
	
	public static final TrainerPokemonAI INSTANCE = new TrainerPokemonAI();

	@Override
	public String getIdentifier() {
		return "trainer_pokemon";
	}

	@Override
	public String getNamespace() {
		return PokeminePlugin.POKEMINE_NAMESPACE;
	}

	@Override
	public void changeAI(CustomEntity<?> entity) {
		if (entity.getTask() == null) {
			//Ride
			if (!entity.getEntity().getPassengers().isEmpty()) {
				entity.setTask(new LegacyRidingTask());
			}
			//Follow owner
			else if (entity instanceof Ownable && ((Ownable) entity).getOwner() != null) {
				entity.setTask(new LegacyFollowTask(((Ownable) entity).getOwner()));
			}
		}
		
		if (!(entity.getTask() instanceof LegacyAttackTask)) {
			//Find enemy
			List<Entity> nearby = entity.getEntity().getNearbyEntities(4, 4, 4);
			Monster attack = null;
			for (Entity e : nearby) {
				if (e instanceof Monster && !(e instanceof Creeper) && !(e instanceof PigZombie)) {
					attack = (Monster) e;
				}
			}
			//Hunt monster
			if (attack != null) {
				entity.setTask(new LegacyAttackTask(attack));
			}
		}
	}


}
