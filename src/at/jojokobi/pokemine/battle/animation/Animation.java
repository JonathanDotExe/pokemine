package at.jojokobi.pokemine.battle.animation;

public abstract class Animation implements IAnimation {
	
	private int duration;
	private int time = 0;
	

	public Animation(int duration) {
		super();
		this.duration = duration;
	}

	@Override
	public void tick() {
		if (time < duration) {
			time++;
		}
	}
	
	@Override
	public boolean isFinished() {
		return time >= duration;
	}
	
	@Override
	public boolean justStarted() {
		return time <= 1;
	}

	public int getDuration() {
		return duration;
	}

	public int getTime() {
		return time;
	}

}
