package at.jojokobi.pokemine.battle.animation;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

import at.jojokobi.mcutil.VectorUtil;
import org.bukkit.entity.Entity;

public class SquirtAnimation extends BattleAnimation {

	public SquirtAnimation(Entity performer, Entity defender) {
		super(performer, defender, 40, Sound.WEATHER_RAIN);
	}
	
	@Override
	public void tick() {
		for (int i = 0; i < getTime(); i++) {
			Location place = VectorUtil.interpolate(getPerformer().getLocation().add(0, 1, 0), getDefender().getLocation().add(0, 1, 0), (double) i/getDuration());
			place.getWorld().spawnParticle(Particle.WATER_BUBBLE, place, 4);
			place.getWorld().spawnParticle(Particle.WATER_SPLASH, place, 1);
		}
		super.tick();
	}

}
