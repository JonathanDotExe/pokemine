package at.jojokobi.pokemine.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.gui.ListGUI;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.trainer.Trainer;

public class PlacedPokemonGUI extends ListGUI {
	
	private Trainer trainer;

	public PlacedPokemonGUI(Player owner, Trainer trainer) {
		super(owner, Bukkit.createInventory(owner, INV_ROW * 6, "Placed Pokémon"));
		this.trainer = trainer;
		setStartIndex(0);
		setItemsPerPage(INV_ROW * 5);
		initGUI();
	}
	
	@Override
	protected void initGUI() {
		getInventory().clear();
		//Init Items
		List<ItemStack> buttons = new ArrayList<>();
		for (Pokemon pokemon : trainer.getPlacedPokemon()) {
			buttons.add(pokemon.toItemStack());
		}
		setItems(buttons);
		super.initGUI();
		fillEmpty(getFiller());
	}
	
	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		int first = getInventory().first(button);
		if (first >= getStartIndex() && first < getItemsPerPage()) {
			int index = getPage() * getItemsPerPage() + first - getStartIndex();
			if (index < trainer.getPlacedPokemon().size()) {
				trainer.moveToPc(trainer.getPlacedPokemon().get(index));
				initGUI();
			}
		}
	}

}
