package at.jojokobi.pokemine.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.mcutil.ChatInputHandler;
import at.jojokobi.mcutil.gui.InventoryGUI;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.MoveUtil;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class PokemonGUI extends InventoryGUI{

	private Pokemon pokemon;
	private ChatInputHandler input;
	
	private static final int POKEMON_SLOT = 1;
	private static final int ITEM_SLOT = 2;
	private static final int MOVE_SLOT = 4;
	private static final int FRIENDS_SLOT = 3;
	
	private static final ItemStack EMPTY = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);

	public PokemonGUI(Player owner, Pokemon pokemon, ChatInputHandler input) {
		super(owner, Bukkit.createInventory(owner, INV_ROW, pokemon.toString()));
		this.pokemon = pokemon;
		this.input = input;
		initGUI();
	}

	@Override
	protected void initGUI() {
		getInventory().clear();
		addButton(pokemon.toItemStack(), POKEMON_SLOT);
		if (pokemon.getItem() != null) {
			addButton(pokemon.getItem(), ITEM_SLOT);
		}
		else {
			addButton(EMPTY, ITEM_SLOT);
		}
		//Friends
		ItemStack friends = new ItemStack(Material.EMERALD);
		ItemMeta meta = friends.getItemMeta();
		meta.setDisplayName("Show friends");
		friends.setItemMeta(meta);
		addButton(friends, FRIENDS_SLOT);
		MoveInstance[] moves = pokemon.getMoves();
		for (int i = 0; i < moves.length; i++) {
			if (moves[i] != null) {
				ItemStack moveItem = MoveUtil.itemFromMove(moves[i]);
				//Enchant selected item
				if (i == pokemon.getSelectedMoveIndex()) {
					moveItem.addUnsafeEnchantment(Enchantment.LURE, 1);
				}
				addButton(moveItem, i + MOVE_SLOT);
			}
		}
		fillEmpty(getFiller());
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		int index = getInventory().first(button);
		// Rename
		if (index == POKEMON_SLOT) {
//			ItemStack item = new ItemStack(Material.NAME_TAG);
//			ItemMeta meta = item.getItemMeta();
//			meta.setDisplayName(pokemon.getName());
//			item.setItemMeta(meta);
//			setNext(new TextInputGUI(getOwner(), item, "Rename your pokémon:") {
//				@Override
//				public void processText(String text) {
//					pokemon.setName(text);
//				}
//			});
			getOwner().sendMessage("Send the new name for " + pokemon.getName() + " in the chat!");
			input.requestPlayerInput(getOwner(), new ChatInputHandler.InputProcessor() {
				@Override
				public void process(Player player, String input) {
					pokemon.setName(input);
				}
			});
			close();
		}
		//Moves
		if (index - MOVE_SLOT >= 0 && index - MOVE_SLOT < Pokemon.MAX_MOVES) {
			pokemon.setSelectedMoveIndex(index - MOVE_SLOT);
			initGUI();
		}
		//Friends
		else if (index == FRIENDS_SLOT) {
			setNext(new LikesPokemonGUI(getOwner(), pokemon));
			close();
		}
		//Retrive Pokemon Item
		else if (button != null && button.equals(pokemon.getItem())) {
			getOwner().getInventory().addItem(pokemon.getItem());
			pokemon.setItem(null);
			addButton(getFiller(), ITEM_SLOT);
			initGUI();
		}
		//Set Pokemon Item
		else if (EMPTY.equals(button)) {
			setNext(new PokemonItemGUI(getOwner(), pokemon));
			close();
		}
	}
	
}
