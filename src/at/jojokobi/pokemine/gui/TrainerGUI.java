package at.jojokobi.pokemine.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.mcutil.gui.InventoryGUI;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.PokemonType;
import at.jojokobi.pokemine.trainer.Trainer;

public class TrainerGUI extends InventoryGUI{
	
	public static final Material NAME_MATERIAL = Material.NAME_TAG;
	public static final Material RANK_MATERIAL = Material.DIAMOND;
	public static final Material LEVEL_MATERIAL = Material.EXPERIENCE_BOTTLE;
	public static final Material BADGES_MATERIAL = Material.GOLDEN_SWORD;
	public static final Material MONEY_MATERIAL = Material.GOLD_NUGGET;
	public static final Material POKEDEX_MATERIAL = Material.WRITABLE_BOOK;
	public static final Material PARTY_MATERIAL = Material.DIAMOND_SWORD;
//	public static final Material PC_MATERIAL = Material.ENCHANTMENT_TABLE;
	
	public static final String POKEDEX_NAME = "Pokédex";
	public static final String PARTY_NAME = "Your Party";
//	public static final String PC_NAME = "Your PC";
	
	private Trainer trainer;
	private PokeminePlugin plugin;
	
	public TrainerGUI(Player owner, Trainer trainer, PokeminePlugin plugin) {
		super(owner, Bukkit.createInventory(owner, 9, trainer.getName() + "'s Trainerpass"));
		this.plugin = plugin;
		this.trainer = trainer;
		initGUI();
	}

	@Override
	protected void initGUI() {
		//Name
		ItemStack name = new ItemStack(NAME_MATERIAL);
		ItemMeta nameMeta = name.getItemMeta();
		nameMeta.setDisplayName(trainer.getName());
		nameMeta.setLore(Arrays.asList("* Your name"));
		name.setItemMeta(nameMeta);
		addButton(name, 0);
		//Rank
		ItemStack rank = new ItemStack(RANK_MATERIAL);
		ItemMeta rankMeta = rank.getItemMeta();
		rankMeta.setDisplayName(trainer.getRank().getName());
		rankMeta.setLore(Arrays.asList("* Your rank"));
		rank.setItemMeta(rankMeta);
		addButton(rank, 1);
		//Level
		ItemStack level = new ItemStack(LEVEL_MATERIAL);
		ItemMeta levelMeta = level.getItemMeta();
		levelMeta.setDisplayName("Level " + trainer.getLevel());
		levelMeta.setLore(Arrays.asList("* Your Level"));
		level.setItemMeta(levelMeta);
		addButton(level, 2);
		//Badges
		ItemStack badges = new ItemStack(BADGES_MATERIAL);
		ItemMeta badgesMeta = level.getItemMeta();
		badgesMeta.setDisplayName("Your Badges");
		List<String> lore = new ArrayList<>();
		for (PokemonType badge : trainer.getBadges().keySet()) {
			lore.add(badge.toString() .toUpperCase() + " Lvl." + trainer.getBadge(badge));
		}
		badgesMeta.setLore(lore);
		badges.setItemMeta(badgesMeta);
		addButton(badges, 3);
		//Money
		ItemStack money = new ItemStack(LEVEL_MATERIAL);
		ItemMeta moneyMeta = money.getItemMeta();
		moneyMeta.setDisplayName(trainer.getMoney() + " PokéDollars");
		moneyMeta.setLore(Arrays.asList("* Your Amount of Money"));
		money.setItemMeta(moneyMeta);
		addButton(money, 4);
		//Champ defeated
		ItemStack champDefeated = new ItemStack(trainer.isDefeatedChamp() ? Material.GREEN_STAINED_GLASS_PANE : Material.PURPLE_STAINED_GLASS_PANE);
		ItemMeta champDefeatedMeta = champDefeated.getItemMeta();
		champDefeatedMeta.setDisplayName(trainer.isDefeatedChamp() ? "Champ defeated" : "Champ not defeated");
		champDefeated.setItemMeta(champDefeatedMeta);
		addButton(champDefeated, 6);
		//Pokedex
		ItemStack pokedex = new ItemStack(POKEDEX_MATERIAL);
		ItemMeta pokedexMeta = pokedex.getItemMeta();
		pokedexMeta.setDisplayName(POKEDEX_NAME);
		pokedexMeta.setLore(Arrays.asList("* Click to open"));
		pokedex.setItemMeta(pokedexMeta);
		addButton(pokedex, 7);
		//Party
		ItemStack party = new ItemStack(PARTY_MATERIAL);
		ItemMeta partyMeta = party.getItemMeta();
		partyMeta.setDisplayName(PARTY_NAME);
		partyMeta.setLore(Arrays.asList("* Click to open"));
		party.setItemMeta(partyMeta);
		addButton(party, 8);
/*		//PC
		ItemStack pc = new ItemStack(PC_MATERIAL);
		ItemMeta pcMeta = pc.getItemMeta();
		pcMeta.setDisplayName(PC_NAME);
		pcMeta.setLore(Arrays.asList("* Click to open"));
		pc.setItemMeta(pcMeta);
		addButton(pc, 8);*/
		//Fill
		fillEmpty(getFiller());
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		if (PARTY_NAME.equals(button.getItemMeta().getDisplayName())) {
			PartySwapGUI gui = new PartySwapGUI(getOwner(), trainer, plugin);
			setNext(gui);
			close();
		}
		else if (POKEDEX_NAME.equals(button.getItemMeta().getDisplayName())) {
			PokedexGUI gui = new PokedexGUI(getOwner(), trainer, PokemonHandler.getInstance());
			setNext(gui);
			close();
		}
	}
	
}
