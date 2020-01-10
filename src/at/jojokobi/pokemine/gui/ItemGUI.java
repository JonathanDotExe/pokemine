package at.jojokobi.pokemine.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.gui.InventoryGUI;
import at.jojokobi.mcutil.item.CustomItem;
import at.jojokobi.mcutil.item.ItemHandler;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.ItemAction;
import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.battle.PokemonWrapper;
import at.jojokobi.pokemine.items.BattleItem;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class ItemGUI extends InventoryGUI{

	private PokemonContainer user;
	private PokemonContainer defender;
	private Battle battle;

	public ItemGUI(Player owner, PokemonContainer user, PokemonContainer defender, Battle battle) {
		super(owner, owner.getInventory());
		this.user = user;
		this.defender = defender;
		this.battle = battle;
		initGUI();
	}

	@Override
	protected void initGUI() {
		
	}

	@Override
	protected void onButtonPress(ItemStack button, ClickType click) {
		CustomItem custom = ItemHandler.getCustomItem(button);
		if (custom instanceof BattleItem) {
			if (user.getNextAction(battle) == null) {
				BattleItem item = (BattleItem) custom;
				button.setAmount(button.getAmount() - 1);
				if (item.isChooseUser()) {
					setNext(new PartySelectionGUI(getOwner(), user.getPokemon().getOwner())  {
						@Override
						public boolean call(Pokemon pokemon) {
							user.setNextAction(new ItemAction(battle, item, user, pokemon == user.getPokemon() ? PokemonWrapper.fromPokemonContainer(user) : PokemonWrapper.fromPokemon(pokemon), defender));
							return true;
						}
					});
				}
				else  {
					user.setNextAction(new ItemAction(battle, item, user, PokemonWrapper.fromPokemonContainer(user), defender));
				}
				close();
			}
			else {
				getOwner().sendMessage("Your next BattleAction is already " + user.getNextAction(battle) + "!");
			}
		}
		else {
			getOwner().sendMessage(custom + " is not a BattleItem!");
		}
	}


}
