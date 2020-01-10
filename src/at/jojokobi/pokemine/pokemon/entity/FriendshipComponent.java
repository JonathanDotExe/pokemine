package at.jojokobi.pokemine.pokemon.entity;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.EntityComponent;

public class FriendshipComponent implements EntityComponent{
	
	private boolean wantsToPlay = false;
	private int playCountdown = 4 * 10 + (int) Math.round(Math.random() * 4 * 60);

	@Override
	public void loop(CustomEntity<?> entity) {
		if (playCountdown <= 0) {
			if (wantsToPlay) {
				playCountdown = 4 * 10 + (int) Math.round(Math.random() * 4 * 60);
			}
			else {
				playCountdown = 4 * 15;
			}
			wantsToPlay = !wantsToPlay;
		}
		playCountdown--;
	}

	public boolean wantsToPlay() {
		return wantsToPlay;
	}

	public void makeWantPlay () {
		this.wantsToPlay = true;
		playCountdown = 4 * 15;
	}

	@Override
	public Map<String, Object> serialize(CustomEntity<?> entity) {
		return new HashMap<>();
	}

	@Override
	public void deserialize(Map<String, Object> map, CustomEntity<?> entity) {
		
	}

}
