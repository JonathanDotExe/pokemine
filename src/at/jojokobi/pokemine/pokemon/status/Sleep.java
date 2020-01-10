package at.jojokobi.pokemine.pokemon.status;

import java.util.Map;
import java.util.Random;

import org.bukkit.Particle;

import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class Sleep extends PrimStatChange {
	
	public static final String NAME = "sleep";
//	public static final String SLEEP_DURATION_TAG = "duration";
//	
//	private int duration = 0;
	
	public Sleep() {
		super("SLP");
		setHasDuration(true);
		Random random = new Random();
		setDuration(random.nextInt(3) + 1);
		setParticle(Particle.SPIT);
	}

	@Override
	public boolean canAttack(PokemonContainer victim) {
		return false;
	}

	@Override
	public String getScriptName() {
		return NAME;
	}
	
	public static Sleep deserialize (Map<String, Object> map) {
		Sleep obj = new Sleep();
		obj.load(map);
		return obj;
	}
	
//	@Override
//	public void saveToXML(Element element, Document document) {
//		super.saveToXML(element, document);
//	}
	
//	@Override
//	public void parseXML(Element element) {
//		super.parseXML(element);
//		duration = Integer.parseInt(element.getElementsByTagName(SLEEP_DURATION_TAG).item(0).getTextContent());
//	}
	
	@Override
	public String getAddMessage(Pokemon pokemon) {
		return pokemon.getName() + " is now sleeping!";
	}

}
