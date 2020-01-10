package at.jojokobi.pokemine.trainer.entity;

import org.bukkit.Location;

import at.jojokobi.mcutil.entity.CustomEntityType;
import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.trainer.NPCTrainer;
import at.jojokobi.pokemine.trainer.TrainerRank;

public final class TrainerEntityType implements CustomEntityType<NPCTrainerEntity>{
	
	public static final String KEY = "trainer";
	
	private static TrainerEntityType instance;
	
	public static TrainerEntityType getInstance () {
		if (instance == null) {
			instance = new TrainerEntityType();
		}
		return instance;
	}
	
	private TrainerEntityType() {
		
	}
	
	@Override
	public String getIdentifier() {
		return KEY;
	}

	@Override
	public String getNamespace() {
		return PokeminePlugin.POKEMINE_NAMESPACE;
	}

	@Override
	public NPCTrainerEntity createInstance(Location loc, EntityHandler handler) {
		return new NPCTrainerEntity(new NPCTrainer(new TrainerRank(PokeminePlugin.POKEMINE_NAMESPACE, "trainer"),
				(byte) 50,
				PokemonHandler.getInstance()),
				loc,
				handler);
	}

}
