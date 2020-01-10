package at.jojokobi.pokemine.trainer;

import java.util.Map;
import java.util.Random;

import org.bukkit.entity.Entity;

import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;

public class NPCTrainer extends Trainer {
	
	private NPCTrainer() {
		
	}

	public NPCTrainer(TrainerRank rank, byte level, PokemonHandler handler, int count, Random random) {
		setRank(rank);
		setName(rank.getNames()[random.nextInt(rank.getNames().length)]);
		for (Pokemon pokemon : TrainerUtil.generateParty(level, this, handler, count, random)) {
			if (pokemon != null) {
				givePokemon(pokemon);
			}
		}
	}
	
	public NPCTrainer(TrainerRank rank, byte level, PokemonHandler handler, Random random) {
		this(rank, level, handler, random.nextInt(Trainer.PARTY_SIZE - 1) + 1, random);
	}
	
	public NPCTrainer(TrainerRank rank, byte level, PokemonHandler handler) {
		this(rank, level, handler, new Random());
	}

	@Override
	public void message(String message) {
		
	}

	@Override
	public Entity getEntity() {
		return null;
	}

	@Override
	public boolean hasExpShare() {
		return false;
	}
	
	public static NPCTrainer deserialize (Map<String, Object> map) {
		NPCTrainer trainer = new NPCTrainer();
		trainer.load(map);
		return trainer;
	}
	
}
