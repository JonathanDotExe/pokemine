package at.jojokobi.pokemine.moves.movescriptveryold;

import java.util.Random;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.moves.DamageClass;
import at.jojokobi.pokemine.moves.Move;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonType;
@Deprecated
public class DamageCommand extends Command {
	
	private Move move;
	
	public DamageCommand(Move move) {
		this.move = move;
	}

	@Override
	public void execute(Battle battle, Pokemon performer, Pokemon defender) {
		Random random = new Random();
		//Stats and Effectivity
		int attack = move.getDamageClass() == DamageClass.PHYSICAL ? performer.getAttack() : performer.getSpecialAttack();
		int defense = move.getDamageClass() == DamageClass.PHYSICAL ? defender.getDefense() : defender.getSpecialDefense();
		int z = 100 - (random.nextInt(16));
		boolean stab = performer.getSpecies().hasType(move.getType());
		boolean critical = Math.random() < 0.0416;
		float effectivity = 1.0f;
		for (PokemonType type : defender.getSpecies().getTypes()) {
			effectivity *= type.getEffectivityMultiplier(move.getType());
		}
		//Factor 1
		float f1 = 1;
		if (performer.getPrimStatChange() != null) {
			if (move.getDamageClass() == DamageClass.PHYSICAL) {
				f1 = performer.getPrimStatChange().getPhysicalDamageModifier();
			}
			else {
				f1 = performer.getPrimStatChange().getSpecialDamageModifier();
			}
		}
		//Damage
		float damage = ((performer.getLevel() * (2.0f/5.0f) + 2) * move.getPower() * (attack/(50.0f*defense))*1 + 2) * (critical ? 1.5f : 1) * f1 * (z/100.0f) * (stab ? 1.5f : 1) * effectivity * 1;
		defender.setHealth(defender.getHealth() - Math.round(damage));
		//Message
		if (effectivity > 1) {
			battle.sendBattleMessage("It's super effective!");
		}
		else if (effectivity < 1) {
			battle.sendBattleMessage("It's not very effective effective!");
		}
		if (critical) {
			battle.sendBattleMessage("A critical hit!");
		}
		if (stab) {
			battle.sendBattleMessage(performer.getName() + " did more damage because of the STAB bonus!");
		}
		battle.sendBattleMessage("Effectivity: " + effectivity);
	}

}
