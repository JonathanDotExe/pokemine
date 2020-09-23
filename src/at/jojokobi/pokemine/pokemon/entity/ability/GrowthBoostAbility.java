package at.jojokobi.pokemine.pokemon.entity.ability;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;

import at.jojokobi.mcutil.entity.ai.EntityTask;
import at.jojokobi.pokemine.pokemon.entity.PokemonBehaviorType;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;

public class GrowthBoostAbility implements PokemonEntityAbility {

	@Override
	public void entityTick(PokemonEntity entity) {
		if (entity.getTime() % (4 * 10) == 0 && entity.getBehaviorType() == PokemonBehaviorType.PLACED_POKEMON) {
			double chance = entity.getPokemon().getAffection()/5.0 * 0.2 + 0.1; 
			int radius = entity.getPokemon().getAffection() * 4 + 10;
			for (int x = -radius; x < radius; x++) {
				for (int y = -radius; y < radius; y++) {
					for (int z = -radius; z < radius; z++) {
						Block block = entity.getEntity().getLocation().getBlock().getRelative(x, y, z);
						if (block.getBlockData() instanceof Ageable && Math.random() < chance) {
							Ageable crop = (Ageable) block.getBlockData();
							if (crop.getAge() < crop.getMaximumAge()) {
								crop.setAge(crop.getAge() + 1);
								block.setBlockData(crop);
								for (int i = 0; i < 5; i++) {
									block.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(Math.random() - 0.5, Math.random(), Math.random() - 0.5), 1);
								}
							}
						}
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
