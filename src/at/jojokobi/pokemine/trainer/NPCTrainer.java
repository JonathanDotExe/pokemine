package at.jojokobi.pokemine.trainer;

import java.util.Map;

import org.bukkit.entity.Entity;

import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class NPCTrainer extends Trainer {
	
	private TeamGenerator generator;
	
	private NPCTrainer() {
		
	}

	public NPCTrainer(TrainerRank rank, TeamGenerator generator) {
		setRank(rank);
		this.generator = generator;
	}
	
	@Override
	public void prepareForBattle(Battle battle) {
		super.prepareForBattle(battle);
		createTeam();
		for (Pokemon pokemon : getParty()) {
			pokemon.heal();
		}
	}
	
	public void createTeam () {
		if (generator != null) {
			for (Pokemon pokemon : generator.create(getRank())) {
				givePokemon(pokemon);
			}
			generator = null;
		}
	}
	
	@Override
	public void endBattle(Battle battle, boolean won) {
		super.endBattle(battle, won);
		for (Pokemon pokemon : getParty()) {
			pokemon.heal();
		}
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
		TypedMap m = new TypedMap(map);
		trainer.generator = m.get("generator", TeamGenerator.class, null);
		return trainer;
	}
	
}
