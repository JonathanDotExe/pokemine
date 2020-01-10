package at.jojokobi.pokemine.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.mcutil.gui.InventoryGUI;
import at.jojokobi.pokemine.trainer.Trainer;

public class PcGUI extends InventoryGUI {

	public static final String BOXES = "Your PC";
	public static final String SHOP = "Shop";
	public static final String PLACED_POKEMON = "Placed Pokémon";
	
	private Trainer trainer;

	public PcGUI(Player owner,  Trainer trainer) {
		super(owner, Bukkit.createInventory(owner, INV_ROW, "PC"));
		this.trainer = trainer;
		initGUI();
	}

	@Override
	protected void initGUI() {
		// PC Boxes
		ItemStack pokemon = new ItemStack(Material.CHEST);
		ItemMeta pokemeta = pokemon.getItemMeta();
		pokemeta.setDisplayName(BOXES);
		pokemon.setItemMeta(pokemeta);
		addButton(pokemon, 1);
		// Placed Pokemon
		ItemStack placed = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta placedmeta = placed.getItemMeta();
		placedmeta.setDisplayName(PLACED_POKEMON);
		placed.setItemMeta(placedmeta);
		addButton(placed, 2);
		// Shop
		ItemStack shop = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta shopmeta = shop.getItemMeta();
		shopmeta.setDisplayName(SHOP);
		shop.setItemMeta(shopmeta);
		addButton(shop, 3);
		
		fillEmpty(getFiller());
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		// Open Boxes
		if (BOXES.equals(button.getItemMeta().getDisplayName())) {
			PcSwapGUI gui = new PcSwapGUI(getOwner(), trainer);
			setNext(gui);
			close();
		}
		//Placed Pokémon
		else if (PLACED_POKEMON.equals(button.getItemMeta().getDisplayName())) {
			PlacedPokemonGUI gui = new PlacedPokemonGUI(getOwner(), trainer);
			setNext(gui);
			close();
		}
		//Open shop
		else if (SHOP.equals(button.getItemMeta().getDisplayName())) {
			ShopGUI gui = new ItemShopGUI(getOwner(), trainer);
			setNext(gui);
			close();
		}
	}

}
