package at.jojokobi.pokemine.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.PokemonWrapper;
import at.jojokobi.pokemine.gui.PartySelectionGUI;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.status.Confusion;
import at.jojokobi.pokemine.trainer.Trainer;

public class FullHeal extends BattleItem implements Buyable{

	public static final String NAME = "Full Heal";
	public static final String IDENTIFIER = "full_heal";
	public static final Material MATERIAL = Material.GLASS_BOTTLE;
	public static final short META = 5;

	public FullHeal(PokeminePlugin plugin) {
		super(PokeminePlugin.POKEMINE_NAMESPACE, IDENTIFIER, plugin);
		setHideFlags(true);
//		setMaxStackSize(64);
		setChooseUser(true);
		setName(NAME);
		setMaterial(MATERIAL);
		setMeta(META);
		Bukkit.getScheduler().runTask(plugin, () -> registerRecipe());
		setMaxStackSize(0);
		ItemHandler.addCustomItem(this);
	}

	@Override
	public void startUse(PokemonWrapper user, PokemonWrapper defender, Battle battle) {
		if (user.getPokemon().getHealth() > 0 && (user.getPokemon().getPrimStatChange() != null || user.hasSecStatChange(Confusion.class))) {
			user.getPokemon().setPrimStatChange(null);
			user.removeSecStatChange(Confusion.class);
		} else {
			user.getPokemon().getOwner().message("It had no effect!");
		}
	}

	@Override
	public void endUse(PokemonWrapper user, PokemonWrapper defender, Battle battle) {

	}

	@Override
	public Recipe getRecipe() {
		ItemStack item = createItem();
		item.setAmount(4);
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), getIdentifier()), item);
		recipe.shape(" R ", "RBR", "RMR");
		recipe.setIngredient('R', Material.REDSTONE);
		recipe.setIngredient('B', Material.GLASS_BOTTLE);
		recipe.setIngredient('M', Material.MILK_BUCKET);
		return recipe;
	}

	@Override
	public void onTrainerUse(ItemStack item, Trainer trainer, Player player) {
		PartySelectionGUI gui = new PartySelectionGUI(player, trainer) {
			@Override
			public boolean call(Pokemon pokemon) {
				if (pokemon.getHealth() > 0 && (pokemon.getPrimStatChange() != null)) {
					pokemon.setPrimStatChange(null);
					item.setAmount(item.getAmount() - 1);
				} else {
					trainer.message("It had no effect!");
				}
				return true;
			}
		};
		getPlugin().getGUIHandler().addGUI(gui);
		gui.show();
	}

	@Override
	public void onHit(ItemStack item, Entity damager, Entity defender) {

	}

	@Override
	public int getBuyPrice() {
		return 400;
	}

	@Override
	public byte getMinBuyLevel() {
		return 12;
	}

	@Override
	public ItemStack getBoughtItem() {
		return createItem();
	}

}
