package at.jojokobi.pokemine.pokemon.entity.ability;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;

import at.jojokobi.mcutil.entity.ai.EntityTask;
import at.jojokobi.pokemine.pokemon.PlacedPokemon;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.entity.PokemonBehaviorType;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;

public class HealPokemonAbility implements PokemonEntityAbility {

	@Override
	public void entityTick(PokemonEntity entity) {
		if (entity.getTime() % (4 * 10) == 0 && entity.getBehaviorType() == PokemonBehaviorType.PLACED_POKEMON) {
			double radius = entity.getPokemon().getAffection() + 5;
			//Heal
			for (Entity e : entity.getEntity().getNearbyEntities(radius, radius, radius)) {
				PokemonEntity other = entity.getHandler().getCustomEntityForEntity(e, PokemonEntity.class);
				if (other != null && other.getPokemon().getOwner() == entity.getPokemon().getOwner()) {
					other.getPokemon().setHealth(other.getPokemon().getHealth() + other.getPokemon().getMaxHealth() * Math.round(0.1f + 0.1f * entity.getPokemon().getAffection()/5.0f));
					entity.getEntity().getWorld().spawnParticle(Particle.HEART, entity.getEntity().getLocation().add(Math.random() - 0.5, Math.random() - 0.5 + 1, Math.random() - 0.5), 1);
					e.getWorld().spawnParticle(Particle.HEART, e.getLocation().add(Math.random() - 0.5, Math.random() - 0.5 + 1, Math.random() - 0.5), 1);
				}
			}
			//Revive
			for (PlacedPokemon placed : entity.getPokemon().getOwner().getPlacedPokemonLocations()) {
				double x = entity.getEntity().getLocation().getX();
				double y = entity.getEntity().getLocation().getY();
				double z = entity.getEntity().getLocation().getZ();
				Pokemon other = entity.getPokemon().getOwner().getPokemon(placed.getPokemon());
				if (placed.getLocation().getWorld() == entity.getEntity().getWorld() && other.getHealth() <= 0 && 
						placed.getLocation().getX() > x && placed.getLocation().getX() < x + 2 * radius &&
						placed.getLocation().getY() > y && placed.getLocation().getY() < y + 2 * radius &&
						placed.getLocation().getZ() > z && placed.getLocation().getZ() < z + 2 * radius) {
					other.setHealth(Math.max(other.getMaxHealth()/10, 1));
					for (int i = 0; i < 5; i++) {
						entity.getEntity().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, entity.getEntity().getLocation().add(Math.random() - 0.5, Math.random() - 0.5 + 1, Math.random() - 0.5), 1);
						placed.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, placed.getLocation().add(Math.random() - 0.5, Math.random() - 0.5 + 1, Math.random() - 0.5), 1);
					}
					entity.getEntity().getWorld().playSound(entity.getEntity().getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);
				}
			}
		}
	}

	@Override
	public List<EntityTask> createTasks(PokemonEntity entity) {
		return new ArrayList<EntityTask>();
	}

}
