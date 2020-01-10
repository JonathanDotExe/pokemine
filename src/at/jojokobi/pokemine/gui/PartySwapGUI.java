package at.jojokobi.pokemine.gui;


import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.entity.PokemonBehaviorType;
import at.jojokobi.pokemine.pokemon.entity.PokemonCriteria;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;
import at.jojokobi.pokemine.trainer.Trainer;

public class PartySwapGUI extends PartyGUI {
	
	private PokemonSwapper swapper;
	private PokeminePlugin plugin;
	private Trainer trainer;
	
	public PartySwapGUI(Player owner, Trainer trainer, PokeminePlugin plugin) {
		super(owner, trainer);
		this.plugin = plugin;
		this.trainer = trainer;
		swapper = new PokemonSwapper(trainer);
		initGUI();
	}

	@Override
	protected void onPokemonClick (Pokemon pokemon, ClickType click) {
		if (click == ClickType.SHIFT_RIGHT) {
			if (trainer.makePlaced(pokemon)) {
				initGUI ();
				getTrainer().message("You placed " + pokemon.getName() + " here! Use your PC to retieve it!");
				EntityHandler handler = plugin.getEntityHandler();
				PokemonEntity entity = new PokemonEntity (getOwner().getLocation(), pokemon, handler);
				entity.setBehaviorType(PokemonBehaviorType.PLACED_POKEMON);
				entity.setDespawnTicks(-1);
				handler.addEntity(entity);
			}
		}
		else if (click == ClickType.SHIFT_LEFT) {
			EntityHandler handler = plugin.getEntityHandler();
			if (handler.getEntity(new PokemonCriteria(pokemon)) == null) {
				Location place = getOwner().getTargetBlock(null, 10).getLocation().add(0, 1, 0);
				place.setDirection(getTrainer().getEntity().getLocation().getDirection().multiply(-1));
				PokemonEntity entity = new PokemonEntity(place, pokemon, handler);
				handler.addEntity(entity);
				getTrainer().message("Go " + pokemon.getName() +"!");
			}
			else {
				getTrainer().message("This Pokemon is already outside it's Pokeball!");
			}
		}
		else if (click == ClickType.RIGHT) {
			setNext(new PokemonGUI(getOwner(), pokemon, plugin.getInputHandler()));
			close();
		}
		else {
			swapper.pokemonClicked(pokemon);
			initGUI();
		}
	}

}
