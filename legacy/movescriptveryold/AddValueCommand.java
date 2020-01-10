package at.jojokobi.pokemine.moves.movescriptveryold;


@Deprecated
public abstract class AddValueCommand extends StatusCommand{

	private int value;


	public AddValueCommand(int value, Target target, float chance) {
		super(target, chance);
		this.value = value;
	}
	
	protected int getValue() {
		return value;
	}

	protected void setValue(int value) {
		this.value = value;
	}

}
