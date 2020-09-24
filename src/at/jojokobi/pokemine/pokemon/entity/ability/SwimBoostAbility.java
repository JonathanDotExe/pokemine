package at.jojokobi.pokemine.pokemon.entity.ability;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.jojokobi.mcutil.entity.ai.EntityTask;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;

public class SwimBoostAbility implements PokemonEntityAbility {

	@Override
	public void entityTick(PokemonEntity entity) {
		if (entity.getTime() % (4 * 5) == 0 && !entity.getPokemon().isWild()) {
			double radius = entity.getPokemon().getAffection() * 4 + 10;
			for (Entity e : entity.getEntity().getNearbyEntities(radius, radius, radius)) {
				if (e instanceof Player) {
					((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20 * 10, 1));
				}
			}
		}
	}

	@Override
	public List<EntityTask> createTasks(PokemonEntity entity) {
		return new ArrayList<EntityTask>();
	}

}
