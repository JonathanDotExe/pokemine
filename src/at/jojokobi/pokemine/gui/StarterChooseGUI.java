package at.jojokobi.pokemine.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.gui.InventoryGUI;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.trainer.Trainer;

public class StarterChooseGUI extends InventoryGUI{

	private static final Material WATER = Material.WATER_BUCKET;
	private static final Material FIRE = Material.LAVA_BUCKET;
	private static final Material GRASS = Material.OAK_LEAVES;
	private static final Material ELECTRIC = Material.REDSTONE;
	private static final Material NORMAL = Material.EGG;
	
	private Trainer trainer;
	private PokemonHandler handler;
	
	public StarterChooseGUI(Player player, Trainer owner, PokemonHandler handler) {
		super(player, Bukkit.createInventory(player, INV_ROW, "Choose your starter!"));
		this.trainer = owner;
		this.handler = handler;
		initGUI();
	}

	@Override
	protected void initGUI() {
		addButton(new ItemStack(GRASS), 2);
		addButton(new ItemStack(FIRE), 3);
		addButton(new ItemStack(WATER), 4);
		addButton(new ItemStack(ELECTRIC), 5);
		addButton(new ItemStack(NORMAL), 6);
		fillEmpty(getFiller());
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		if (button.getType() == GRASS) {
			trainer.givePokemon(new Pokemon(handler.getItem(PokeminePlugin.POKEMINE_NAMESPACE, "bulbasaur"), (byte) 5));
			close();
		}
		else if (button.getType() == FIRE) {
			trainer.givePokemon(new Pokemon(handler.getItem(PokeminePlugin.POKEMINE_NAMESPACE, "charmander"), (byte) 5));
			close();
		}
		else if (button.getType() == WATER) {
			trainer.givePokemon(new Pokemon(handler.getItem(PokeminePlugin.POKEMINE_NAMESPACE, "squirtle"), (byte) 5));
			close();
		}
		else if (button.getType() == ELECTRIC) {
			trainer.givePokemon(new Pokemon(handler.getItem(PokeminePlugin.POKEMINE_NAMESPACE, "pikachu"), (byte) 5));
			close();
		}
		else if (button.getType() == NORMAL) {
			trainer.givePokemon(new Pokemon(handler.getItem(PokeminePlugin.POKEMINE_NAMESPACE, "eevee"), (byte) 5));
			close();
		}
	}

}
