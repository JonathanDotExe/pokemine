package at.jojokobi.pokemine.pokemon;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class PokemonValueSet implements ConfigurationSerializable {
	
	private int health;
	private int attack;
	private int defense;
	private int specialAttack;
	private int specialDefense;
	private int speed;
	
	public PokemonValueSet(int health, int attack, int defense, int specialAttack, int specialDefense, int speed) {
		super();
		this.health = health;
		this.attack = attack;
		this.defense = defense;
		this.specialAttack = specialAttack;
		this.specialDefense = specialDefense;
		this.speed = speed;
	}
	
	public PokemonValueSet() {
		this(0, 0, 0, 0, 0, 0);
	}
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public int getSpecialAttack() {
		return specialAttack;
	}
	public void setSpecialAttack(int specialAttack) {
		this.specialAttack = specialAttack;
	}
	public int getSpecialDefense() {
		return specialDefense;
	}
	public void setSpecialDefense(int specialDefense) {
		this.specialDefense = specialDefense;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("health", health);
		map.put("attack", attack);
		map.put("defense", defense);
		map.put("specialAttack", specialAttack);
		map.put("specialDefense", specialDefense);
		map.put("speed", speed);
		return map;
	}
	
	public static PokemonValueSet deserialize (Map<String, Object> map) {
		PokemonValueSet valueSet = new PokemonValueSet();
		try {
			valueSet.health = Integer.parseInt("" + map.get("health"));
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		try {
			valueSet.attack = Integer.parseInt("" + map.get("attack"));
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		try {
			valueSet.defense = Integer.parseInt("" + map.get("defense"));
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		try {
			valueSet.specialAttack = Integer.parseInt("" + map.get("specialAttack"));
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		try {
			valueSet.specialDefense = Integer.parseInt("" + map.get("specialDefense"));
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		try {
			valueSet.speed = Integer.parseInt("" + map.get("speed"));
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return valueSet;
	}

}
