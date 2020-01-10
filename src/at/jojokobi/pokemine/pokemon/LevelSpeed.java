package at.jojokobi.pokemine.pokemon;

public enum LevelSpeed {
	ERATIC, FAST, MIDDLE_FAST, MIDDLE_SLOW, SLOW, FLUCATING;
	
	public int getEpToLevelUp (int level) {
		int ep = 100;
		switch (this) {
		case ERATIC:
			if (level <= 50) {
				ep = Math.round((level*level*level * (100 - level)/50.0f));
			}
			else if (level <= 68) {
				ep = Math.round((level*level*level * (150 - level)/100.0f));
			}
			else if (level <= 98) {
				ep = Math.round((level*level*level * ((1911-10*level)/3.0f)/50.0f));
			}
			else {
				ep = Math.round((level*level*level * (160 - level)/100.0f));
			}
			break;
		case FAST:
			ep = Math.round(4*level*level*level/5f);
			break;
		case MIDDLE_FAST:
			ep = level*level*level;
			break;
		case MIDDLE_SLOW:
			ep = Math.round((6/5f)*level*level*level-15*level*level+100*level-140);
			break;
		case SLOW:
			ep = Math.round(5*level*level*level/4f);
			break;
		case FLUCATING:
			if (level <= 15) {
				ep = Math.round(level*level*level*((((level+1)/3f)+24)/50f));
			}
			else if (level <= 36) {
				ep = Math.round(level*level*level*((level+14)/50f));
			}
			else {
				ep = Math.round(level * level * level * (((level/2f) + 32)/50f));
			}
			break;
		default:
			break;
		}
		return ep;
	}
	
	public static LevelSpeed stringToLevelSpeed (String string) {
		LevelSpeed speed = LevelSpeed.ERATIC;
		switch (string.toLowerCase()) {
		case "eratic":
			speed = LevelSpeed.ERATIC;
			break;
		case "fast":
			speed = LevelSpeed.FAST;
			break;
		case "flucating":
			speed = LevelSpeed.FLUCATING;
			break;
		case "middle_fast":
			speed = LevelSpeed.MIDDLE_FAST;
			break;
		case "middle_slow":
			speed = LevelSpeed.MIDDLE_SLOW;
			break;
		case "slow":
			speed = LevelSpeed.SLOW;
			break;
		}
		return speed;
	}
	
	@Override
	public String toString() {
		String string = "";
		switch (this) {
		case ERATIC:
			string = "eratic";
			break;
		case FAST:
			string = "fast";
			break;
		case FLUCATING:
			string = "flucating";
			break;
		case MIDDLE_FAST:
			string = "middle_fast";
			break;
		case MIDDLE_SLOW:
			string = "middle_slow";
			break;
		case SLOW:
			string = "slow";
			break;
		default:
			break;
		}
		return string;
	}
	
}
