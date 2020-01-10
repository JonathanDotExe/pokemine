package at.jojokobi.pokemine.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.pokemine.items.Buyable;
import at.jojokobi.pokemine.moves.LearningMethod;
import at.jojokobi.pokemine.moves.Move;
import at.jojokobi.pokemine.moves.MoveHandler;
import at.jojokobi.pokemine.trainer.Trainer;

public class MoveShopGUI extends ShopGUI{
	
	private MoveHandler moveHandler;
	private ItemStack item;

	public MoveShopGUI(Player player, Trainer trainer, MoveHandler moveHandler, ItemStack item) {
		super(player, trainer);
		this.moveHandler = moveHandler;
		this.item = item;
		initGUI();
	}

	@Override
	public List<Buyable> generateShopItems() {
		List<Buyable> buyables = new ArrayList<>();
		for (Move move : moveHandler.getItemList()) {
			if (move.getLearningMethod() != LearningMethod.NONE) {
				buyables.add(move);
			}
		}
		return buyables;
	}
	
	@Override
	protected void buy(Trainer trainer, Player player, Buyable bought) {
		super.buy(trainer, player, bought);
		close ();
		item.setAmount(item.getAmount() - 1);
	}

}
