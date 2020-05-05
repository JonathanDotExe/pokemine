package at.jojokobi.pokemine.items;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.PokemonWrapper;
import at.jojokobi.pokemine.gui.PartySelectionGUI;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.trainer.Trainer;

public abstract class AbstractPotion extends BattleItem{

	private int heal = 20;
	
	public AbstractPotion(String namespace, String identifier, PokeminePlugin plugin) {
		super(namespace, identifier, plugin);
		setHideFlags(true);
		setMaxStackSize(64);
		setChooseUser(true);
	}
	
	@Override
	public void startUse(PokemonWrapper user, PokemonWrapper defender, Battle battle) {
		if (user.getPokemon().getHealth() > 0) {
			user.getPokemon().setHealth(user.getPokemon().getHealth() + getHeal());
			user.getPokemon().getOwner().message(user.getPokemon().getName() + " thanked you!");
		}
	}

	@Override
	public void endUse(PokemonWrapper user, PokemonWrapper defender, Battle battle) {
		
	}

	@Override
	public boolean onTrainerUse(ItemStack item, Trainer trainer, Player player) {
		PartySelectionGUI gui = new PartySelectionGUI(player, trainer) {
			@Override
			public boolean call(Pokemon pokemon) {
				if (pokemon.getHealth() > 0) {
					pokemon.setHealth(pokemon.getHealth() + getHeal());
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
		return true;
	}

	public int getHeal() {
		return heal;
	}

	protected void setHeal(int heal) {
		this.heal = heal;
	}
	
	@Override
	public void onHit(ItemStack item, Entity damager, Entity defender) {
		
	}

}
