package at.jojokobi.pokemine.battle.animation;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

import at.jojokobi.mcutil.VectorUtil;
import org.bukkit.entity.Entity;

public class ThunderAnimation extends BattleAnimation {

	public ThunderAnimation(Entity performer, Entity defender) {
		super(performer, defender, 30, Sound.ENTITY_LIGHTNING_BOLT_THUNDER);
	}
	
	@Override
	public void tick() {
		if (getTime() < getDuration()) {
			Location place = VectorUtil.interpolate(getDefender().getLocation().add(0, 5, 0), getDefender().getLocation(), (double) getTime()/getDuration());
			place.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, place, 10);
		}
		super.tick();
	}

}
