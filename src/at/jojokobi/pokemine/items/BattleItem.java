package at.jojokobi.pokemine.items;

import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.item.ItemUtil;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.PokemonWrapper;

public abstract class BattleItem extends PokemineItem {
	
	public static final String BATTLE_TAG = "battle";
	private boolean chooseUser = false;
	
	public BattleItem(String namespace, String identifier, PokeminePlugin plugin) {
		super(namespace, identifier, plugin);
	}
	
	@Override
	public boolean isItem(ItemStack item) {
		return super.isItem(item) && ItemUtil.getNBTBoolean(item, BATTLE_TAG);
	}
	
	@Override
	public ItemStack createItem() {
		ItemStack item = super.createItem();
		ItemUtil.setNBTBoolean(item, BATTLE_TAG, true);
		return item;
	}
	
	public abstract void startUse (PokemonWrapper user, PokemonWrapper defender, Battle battle);
	
	public abstract void endUse (PokemonWrapper user, PokemonWrapper defender, Battle battle);

	public boolean isChooseUser() {
		return chooseUser;
	}

	protected void setChooseUser(boolean chooseUser) {
		this.chooseUser = chooseUser;
	}

}
