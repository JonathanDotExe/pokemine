package at.jojokobi.pokemine.battle.animation;

import org.bukkit.Sound;
import org.bukkit.util.Vector;

import org.bukkit.entity.Entity;

public class HitAnimation extends BattleAnimation{

	public HitAnimation(Entity performer, Entity defender) {
		super(performer, defender, 20, Sound.ENTITY_GENERIC_HURT);
	}
	
	@Override
	public void tick() {
		if (getTime() == 0) {
			Vector velocity = getPerformer().getLocation().getDirection();
			velocity.normalize();
			velocity.setY(0.5);
			getPerformer().setVelocity(velocity);
		}
		else if (getTime() == getDuration()/2) {
			Vector velocity = getDefender().getLocation().getDirection();
			velocity.normalize();
			velocity.multiply(-1.5);
			velocity.setY(0.5);
			getDefender().setVelocity(velocity);
		}
		else if (getTime() == getDuration() - 1)  {
			Vector velocity = getPerformer().getLocation().getDirection();
			velocity.multiply(-1);
			velocity.normalize();
			getPerformer().setVelocity(velocity);
			
			Vector velocity2 = getDefender().getLocation().getDirection();
			velocity2.multiply(0.2);
			velocity2.normalize();
			getDefender().setVelocity(velocity2);
		}
		super.tick();
	}

}
