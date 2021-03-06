package at.jojokobi.pokemine.pokemon.entity.ability;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.jojokobi.mcutil.entity.ai.EntityTask;
import at.jojokobi.pokemine.pokemon.entity.PokemonBehaviorType;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;

public class SlowMonsterAbility implements PokemonEntityAbility {

	@Override
	public void entityTick(PokemonEntity entity) {
		if (entity.getTime() % (4 * 10) == 0 && entity.getBehaviorType() == PokemonBehaviorType.PLACED_POKEMON) {
			double radius = entity.getPokemon().getAffection() * 2 + 5;
			for (Entity e : entity.getEntity().getNearbyEntities(radius, radius, radius)) {
				if (e instanceof Monster) {
					((Monster) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 1));
					for (int i = 0; i < 5; i++) {
						e.getLocation().getWorld().spawnParticle(Particle.FALLING_DUST, e.getLocation().add(Math.random() - 0.5, Math.random() - 0.5 + 1, Math.random() - 0.5), 2);
					}
				}
			}
		}
	}

	@Override
	public List<EntityTask> createTasks(PokemonEntity entity) {
		return new ArrayList<EntityTask>();
	}

}
