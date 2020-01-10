package at.jojokobi.pokemine.battle.effects;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.PokemonContainer;

public abstract class BattleEffect {

	private Battle battle;
	private boolean hasDuration = false;
	private int duration = 0;
	private int round = 0;
	private String endMessage = "";
	
	public BattleEffect(Battle battle) {
		this.battle = battle;
	}
	
	public void startRound () {
		round++;
		if (hasDuration() && getRound() > duration) {
			battle.removeBattleEffect(this);
			battle.sendBattleMessage(getEndMessage());
		}
	}
	
	public abstract void startTurn (PokemonContainer pokemon);
	
	public abstract void endTurn (PokemonContainer pokemon);
	
	public abstract void endRound ();
	
	public abstract String getScriptName();
	
	public abstract String getAddMessage ();

	public Battle getBattle() {
		return battle;
	}

	public boolean hasDuration() {
		return hasDuration;
	}

	protected void setHasDuration(boolean hasDuration) {
		this.hasDuration = hasDuration;
	}

	public int getDuration() {
		return duration;
	}

	protected void setDuration(int duration) {
		this.duration = duration;
	}

	public int getRound() {
		return round;
	}

	public String getEndMessage() {
		return endMessage;
	}

	protected void setEndMessage(String endMessage) {
		this.endMessage = endMessage;
	}

}
