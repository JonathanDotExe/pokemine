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
import at.jojokobi.pokemine.trainer.Trainer;

public class Revive extends BattleItem implements Buyable{
	
	public static final String NAME = "Revive";
	public static final String IDENTIFIER = "revive";
	public static final Material MATERIAL= Material.GLASS_BOTTLE;
	public static final short meta = 4;
	
	public Revive(PokeminePlugin plugin) {
		super(PokeminePlugin.POKEMINE_NAMESPACE, IDENTIFIER, plugin);
		setName(NAME);
		setMaterial(MATERIAL);
		setMeta(meta);
		setChooseUser(true);
		Bukkit.getScheduler().runTask(plugin, () -> registerRecipe());
		setMaxStackSize(0);
		ItemHandler.addCustomItem(this);
	}
	
	@Override
	public void startUse(PokemonWrapper user, PokemonWrapper defender, Battle battle) {
		//TODO: Fill PP
		if (user.getPokemon().getHealth() <= 0) {
			user.getPokemon().setHealth(user.getPokemon().getMaxHealth()/2);
			user.getPokemon().setPrimStatChange(null);
		}
	}

	@Override
	public void endUse(PokemonWrapper user, PokemonWrapper defender, Battle battle) {
		user.getPokemon().getOwner().message(user.getPokemon().getName() + " thanked you!");
	}
	
	@Override
	public void onHit(ItemStack item, Entity damager, Entity defender) {
		
	}

	@Override
	public Recipe getRecipe() {
		ItemStack item = createItem();
		item.setAmount(2);
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), getIdentifier()), item);
		recipe.shape(" S ", "SGS", " S ");
		recipe.setIngredient('S', Material.SUGAR);
		recipe.setIngredient('G', Material.GOLD_INGOT);
		return recipe;
	}

	@Override
	public void onTrainerUse(ItemStack item, Trainer trainer, Player player) {
		if (getPlugin().getBattleHandler().getTrainersBattle(trainer) == null) {
			PartySelectionGUI gui = new PartySelectionGUI(player, trainer){
				@Override
				public boolean call(Pokemon pokemon) {
					if (pokemon.getHealth() <= 0) {
						pokemon.setHealth(pokemon.getMaxHealth()/2);
						pokemon.setPrimStatChange(null);
						item.setAmount(item.getAmount() - 1);
					}
					else {
						trainer.message("It had no effect!");
					}
					return true;
				}
			};
			getPlugin().getGUIHandler().addGUI(gui);
			gui.show();
		}
		else {
			trainer.message("You can't heal during a battle.");
		}
	}

	@Override
	public int getBuyPrice() {
		return 2000;
	}

	@Override
	public byte getMinBuyLevel() {
		return 10;
	}

	@Override
	public ItemStack getBoughtItem() {
		return createItem();
	}

}
