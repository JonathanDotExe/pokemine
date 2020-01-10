package at.jojokobi.pokemine.battle.animation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

import at.jojokobi.mcutil.VectorUtil;
import org.bukkit.entity.Entity;

public class BreakAnimation extends BattleAnimation{

	public BreakAnimation(Entity performer, Entity defender) {
		super(performer, defender, 10, Sound.BLOCK_METAL_BREAK);
	}
	
	@Override
	public void tick() {
		super.tick();
		if (!isFinished()) {
			Location place = VectorUtil.interpolate(getDefender().getLocation().add(0, 5, 0), getDefender().getLocation(), (double) getTime()/getDuration());
			place.getWorld().spawnParticle(Particle.BLOCK_CRACK, place, 10, Material.BRICKS.createBlockData());
		}
	}

}
