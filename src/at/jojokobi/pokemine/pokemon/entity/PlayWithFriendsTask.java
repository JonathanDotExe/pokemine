package at.jojokobi.pokemine.pokemon.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;

import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.ai.EntityTask;
import at.jojokobi.mcutil.locatables.EntityLocatable;
import at.jojokobi.mcutil.locatables.Locatable;

public class PlayWithFriendsTask implements EntityTask{
	
	private FriendshipChecker checker;
	private Locatable target;

	public PlayWithFriendsTask(FriendshipChecker checker) {
		super();
		this.checker = checker;
	}

	@Override
	public boolean canApply(CustomEntity<?> entity) {
		return entity.getComponent(FriendshipComponent.class) != null && entity.getComponent(FriendshipComponent.class).wantsToPlay() && !findFriends(entity).isEmpty();
	}

	@Override
	public Locatable apply(CustomEntity<?> entity) {
		if (entity.getEntity().getLocation().distance(target.getLocation()) <= 5) {
			entity.jump();
			if (Math.random() < 0.1) {
				entity.getEntity().getWorld().spawnParticle(Particle.HEART, entity.getEntity().getLocation().add(Math.random() - 0.5, entity.getEntity().getHeight() + Math.random() - 0.5, Math.random() - 0.5), 2);
			}
		}
		return target;
	}

	@Override
	public void activate(CustomEntity<?> entity) {
//		System.out.println("Activate: " + entity.getEntity().getCustomName());
		List<CustomEntity<?>> friends = findFriends(entity);
		for (CustomEntity<?> friend : friends) {
			if (!friend.getComponent(FriendshipComponent.class).wantsToPlay()) {
				friend.getComponent(FriendshipComponent.class).makeWantPlay();
			}
		}
		target = new EntityLocatable(friends.get(new Random().nextInt(friends.size())).getEntity());
	}

	@Override
	public void deactivate(CustomEntity<?> entity) {
		
	}
	
	private List<CustomEntity<?>> findFriends (CustomEntity<?> entity) {
		List<CustomEntity<?>> entities = new ArrayList<>();
		for (Entity e : entity.getEntity().getNearbyEntities(15, 15, 15)) {
			CustomEntity<?> other = entity.getHandler().getCustomEntityForEntity(e);
			if (other != null && entity != other && other.getComponent(FriendshipComponent.class) != null && checker.likes(entity, other)) {
				entities.add(other);
			}
		}
		return entities;
	}

}
