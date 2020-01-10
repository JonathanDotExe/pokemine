package at.jojokobi.pokemine.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.gui.InventoryGUI;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class PokemonItemGUI extends InventoryGUI{

	private Pokemon pokemon;

	public PokemonItemGUI(Player owner, Pokemon pokemon) {
		super(owner, owner.getInventory());
		this.pokemon = pokemon;
		initGUI();
	}

	@Override
	protected void initGUI() {
		
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		if (button.getType() != Material.AIR && pokemon.getItem() == null) {
			pokemon.setItem(button);
			getOwner().getInventory().remove(button);
			close();
		}
	}

}
