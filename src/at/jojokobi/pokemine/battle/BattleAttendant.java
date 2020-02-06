package at.jojokobi.pokemine.battle;

import java.util.List;

import at.jojokobi.pokemine.pokemon.Pokemon;

public interface BattleAttendant {
	
	public void prepareForBattle (Battle battle);
	
	public List<Pokemon> getParty ();
	
	public void endBattle (Battle battle, BattleAttendant defeated);
	
	public BattleTask requestDeathSwitch ();
	
	public String getName ();
	
	public String getWinMessage ();

	public String getLooseMessage ();
	
}
