package at.jojokobi.pokemine.pokemon.entity.ability;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;

import at.jojokobi.mcutil.entity.ai.EntityTask;
import at.jojokobi.pokemine.pokemon.entity.PokemonBehaviorType;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;

public class HealPlayerAbility implements PokemonEntityAbility {

	@Override
	public void entityTick(PokemonEntity entity) {
		if (entity.getTime() % (4 * 10) == 0 && entity.getBehaviorType() == PokemonBehaviorType.PLACED_POKEMON) {
			double radius = entity.getPokemon().getAffection() + 5;
			for (Entity e : entity.getEntity().getNearbyEntities(radius, radius, radius)) {
				if (e instanceof Player || (e instanceof Tameable && ((Tameable) e).isTamed())) {
					LivingEntity l = (LivingEntity) e;
					l.setHealth(l.getHealth() + entity.getPokemon().getAffection());
				}
			}
		}
	}

	@Override
	public List<EntityTask> createTasks(PokemonEntity entity) {
		return new ArrayList<EntityTask>();
	}

}
