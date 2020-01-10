package at.jojokobi.pokemine.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.mcutil.gui.ListGUI;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.PokemonSpecies;
import at.jojokobi.pokemine.trainer.Trainer;

public class PokedexGUI extends ListGUI{

	public PokedexGUI(Player owner, Trainer trainer, PokemonHandler handler) {
		super(owner, Bukkit.createInventory(owner, INV_ROW * 6, "Your Pokédex:"));
		setItemsPerPage(INV_ROW * 5);
		setStartIndex(0);
		//Generate Pokedex
		List<ItemStack> items = new ArrayList<>();
		for (PokemonSpecies species : handler.getItemList()) {
			if (trainer.hasInPokedex(species)) {
				//Add Species to Pokedex
				items.add(species.toItemStack());
			}
			else {
				//Create Placeholder item
				ItemStack none = new ItemStack(Material.BARRIER);
				ItemMeta meta = none.getItemMeta();
				meta.setDisplayName("???");
				none.setItemMeta(meta);
				items.add(none);
			}
		}
		setItems(items);
		initGUI();
	}
	
	@Override
	protected void initGUI() {
		super.initGUI();
		fillEmpty(getFiller());
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		
	}

}
