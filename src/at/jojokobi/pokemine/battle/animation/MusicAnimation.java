package at.jojokobi.pokemine.battle.animation;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

import at.jojokobi.mcutil.VectorUtil;
import org.bukkit.entity.Entity;

public class MusicAnimation extends BattleAnimation {

	public MusicAnimation(Entity performer, Entity defender) {
		super(performer, defender, 40, Sound.AMBIENT_CAVE);
	}
	@Override
	public void tick() {
		for (int i = 0; i < getTime(); i++) {
			Location place = VectorUtil.interpolate(getPerformer().getLocation().add(0, 1, 0), getDefender().getLocation().add(0, 1, 0), (double) i/getDuration());
			place.add(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5);
			place.getWorld().spawnParticle(Particle.NOTE, place, 1);
		}
		super.tick();
	}
}