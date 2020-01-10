package at.jojokobi.pokemine.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.gui.MoveLearnGUI;
import at.jojokobi.pokemine.gui.PartySelectionGUI;
import at.jojokobi.pokemine.moves.Move;
import at.jojokobi.pokemine.moves.MoveHandler;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.trainer.Trainer;

public class TM extends PokemineItem implements Buyable {

	public static final String NAME = "TM";
	public static final String IDENTIFIER = "tm";
	public static final Material MATERIAL = Material.SNOWBALL;
	public static final short meta = 4;
	private static final String MOVE = "move";
	private static final String MOVE_NAMESPACE = "move_namespace";
	private PokeminePlugin plugin;
	
	private NamespacedKey moveKey;
	private NamespacedKey moveNamespaceKey;

	public TM(PokeminePlugin plugin) {
		super(PokeminePlugin.POKEMINE_NAMESPACE, IDENTIFIER, plugin);
		this.plugin = plugin;
		setName(NAME);
		setMaterial(MATERIAL);
		setMeta(meta);
		setMaxStackSize(1);
		moveKey = new NamespacedKey(plugin, MOVE);
		moveNamespaceKey = new NamespacedKey(plugin, MOVE_NAMESPACE);
		ItemHandler.addCustomItem(this);
	}

	@Override
	public ItemStack createItem() {
		Random random = new Random();
		List<NamespacedEntry> moves = new ArrayList<NamespacedEntry>(MoveHandler.getInstance().getItems().keySet());
		NamespacedEntry selected = moves.get(random.nextInt(moves.size()));
		Move move = MoveHandler.getInstance().getItem(selected.getNamespace(), selected.getIdentifier());
		return getItem(move);
	}

	public ItemStack getItem(Move move) {
		ItemStack item = super.createItem();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(NAME + " - " + move.getName());
		item.setItemMeta(meta);
//		ItemUtil.setNBTString(item, MOVE_NAMESPACE, move.getNamespace());
//		ItemUtil.setNBTString(item, MOVE, move.getIdentifier());
		setItemDataString(meta, moveNamespaceKey, move.getNamespace());
		setItemDataString(meta, moveKey, move.getIdentifier());
		return item;
	}

	@Override
	public void onTrainerUse(ItemStack item, Trainer trainer, Player player) {
		PartySelectionGUI gui = new PartySelectionGUI(player, trainer) {
			@Override
			public boolean call(Pokemon pokemon) {
				String type = item.getItemMeta().getPersistentDataContainer().getOrDefault(moveKey, PersistentDataType.STRING, "pound");
				String namespace = item.getItemMeta().getPersistentDataContainer().getOrDefault(moveNamespaceKey, PersistentDataType.STRING, PokeminePlugin.POKEMINE_NAMESPACE);

				Move move = MoveHandler.getInstance().getItem(namespace, type);
				player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 1, 1);
				if (pokemon.getSpecies().canLearn(move)) {
					item.setAmount(item.getAmount() - 1);
					if (!pokemon.learnMove(move)) {
						MoveLearnGUI gui = new MoveLearnGUI(player, pokemon, move);
						plugin.getGUIHandler().addGUI(gui);
						gui.show();
					}
					return true;
//					if (pokemon.learnMove(move)) {
//						trainer.message(pokemon.getName() + " learned " + move.getName() + "!");
//						return true;
//					} else {
//						MoveLearnGUI moveGUI = new MoveLearnGUI(trainer.getPlayer(), pokemon, move);
//						plugin.getGUIHandler().addGUI(moveGUI);
//						moveGUI.show();
//						return false;
//					}
				} else {
					trainer.message("It had no effect!");
					return false;
				}
			}
		};
		plugin.getGUIHandler().addGUI(gui);
		gui.show();
	}

	@Override
	public Recipe getRecipe() {
		return null;
	}

	@Override
	public void onHit(ItemStack item, Entity damager, Entity defender) {

	}

	@Override
	public int getBuyPrice() {
		return 7000;
	}

	@Override
	public byte getMinBuyLevel() {
		return 15;
	}

	@Override
	public ItemStack getBoughtItem() {
		return createItem();
	}
}
