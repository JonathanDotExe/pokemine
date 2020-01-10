package at.jojokobi.pokemine.moves.movescriptveryold;

import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.pokemon.Pokemon;
@Deprecated
public abstract class StatusCommand extends Command{

	private Target target;
	private float chance = 1;
	
	public StatusCommand(Target target, float chance) {
		this.target = target;
		this.chance = chance;
	}
	
	@Override
	public void execute(Battle battle, Pokemon performer, Pokemon defender) {
		Pokemon pokemon = target == Target.PERFORMER ? performer : defender;
		if (Math.random() < chance) {
			battle.sendBattleMessage(applyValues(pokemon));
		}
	}
	
	public abstract String applyValues (Pokemon pokemon);
	
	protected Target getTarget() {
		return target;
	}

	protected void setTarget(Target target) {
		this.target = target;
	}

	protected float getChance() {
		return chance;
	}

	protected void setChance(float chance) {
		this.chance = chance;
	}

}
