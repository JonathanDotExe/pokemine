package at.jojokobi.pokemine.battle.animation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

import at.jojokobi.mcutil.VectorUtil;
import org.bukkit.entity.Entity;

public class LeafAnimation extends BattleAnimation{

	public LeafAnimation(Entity performer, Entity defender) {
		super(performer, defender, 40, Sound.BLOCK_GRASS_BREAK);
	}

	@Override
	public void tick() {
		getPerformer().getWorld().spawnParticle(Particle.BLOCK_CRACK, getPerformer().getLocation().add(0, 2, 0), 1, Material.OAK_LEAVES.createBlockData());
		for (int i = 0; i < getTime(); i++) {
			Location place = VectorUtil.interpolate(getPerformer().getLocation().add(0, 1, 0), getDefender().getLocation().add(0, 1, 0), (double) i/getDuration());
			place.getWorld().spawnParticle(Particle.BLOCK_CRACK, place, 1, Material.OAK_LEAVES.createBlockData());
		}
		super.tick();
	}

}
