package at.jojokobi.pokemine.battle.animation;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

import at.jojokobi.mcutil.VectorUtil;
import org.bukkit.entity.Entity;

public class IceBeamAnimation extends BattleAnimation {

	public IceBeamAnimation(Entity performer, Entity defender) {
		super(performer, defender, 40, Sound.BLOCK_GLASS_BREAK);
	}
	
	@Override
	public void tick() {
		for (int i = 0; i < getTime(); i++) {
			Location place = VectorUtil.interpolate(getPerformer().getLocation().add(0, 1, 0), getDefender().getLocation().add(0, 1, 0), (double) i/getDuration());
			place.getWorld().spawnParticle(Particle.END_ROD, place, 1);
		}
		super.tick();
	}

}
