package at.jojokobi.pokemine.pokemon.status;


import java.util.Map;

import org.bukkit.Particle;

import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class Freeze extends PrimStatChange {

	public static final String NAME = "freeze";
	
	public Freeze() {
		super("FRZ");
		setParticle(Particle.SNOW_SHOVEL);
	}

	@Override
	public boolean canAttack(PokemonContainer pokemon) {
		return false;
	}
	
	@Override
	public boolean startTurn(PokemonContainer victim) {
		super.startTurn(victim);
		return Math.random() < 0.2;
	}
	
	@Override
	public String getScriptName() {
		return NAME;
	}
	
	public static Freeze deserialize (Map<String, Object> map) {
		Freeze obj = new Freeze();
		obj.load(map);
		return obj;
	}
	
	@Override
	public String getAddMessage(Pokemon pokemon) {
		return pokemon.getName() + " was frozen!";
	}

}
