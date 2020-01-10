package at.jojokobi.pokemine.items;

import at.jojokobi.mcutil.item.CustomItem;

public abstract class CarriableItem extends CustomItem{

	public CarriableItem(String namespace, String identifier) {
		super(namespace, identifier);
	}
	
	public float getPhysicalDamageModifier () {
		return 1;
	}
	
	public float getSpecialDamageModifier () {
		return 1;
	}

}
