package at.jojokobi.pokemine.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import at.jojokobi.mcutil.gui.InventoryGUIHandler;
import at.jojokobi.pokemine.moves.Move;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.MoveUtil;
import at.jojokobi.pokemine.pokemon.Pokemon;

public class MoveLearnGUI extends MoveGUI {

	private Move move;
	
	public static void learnMoves (InventoryGUIHandler guiHandler, Pokemon pokemon) {
		if (pokemon.getOwner().getEntity() instanceof Player) {
			MoveLearnGUI gui = MoveLearnGUI.createGUIForPokemon((Player) pokemon.getOwner().getEntity(), pokemon);
			if (gui != null) {
				guiHandler.addGUI(gui);
				gui.show();
			}
		}
	}
	
	public static MoveLearnGUI createGUIForPokemon (Player owner, Pokemon pokemon) {
		MoveLearnGUI gui = null;
		Move move = pokemon.fetchMoveToLearn();
		while (move != null && pokemon.learnMove(move)) {
			move = pokemon.fetchMoveToLearn();
		}
		if (move != null) {
			gui = new MoveLearnGUI(owner, pokemon, move);
			gui.setNext(createGUIForPokemon(owner, pokemon));
		}
		return gui;
	}
	
	public MoveLearnGUI(Player owner, Pokemon pokemon, Move move) {
		super(owner, pokemon, Bukkit.createInventory(owner.getPlayer(), INV_ROW * 3, "Which move do you want to overwrite?"));
		this.move = move;
		initGUI();
	}
	
	@Override
	protected void initGUI() {
		addButton(MoveUtil.itemFromMove(new MoveInstance(this.move)), 4);
		super.initGUI();
	}

	@Override
	protected void onMoveClick(MoveInstance move, ClickType click) {
		boolean learned = false;
		for (int i = 0; i < getPokemon().getMoves().length && !learned; i++) {
			if (getPokemon().getMoves()[i] == move) {
				getPokemon().getMoves()[i] = new MoveInstance(this.move);
				learned = true;
				close();
			}
		}
	}

}
