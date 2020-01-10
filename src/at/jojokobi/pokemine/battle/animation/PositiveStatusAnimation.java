package at.jojokobi.pokemine.battle.animation;


import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

import org.bukkit.entity.Entity;

public class PositiveStatusAnimation extends BattleAnimation{

	public PositiveStatusAnimation(Entity performer, Entity defender) {
		super(performer, defender, 40, Sound.ENTITY_GENERIC_DRINK);
	}
	
	@Override
	public void tick() {
		super.tick();
		Location place = getPerformer().getLocation();
		place.getWorld().spawnParticle(Particle.FLAME, place, 10);
	}


}
