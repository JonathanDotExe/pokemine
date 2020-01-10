package at.jojokobi.pokemine.pokemon.status;

import java.util.Map;

import at.jojokobi.pokemine.battle.PokemonContainer;

public class HeavyPoison extends Poison {

	public static final String NAME = "heavy_poison";
	
	public HeavyPoison() {
		super();
	}
	
	@Override
	public boolean endRound(PokemonContainer victim) {
		victim.getPokemon().setHealth(Math.round(victim.getPokemon().getHealth() - victim.getPokemon().getMaxHealth()*(Math.min(getRound(), 15)*0.0625f)));
		return super.endRound(victim);
	}
	
	@Override
	public String getScriptName() {
		return NAME;
	}
	
	public static HeavyPoison deserialize (Map<String, Object> map) {
		HeavyPoison obj = new HeavyPoison();
		obj.load(map);
		return obj;
	}

}
