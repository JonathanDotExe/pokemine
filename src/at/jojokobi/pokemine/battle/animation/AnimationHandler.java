package at.jojokobi.pokemine.battle.animation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class AnimationHandler {
	
	private List<IAnimation> animations = new LinkedList<>();

	public AnimationHandler(Plugin plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				List<IAnimation> remove = new ArrayList<>();
				//Update animations
				for (IAnimation animation : animations) {
					animation.tick();
					if (animation.isFinished()) {
						remove.add(animation);
					}
				}
				//Remove finished animations
				animations.removeAll(remove);
			}
		}, 1L, 1L);
	}
	
	public void addAnimation (IAnimation animation) {
		animations.add(animation);
	}

}
