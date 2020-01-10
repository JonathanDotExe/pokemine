package at.jojokobi.pokemine.moves.movescript;

import at.jojokobi.pokemine.moves.Move;

public class MoveInitializer {

	private float accuracy = 1;
	private float priority =  0;
	private boolean fail = false;
	private String animation;
	
	public MoveInitializer(Move move) {
		this.accuracy = move.getAccuracy();
		this.priority = move.getPriority();
		this.animation = move.getAnimation();
	}

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public boolean isFail() {
		return fail;
	}

	public void setFail(boolean fail) {
		this.fail = fail;
	}

	public float getPriority() {
		return priority;
	}

	public void setPriority(float priority) {
		this.priority = priority;
	}

	public String getAnimation() {
		return animation;
	}

	public void setAnimation(String animation) {
		this.animation = animation;
	}

}
