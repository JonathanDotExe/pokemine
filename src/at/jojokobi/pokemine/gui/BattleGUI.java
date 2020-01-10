package at.jojokobi.pokemine.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.MoveAction;
import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.battle.RunAction;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.trainer.Trainer;

public class BattleGUI extends MoveGUI{

	private static final String RUN = "Run";
	private static final String SWITCH = "Switch";
	private static final String BAG = "Bag";
	
	private PokemonContainer pokemon;
	private Battle battle;
	private PokeminePlugin plugin;
	private Trainer trainer;

	public BattleGUI(Player player, Trainer trainer, Battle battle, PokemonContainer pokemon, PokeminePlugin plugin) {
		super(player, pokemon.getPokemon(), Bukkit.createInventory(player, 27, "Choose a move!"));
		this.pokemon = pokemon;
		this.battle = battle;
		this.trainer = trainer;
		this.plugin = plugin;
		initGUI();
	}

	@Override
	protected void initGUI() {
		super.initGUI();
		//Run
		addButton(getSwitchButton(), 4);
		addButton(getRunButton(), 6);
		addButton(getBagButton(), 7);
		fillEmpty(getFiller());
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		super.onButtonPress(button, click);
		if (button.getItemMeta().getDisplayName().equals(RUN) && pokemon.canSwitch()) {
			pokemon.setNextAction(new RunAction(battle, pokemon, pokemon));
			close();
		}
		else if (button.getItemMeta().getDisplayName().equals(SWITCH) && pokemon.canSwitch()) {
			SwitchGUI gui = new SwitchGUI(getOwner(), trainer, pokemon, battle);
			plugin.getGUIHandler().addGUI(gui);
			gui.show();
		}
		else if (button.getItemMeta().getDisplayName().equals(BAG)) {
			ItemGUI gui = new ItemGUI(getOwner(), pokemon, pokemon.getOppent(battle.getPokemonContainers()), battle);
			plugin.getGUIHandler().addGUI(gui);
			gui.show();
		}
	}
	
	
	
	public static ItemStack getRunButton () {
		ItemStack item = new ItemStack(Material.BARRIER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(RUN);
		List<String> lore = new ArrayList<>();
		lore.add("Ends the battle instantly!");
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getSwitchButton () {
		ItemStack item = new ItemStack(Material.WHITE_BED);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(SWITCH);
		List<String> lore = new ArrayList<>();
		lore.add("Switch your active battling Pokemon!");
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getBagButton () {
		ItemStack item = new ItemStack(Material.CHEST);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(BAG);
		List<String> lore = new ArrayList<>();
		lore.add("Use an Item!");
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}

	@Override
	protected void onMoveClick(MoveInstance move, ClickType click) {
		pokemon.setNextAction(new MoveAction(battle, move, pokemon, pokemon.getOppent(battle.getPokemonContainers())));
		close();
	}

}
