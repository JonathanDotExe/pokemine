package at.jojokobi.pokemine.battle.animation;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

import at.jojokobi.mcutil.VectorUtil;
import org.bukkit.entity.Entity;

public class SnowballAnimation extends BattleAnimation {

	public SnowballAnimation(Entity performer, Entity defender) {
		super(performer, defender, 40, Sound.ENTITY_SNOWBALL_THROW);
	}
	
	@Override
	public void tick() {
		Location place = VectorUtil.interpolate(getPerformer().getLocation().add(0, 1, 0), getDefender().getLocation().add(0, 1, 0), (double) getTime()/getDuration());
		place.getWorld().spawnParticle(Particle.SNOWBALL, place, 2);
		super.tick();
	}

}
