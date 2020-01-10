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
import at.jojokobi.pokemine.trainer.Trainer;

public class TradeGUI extends InventoryGUI{

	public static final int OTHER_OFFSET = 9*2 + PartyGUI.OFFSET;
	public static final int SUBMIT_INDEX = 14;
	
	private Trainer trainer;
	private Trainer otherTrainer;
	
	private Player other;
	
	private Pokemon ownPokemon;
	private Pokemon otherPokemon;
	
	private ItemStack submit;
	private PokemonHandler handler;
	private InventoryGUIHandler guiHandler;
	
	public TradeGUI(Player owner, Trainer trainer, Player other, Trainer otherTrainer, PokemonHandler handler, InventoryGUIHandler guiHandler) {
		super(owner, Bukkit.createInventory(owner, 27, "What do you want to trade?"));
		this.trainer = trainer;
		this.otherTrainer = otherTrainer;
		this.other = other;
		this.handler = handler;
		this.guiHandler = guiHandler;
		initGUI();
	}

	@Override
	protected void initGUI() {
		getInventory().clear();
		//Owner
//		Pokemon[] ownerParty = trainer.getParty();
		int i = 0;
		for (Pokemon poke : trainer.getPokemon()) {
			ItemStack pokemon = poke.toItemStack();
			addButton(pokemon, i + PartyGUI.OFFSET);
		}
		//Other
//		Pokemon[] otherParty = otherTrainer.getParty();
		i = 0;
		for (Pokemon poke : otherTrainer.getPokemon()) {
			ItemStack pokemon = poke.toItemStack();
			addButton(pokemon, i + PartyGUI.OFFSET);
		}
		//Submit
		submit = new ItemStack(Material.EMERALD);
		ItemMeta meta = submit.getItemMeta();
		meta.setDisplayName("Submit trade: " + (ownPokemon == null ? "nothing" : ownPokemon) + " for " + (otherPokemon == null ? "nothing" : otherPokemon));
		meta.setLore(Arrays.asList("WARNING", "You won't be able to get your selected pokemon back, ", "except for trading it back again!"));
		submit.setItemMeta(meta);
		addButton(submit, SUBMIT_INDEX);
		fillEmpty(getFiller());
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		int index = getInventory().first(button);
		//Owner
		if (index >= PartyGUI.OFFSET && index <= PartyGUI.OFFSET + trainer.getParty().size()) {
			ownPokemon = trainer.getParty().get(index - PartyGUI.OFFSET);
			initGUI();
		}
		//Other
		else if (index >= OTHER_OFFSET && index <= OTHER_OFFSET + trainer.getParty().size()) {
			otherPokemon = otherTrainer.getParty().get(index - OTHER_OFFSET);
			initGUI();
		}
		//Submit
		else if (ownPokemon != null && otherPokemon != null && button != null && button.equals(submit)) {
			SubmitGUI gui = new SubmitGUI(other, otherPokemon, ownPokemon, handler, guiHandler);
			setNext(gui);
			close();
		}
	}

}
