package at.jojokobi.pokemine.trainer.entity;

import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.ai.EntityAI;
import at.jojokobi.pokemine.PokeminePlugin;

public class StationaryTrainerAI implements EntityAI {
	
	public static final StationaryTrainerAI INSTANCE = new StationaryTrainerAI();

	@Override
	public String getIdentifier() {
		return "stationary_trainer";
	}

	@Override
	public String getNamespace() {
		return PokeminePlugin.POKEMINE_NAMESPACE;
	}

	@Override
	public void changeAI(CustomEntity<?> entity) {
		entity.setTask(null);
	}

}
