package at.jojokobi.pokemine.moves.movescriptveryold;
@Deprecated
public enum Target {
	PERFORMER, DEFENDER;
	
	public static Target fromString (String string) {
		return string.equals("performer") ? PERFORMER : DEFENDER;
	}
}
