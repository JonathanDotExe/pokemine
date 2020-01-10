package at.jojokobi.pokemine.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.mcutil.gui.InventoryGUI;
import at.jojokobi.mcutil.gui.InventoryGUIHandler;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;

public class SubmitGUI extends InventoryGUI{
	
	private Pokemon ownPokemon;
	private Pokemon otherPokemon;
	
	private ItemStack submit;
	private PokemonHandler handler;
	private InventoryGUIHandler guiHandler;
	
	public SubmitGUI(Player owner, Pokemon ownPokemon, Pokemon otherPokemon, PokemonHandler handler, InventoryGUIHandler guiHandler) {
		super(owner, Bukkit.createInventory(owner, 9, "Do you want to trade?"));
		this.ownPokemon = ownPokemon;
		this.otherPokemon = otherPokemon;
		this.handler = handler;
		this.guiHandler = guiHandler;
		initGUI();
	}

	@Override
	protected void initGUI() {
		submit = new ItemStack(Material.EMERALD);
		ItemMeta meta = submit.getItemMeta();
		meta.setDisplayName("Submit trade: " + (ownPokemon == null ? "nothing" : ownPokemon) + " for " + (ownPokemon == null ? "nothing" : otherPokemon));
		meta.setLore(Arrays.asList("WARNING", "You won't be able to get your selected pokemon back, ", "except for trading it back again!"));
		submit.setItemMeta(meta);
		addButton(submit, 4);
		fillEmpty(getFiller());
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		if (button != null && button.equals(this.submit)) {
			ownPokemon.getOwner().message("You sent " +  ownPokemon.getName() + " to " + otherPokemon.getOwner().getName() + "!");
			ownPokemon.getOwner().message("You recieved " +  otherPokemon.getName() + "!");
			otherPokemon.getOwner().message("You sent " +  otherPokemon.getName() + " to " + ownPokemon.getOwner().getName() + "!");
			otherPokemon.getOwner().message("You recieved " +  ownPokemon.getName() + "!");
			//Trade
			ownPokemon.getOwner().trade(ownPokemon, otherPokemon, handler, guiHandler);
			close();
		}
	}

}
