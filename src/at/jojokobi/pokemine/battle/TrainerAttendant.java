package at.jojokobi.pokemine.battle;

import java.util.List;

import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.trainer.Trainer;

public class TrainerAttendant implements BattleAttendant {
	
	private Trainer trainer;
	
	
	public TrainerAttendant(Trainer trainer) {
		super();
		this.trainer = trainer;
	}

	@Override
	public void prepareForBattle(Battle battle) {
		this.trainer.prepareForBattle(battle);
	}

	@Override
	public List<Pokemon> getParty() {
		return trainer.getParty();
	}

	@Override
	public void endBattle(Battle battle, boolean won) {
		trainer.endBattle(battle, won);
	}

}
