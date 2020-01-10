package at.jojokobi.pokemine.pokemon.entity;

import at.jojokobi.mcutil.entity.CustomEntity;

public interface FriendshipChecker {
	
	public boolean likes (CustomEntity<?> entity, CustomEntity<?> other);
	
	public boolean dislikes (CustomEntity<?> entity, CustomEntity<?> other);

}
