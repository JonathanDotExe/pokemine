package at.jojokobi.pokemine.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.mcutil.gui.InventoryGUI;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class LikesPokemonGUI extends InventoryGUI{
	
	private Pokemon pokemon;

	public LikesPokemonGUI(Player owner, Pokemon pokemon) {
		super(owner, Bukkit.createInventory(owner, INV_ROW, pokemon.getName() + "'s friends"));
		this.pokemon = pokemon;
		initGUI();
	}

	@Override
	protected void initGUI() {
		int i = 2;
		System.out.println();
		for (Pokemon poke : pokemon
				.getOwner()
				.getParty()) {
			ItemStack item = poke.toItemStack();
			ItemMeta meta = item.getItemMeta();
			ChatColor color = ChatColor.RESET;
			if (pokemon.likes(poke)) {
				System.out.println(pokemon.getName() + " likes " + poke.getName());
				color = ChatColor.GREEN;
			}
			else if (pokemon.dislikes(poke)) {
				System.out.println(pokemon.getName() + " dislikes " + poke.getName());
				color = ChatColor.RED;
			}
			else {
				System.out.println(pokemon.getName() + " doesn't mind " + poke.getName());
			}
			meta.setDisplayName(color + meta.getDisplayName());
			item.setItemMeta(meta);
			addButton(item, i);
			i++;
		}
		fillEmpty(getFiller());
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		
	}

}
