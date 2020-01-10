package at.jojokobi.pokemine.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.gui.ListGUI;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.trainer.Trainer;

public class PcSwapGUI extends ListGUI {

	private Trainer trainer;
//	private Pokemon selectedPokemon = null;
	private PokemonSwapper swapper;
	private ItemStack[] partyButtons = new ItemStack[6];
	private List<ItemStack> pcButtons = new ArrayList<>();

	public static final int PARTY_OFFSET = 2;
	public static final int PC_OFFSET = INV_ROW * 2;

	public PcSwapGUI(Player owner, Trainer trainer) {
		super(owner, Bukkit.createInventory(owner, INV_ROW * 6, trainer.getName() + "'s PC"));
		this.trainer = trainer;
		swapper = new PokemonSwapper(trainer);
		setItemsPerPage(27);
		setStartIndex(PC_OFFSET);
		initGUI();
	}

	@Override
	protected void initGUI() {
		getInventory().clear();
		partyButtons = new ItemStack[6];
		pcButtons.clear();
		int i = 0;
		for (Pokemon poke : trainer.getParty()) {
			ItemStack pokemon = poke.toItemStack();
			addButton(pokemon, i + PARTY_OFFSET);
			partyButtons[i] = pokemon;
			i++;
		}
		i = 0;
		for (Pokemon poke : trainer.getPokemon()) {
			ItemStack pokemon = poke.toItemStack();
//			addButton(pokemon, i + PC_OFFSET);
			pcButtons.add(pokemon);
			i++;
		}
		setItems(new ArrayList<ItemStack>(pcButtons));
		super.initGUI();
		fillEmpty(getFiller());
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		if (button != null && !checkPageButton(button)) {
			Pokemon pokemon = null;
			// Acutal Pokemon
			// Party
			if (Arrays.asList(partyButtons).contains(button)) {
				pokemon = trainer.getParty().get(Arrays.asList(partyButtons).indexOf(button));
			}
			// PC
			else if (pcButtons.contains(button)) {
				pokemon = trainer.getPokemon().get(pcButtons.indexOf(button));
			}
			// Last Pokemon
			swapper.pokemonClicked(pokemon);
			initGUI();
		}
	}

}
