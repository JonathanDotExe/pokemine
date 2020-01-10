package at.jojokobi.pokemine.battle;

public abstract class BattleAction {
	
	private float priority = 0;
	private int duration = 80;
	private int timer = 0;
	private Battle battle;
	private PokemonContainer performer;
	private PokemonContainer defender;

	public BattleAction(Battle battle, PokemonContainer performer, PokemonContainer defender) {
		super();
		this.battle = battle;
		this.performer = performer;
		this.defender = defender;
	}

	public Battle getBattle() {
		return battle;
	}
	
	public void nextStep () {
		if (timer == 0 && getPerformer().getPokemon().getHealth() > 0) {
			//Send start turn
			getPerformer().startTurn();
			String[] text = startPerform();
			//Chat
			for (String s : text) {
				getBattle().sendBattleMessage(s);
			}
			//Title
			getBattle().sendBattleTitle(text.length > 0 ? text[0] : "", text.length > 1 ? text[1] : "");
		}
		tickPerform();
		if (timer >= duration - 1) {
			//Send start turn
			getPerformer().startTurn();
			stopPerform();
		}
		timer++;
	}
	
	public boolean isFinished() {
		return timer >= duration;
	}
	
	public abstract void init();

	public abstract String[] startPerform ();
	
	public abstract String[] tickPerform ();
	
	public abstract String[] stopPerform ();
	
	public float getPriority() {
		return priority;
	}

	protected void setPriority(float priority) {
		this.priority = priority;
	}

	public PokemonContainer getPerformer() {
		return performer;
	}

	public PokemonContainer getDefender() {
		return defender;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getTime () {
		return timer;
	}
	
}
