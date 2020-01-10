package at.jojokobi.pokemine.items;

import org.bukkit.inventory.ItemStack;

public interface Buyable {
	
	public int getBuyPrice ();
	
	public default int getSellPrice () {
		return getBuyPrice() / 2;
	}
	
	public byte getMinBuyLevel ();
	
	public ItemStack getBoughtItem ();
	
	public default ItemStack getShopIcon () {
		return getBoughtItem();
	}

}
