package at.jojokobi.pokemine.moves;

public enum DamageClass {
	PHYSICAL, SPECIAL, STATUS;
	
	public static DamageClass stringToDamageClass (String string) {
		DamageClass dmgClass = STATUS;
		DamageClass[] values = values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].toString().equals(string)) {
				dmgClass = values[i];
			}
		}
		return dmgClass;
	}
	
	@Override
	public String toString () {
		String string = "status";
		switch (this) {
		case PHYSICAL:
			string = "physical";
			break;
		case SPECIAL:
			string = "special";
			break;
		case STATUS:
			string = "status";
			break;
		}
		return string;
	}
}
