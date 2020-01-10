package at.jojokobi.pokemine.battle.animation;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

import at.jojokobi.mcutil.VectorUtil;
import org.bukkit.entity.Entity;

public class DustRayAnimation extends BattleAnimation{

	public DustRayAnimation(Entity performer, Entity defender) {
		super(performer, defender, 40, Sound.ENTITY_SLIME_JUMP);
	}

	@Override
	public void tick() {
		for (int i = 0; i < getTime(); i++) {
			Location place = VectorUtil.interpolate(getPerformer().getLocation().add(0, 1, 0), getDefender().getLocation().add(0, 1, 0), (double) i/getDuration());
			place.getWorld().spawnParticle(Particle.SPIT, place, 2);
		}
		super.tick();
	}

}
