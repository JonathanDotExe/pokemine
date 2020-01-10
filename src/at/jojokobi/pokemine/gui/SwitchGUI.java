package at.jojokobi.pokemine.gui;


import org.bukkit.entity.Player;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.battle.SwitchAction;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.trainer.Trainer;

public class SwitchGUI extends PartySelectionGUI{
//
//	private PokeminePlugin plugin;
	private PokemonContainer oldPokemon;
	private Battle battle;

	public SwitchGUI(Player player, Trainer trainer, PokemonContainer oldPokemon, Battle battle) {
		super(player, trainer);
//		this.plugin = plugin;
		this.battle = battle;
		this.oldPokemon = oldPokemon;
		initGUI();
	}

	@Override
	public boolean call(Pokemon pokemon) {
		oldPokemon.setNextAction(new SwitchAction(battle, oldPokemon, pokemon));
		return true;
	}

}
