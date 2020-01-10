package at.jojokobi.pokemine.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.gui.InventoryGUI;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.MoveUtil;
import at.jojokobi.pokemine.pokemon.Pokemon;

public abstract class MoveGUI extends InventoryGUI{

	private Pokemon pokemon;
	
	public MoveGUI(Player owner, Pokemon pokemon, Inventory inventory) {
		super(owner, inventory);
		this.pokemon = pokemon;
	}

	@Override
	protected void initGUI() {
		addButton(pokemon.toItemStack(), 5);
		//Moves
		for (int i = 0; i < pokemon.getMoves().length; i++) {
			if (pokemon.getMoves()[i] != null) {
				addButton(MoveUtil.itemFromMove(pokemon.getMoves()[i]), 2* INV_ROW + 1 + i*2);
			}
		}
		fillEmpty(getFiller());
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		boolean found = false;
		for (int i = 0; i < pokemon.getMoves().length && !found; i++) {
			if (pokemon.getMoves()[i] != null && pokemon.getMoves()[i].getMove().getName().equals(button.getItemMeta().getDisplayName())) {
				onMoveClick(pokemon.getMoves()[i], click);
				found = true;
			}
		}
	}
	
	protected abstract void onMoveClick (MoveInstance move, ClickType click);
	
	public Pokemon getPokemon () {
		return pokemon;
	}

}
