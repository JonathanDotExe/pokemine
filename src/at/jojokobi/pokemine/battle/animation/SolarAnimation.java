package at.jojokobi.pokemine.battle.animation;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

import at.jojokobi.mcutil.VectorUtil;
import org.bukkit.entity.Entity;

public class SolarAnimation extends BattleAnimation {

	public SolarAnimation(Entity performer, Entity defender) {
		super(performer, defender, 40, Sound.BLOCK_WOOD_PLACE);
	}
	
	@Override
	public void tick() {
		for (int i = 0; i < getTime(); i++) {
			Location place = VectorUtil.interpolate(getPerformer().getLocation().add(0, 1, 0), getDefender().getLocation().add(0, 1, 0), (double) i/getDuration());
			place.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, place, 1);
			place.getWorld().spawnParticle(Particle.CRIT_MAGIC, place, 1);
		}
		super.tick();
	}

}
