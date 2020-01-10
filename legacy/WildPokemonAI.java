package at.jojokobi.pokemine.pokemon.entity;

import java.util.List;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;

import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.ai.EntityAI;
import at.jojokobi.mcutil.entity.ai.LegacyAttackTask;
import at.jojokobi.mcutil.entity.ai.LegacyRandomTask;
import at.jojokobi.pokemine.PokeminePlugin;

public class WildPokemonAI implements EntityAI{
	
	public static final WildPokemonAI INSTANCE = new WildPokemonAI();

	public WildPokemonAI() {
		
	}

	@Override
	public String getIdentifier() {
		return "wild_pokemon";
	}

	@Override
	public String getNamespace() {
		return PokeminePlugin.POKEMINE_NAMESPACE;
	}

	@Override
	public void changeAI(CustomEntity<?> entity) {
		if (entity.getTask() == null) {
			//Hunt monster
			List<Entity> nearby = entity.getEntity().getNearbyEntities(20, 20, 20);
			Monster attack = null;
			for (Entity e : nearby) {
				if (e instanceof Monster && !(e instanceof Creeper) && !(e instanceof PigZombie)
						&& ((Monster) e).getTarget() != null) {
					attack = (Monster) e;
				}
			}
			if (attack != null) {
				entity.setTask(new LegacyAttackTask(attack));
			}
			//Random
			else {
				entity.setTask(new LegacyRandomTask());
			}
		}
	}

}
