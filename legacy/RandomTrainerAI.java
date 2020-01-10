package at.jojokobi.pokemine.trainer.entity;

import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.ai.EntityAI;
import at.jojokobi.mcutil.entity.ai.LegacyRandomTask;
import at.jojokobi.pokemine.PokeminePlugin;

public class RandomTrainerAI implements EntityAI {
	
	public static final RandomTrainerAI INSTANCE = new RandomTrainerAI();

	@Override
	public String getIdentifier() {
		return "random_trainer";
	}

	@Override
	public String getNamespace() {
		return PokeminePlugin.POKEMINE_NAMESPACE;
	}

	@Override
	public void changeAI(CustomEntity<?> entity) {
		if (entity.getTask() == null) {
			entity.setTask(new LegacyRandomTask());
		}
	}

}
