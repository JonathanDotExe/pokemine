package at.jojokobi.pokemine.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.gui.InventoryGUI;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.trainer.Trainer;

public abstract class PartyGUI extends InventoryGUI {

	public static final int OFFSET = 2;

	private Trainer trainer;

	public PartyGUI(Player owner, Trainer trainer) {
		super(owner, Bukkit.createInventory(owner, 9, trainer.getName() + "'s Party"));
		this.trainer = trainer;
	}

	@Override
	protected void initGUI() {
		getInventory().clear();
		int i = 0;
		for (Pokemon poke : trainer.getParty()) {
				ItemStack pokemon = poke.toItemStack();
				addButton(pokemon, i + OFFSET);
				i++;
		}
		fillEmpty(getFiller());
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		int index = getInventory().first(button);
		if (index >= OFFSET && index <= OFFSET + getTrainer().getParty().size()) {
			Pokemon pokemon = getTrainer().getParty().get(index - OFFSET);
			onPokemonClick(pokemon, click);
		}
	}

	protected abstract void onPokemonClick(Pokemon pokemon, ClickType click);

	public Trainer getTrainer() {
		return trainer;
	}

}
