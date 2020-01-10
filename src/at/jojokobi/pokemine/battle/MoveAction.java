package at.jojokobi.pokemine.battle;

import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.pokemine.battle.animation.BattleAnimation;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.moves.movescript.MoveInitializer;
import at.jojokobi.pokemine.moves.procedures.wrappers.ProcedurePokemon;
import at.jojokobi.pokemine.pokemon.entity.PokemonCriteria;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;

public class MoveAction extends BattleAction {

	private MoveInstance move;
	private BattleAnimation animation;
	private boolean canExecute = true;
	private int round = 0;
	private boolean takePP = true;
	private MoveInitializer init;
	
	private boolean executed = false;

	private ProcedurePokemon procedurePerformer;
	private ProcedurePokemon procedureDefender;
	
	public MoveAction(Battle battle, MoveInstance move, PokemonContainer performer, PokemonContainer defender, int round,
			boolean takePP) {
		super(battle, performer, defender);
		this.move = move;
		this.round = round;
		this.takePP = takePP;
		setPriority(move.getMove().getPriority());
		
		procedurePerformer = new ProcedurePokemon(performer, battle);
		procedureDefender = new ProcedurePokemon(defender, battle);
	}

	public MoveAction(Battle battle, MoveInstance move, PokemonContainer performer, PokemonContainer defender) {
		this(battle, move, performer, defender, 0, true);
	}

	public MoveInstance getMove() {
		return move;
	}

	@Override
	public String[] startPerform() {
		move.getMove().preExecute(getBattle(), move, init, procedurePerformer, procedureDefender, round);
		double rand = Math.random();
		boolean canAttack = getPerformer().canAttack();
		// Initialize Move
		canExecute = !init.isFail() && (rand <= init.getAccuracy()
				* getPerformer().getAccuracy(getDefender().getEvasionLevel()) || init.getAccuracy() > 1)
				&& move.getPp() > 0 && canAttack;
		String[] text = { getPerformer().getPokemon().getSpecies().getTypes().get(0).getColor() + getPerformer().getPokemon().getName() + " used "
				+ getMove().getMove().getType().getColor() + getMove().getMove().getName() + "!" };
		if (canExecute) {
//			switch (getMove().getMove().getDamageClass()) {
//			case PHYSICAL:
//				break;
//			case SPECIAL:
//				sound = Sound.ENTITY_ENDERMAN_TELEPORT;
//				break;
//			case STATUS:
//				sound = Sound.ENTITY_GENERIC_DRINK;
//				break;
//			default:
//				break;
//			}
			// Animation
			EntityHandler handler = getBattle().getBattleHandler().getPlugin().getEntityHandler();
			PokemonEntity performerEntity = handler.getEntity(new PokemonCriteria(getPerformer().getPokemon()));
			PokemonEntity defenderEntity = handler.getEntity(new PokemonCriteria(getDefender().getPokemon()));
			if (defenderEntity != null && performerEntity != null) {
				this.animation = BattleAnimation.stringToAnimation(init.getAnimation(), performerEntity.getEntity(),
						defenderEntity.getEntity());
				setDuration(animation.getDuration() + 10);
			}
			getBattle().getBattleHandler().getPlugin().getAnimationHandler().addAnimation(animation);
		} else if (init.isFail()) {
			text = new String[] {
					getPerformer().getPokemon().getSpecies().getTypes().get(0).getColor() + getPerformer().getPokemon().getName() + " used "
							+ getMove().getMove().getType().getColor() + getMove().getMove().getName() + "!",
					"But it failed!" };
		} else if (!canAttack) {
			text = new String[] { getPerformer().getPokemon().getName() + " couldn't move!" };
		} else {
			text = new String[] {
					getPerformer().getPokemon().getSpecies().getTypes().get(0).getColor() + getPerformer().getPokemon().getName() + " used "
							+ getMove().getMove().getType().getColor() + getMove().getMove().getName() + "!",
					getDefender().getPokemon().getName() + " avoided the attack!" };
		}
		return text;
	}

//	private boolean canExecute() {
//		return move.getPp() > 0 && (getPerformer().getPrimStatChange() == null || getPerformer().getPrimStatChange().canAttack());
//	}

	@Override
	public String[] tickPerform() {
		if (animation != null) {
			if (getTime() == animation.getDuration()) {
				performMove();
			}
		}
		return new String[] {};
	}

	@Override
	public String[] stopPerform() {
		if (!executed) {
			performMove();
		}
		return new String[] {};
	}

	private void performMove() {
		executed = true;
		if (canExecute) {
			if (getPerformer().getPokemon().getHealth() > 0) {
				move.perform(getBattle(), init, procedurePerformer, procedureDefender, round);
			}
			if (takePP) {
				move.setPp(move.getPp() - 1);
			}
		}
	}

	@Override
	public void init() {
		init = move.getMove().initialize(getBattle(), move, procedurePerformer, procedureDefender, round);
		setPriority(init.getPriority());
	}

}
