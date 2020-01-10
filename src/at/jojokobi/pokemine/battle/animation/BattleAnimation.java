package at.jojokobi.pokemine.battle.animation;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import at.jojokobi.mcutil.entity.NMSEntityUtil;

public abstract class BattleAnimation extends Animation{
	
	private Entity performer;
	private Entity defender;
	private Sound sound;
	
	
	public BattleAnimation(Entity performer, Entity defender, int duration, Sound sound) {
		super(duration);
		this.performer = performer;
		this.defender = defender;
		this.sound = sound;
	}
	
	public void tick() {
		super.tick();
		if (justStarted()) {
			//Play Sound
			for (Entity entity : getPerformer().getNearbyEntities(20, 20, 20)) {
				if (entity instanceof Player) {
					((Player) entity).playSound(getPerformer().getLocation(), sound, 1.0f, 1.0f);
				}
			}
			//Rotate
			NMSEntityUtil.rotateVehicle(getPerformer(), getDefender().getLocation().toVector().subtract(getPerformer().getLocation().toVector()));
			NMSEntityUtil.rotateVehicle(getDefender(), getPerformer().getLocation().toVector().subtract(getDefender().getLocation().toVector()));
		}
	}
	
	public static BattleAnimation stringToAnimation (String string, Entity performer, Entity defender) {
		BattleAnimation animation = null;
		switch (string) {
		case "squirt":
			animation = new SquirtAnimation(performer, defender);
			break;
		case "hit":
			animation = new HitAnimation(performer, defender);
			break;
		case "thunder":
			animation = new ThunderAnimation(performer, defender);
			break;
		case "flame":
			animation = new FlameAnimation(performer, defender);
			break;
		case "inferno":
			animation = new InfernoAnimation(performer, defender);
			break;
		case "solar":
			animation = new SolarAnimation(performer, defender);
			break;
		case "leaf":
			animation = new LeafAnimation(performer, defender);
			break;
		case "break":
			animation = new BreakAnimation(performer, defender);
			break;
		case "psybeam":
			animation = new PsyBeamAnimation(performer, defender);
			break;
		case "dust":
			animation = new DustRayAnimation(performer, defender);
			break;
		case "icebeam":
			animation = new IceBeamAnimation(performer, defender);
			break;
		case "snowball":
			animation = new SnowballAnimation(performer, defender);
			break;
		case "music":
			animation = new MusicAnimation(performer, defender);
			break;
		case "positive_status":
			animation = new PositiveStatusAnimation(performer, defender);
			break;
		default:
			animation = new BattleAnimation(performer, defender, 0, null) { };
			break;
		}
		return animation;
	}

	public Sound getSound() {
		return sound;
	}

	public Entity getPerformer() {
		return performer;
	}

	public void setPerformer(Entity performer) {
		this.performer = performer;
	}

	public Entity getDefender() {
		return defender;
	}

	public void setDefender(Entity defender) {
		this.defender = defender;
	}
	
}
