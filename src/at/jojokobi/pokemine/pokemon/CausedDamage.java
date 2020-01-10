package at.jojokobi.pokemine.pokemon;

import at.jojokobi.pokemine.moves.DamageClass;

public class CausedDamage {

	private int damage;
	private DamageClass damageClass;
	
	public CausedDamage(int amount, DamageClass damageClass) {
		super();
		this.damage = amount;
		this.damageClass = damageClass;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public DamageClass getDamageClass() {
		return damageClass;
	}

	@Override
	public String toString() {
		return "CausedDamage [amount=" + damage + ", damageClass=" + damageClass + "]";
	}

}
