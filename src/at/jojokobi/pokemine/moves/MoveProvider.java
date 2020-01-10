package at.jojokobi.pokemine.moves;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.mcutil.ItemProvider;

public class MoveProvider implements ItemProvider<Move>{
	
	private static MoveProvider instance = null;
	
	public static MoveProvider getInstance () {
		if (instance == null) {
			instance = new MoveProvider();
		}
		return instance;
	}

	@Override
	public List<Move> provide() {
		List<Move> moves = new ArrayList<>();
		
		
		
		return moves;
	}
	
	
	
}
