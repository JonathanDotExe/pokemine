package at.jojokobi.pokemine.battle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import at.jojokobi.mcutil.music.Music;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.battle.effects.BattleEffect;
import at.jojokobi.pokemine.gui.DeathSwitchGUI;
import at.jojokobi.pokemine.gui.MoveLearnGUI;
import at.jojokobi.pokemine.pokemon.MathUtil;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.entity.PokemonCriteria;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;
import at.jojokobi.pokemine.pokemon.status.SecStatChange;
import at.jojokobi.pokemine.trainer.Trainer;
import at.jojokobi.pokemine.trainer.WildPokemonTrainer;

public class Battle {
	
	public static final String POKEMON_BATTLE_THEME_NAME = "pokemon.battle";
	public static final String TRAINER_BATTLE_THEME_NAME = "trainer.battle";
	public static final String GYM_BATTLE_THEME_NAME = "gym.battle";
	public static final String WIN_THEME_NAME = "pokemon.win";
	
	public static final Music POKEMON_BATTLE_THEME = new Music(POKEMON_BATTLE_THEME_NAME, 180);
	public static final Music TRAINER_BATTLE_THEME = new Music(TRAINER_BATTLE_THEME_NAME, 180);
	public static final Music GYM_BATTLE_THEME = new Music(GYM_BATTLE_THEME_NAME, 180);
	public static final Music WIN_THEME = new Music(WIN_THEME_NAME, 25);
	
	private BattleHandler handler;
	private Map<BattleAttendant, PokemonContainer[]> trainers;
	
	private List<BattleAction> actionQueue = new ArrayList<>();
	private Set<Pokemon> epPokemon = new HashSet<>();
	private List<DeathSwitchGUI> switchGuis = new ArrayList<>();
	private List<BattleEffect> effects = new ArrayList<>();
//	private List<MoveLearnGUI> moveGuiQueue = new ArrayList<MoveLearnGUI>();
	
	private int timer = 0;

	public Battle(BattleHandler handler, Map<BattleAttendant, BattleAttendant[]> trainers) {
		super();
		this.handler = handler;
		this.trainers = trainers;
	}

	public void loop() {
		//Switch GUIs
		if (!switchGuis.isEmpty()) {
			if (switchGuis.stream().allMatch((gui) -> gui.getNewPokemon() != null)) {
				for (DeathSwitchGUI gui : switchGuis) {
					switchPokemon(gui.getOldPokemon(), gui.getNewPokemon(), true);
				}
				switchGuis.clear();
			}
			epPokemon.clear();
			updateEpList();
		}
		else if (actionQueue.isEmpty()) {
/*			if (!moveGuiQueue.isEmpty()) {
				if (handler.getPlugin().getGUIHandler().getGUIs().contains((moveGuiQueue.get(0)))) {
					moveGuiQueue.get(0).show();
					moveGuiQueue.remove(0);
				}
			}
			else */
			if (timer >= 100) {
				requestBattleActions();
				timer = 0;
			}
			if (Arrays.stream(getPokemonContainers()).allMatch((PokemonContainer pokemon) -> pokemon.hasNextAction(this))) {
				//Get next Actions
				for (PokemonContainer poke : getPokemonContainers()) {
					actionQueue.add(poke.getNextAction(this));
				}
				for (BattleAction action : actionQueue) {
					action.init();
				}
				//Sort Actions
				Collections.sort(actionQueue, new Comparator<BattleAction>() {
					@Override
					public int compare(BattleAction action1, BattleAction action2) {
						int same = Math.round(action2.getPriority() - action1.getPriority());
						if (same == 0) {
							same = action2.getPerformer().getSpeed() - action1.getPerformer().getSpeed();
						}
						return same;
					}
		
				});
				sendBattleStatus();
				for (PokemonContainer poke : getPokemonContainers()) {
					for (SecStatChange stat : poke.getStatChanges()) {
						sendBattleMessage(stat.getMessage(poke.getPokemon()));
					}
				}
				//Send Round start
				for (BattleEffect effect : new ArrayList<>(effects)) {
					effect.startRound();
				}
				for (PokemonContainer poke : getPokemonContainers()) {
					poke.startRound();
				}
			}
		}
		else {
			//All actions
			if (actionQueue.get(0).getTime() == 0) {
				//Send Turn start
				for (BattleEffect effect : new ArrayList<>(effects)) {
					effect.startTurn(actionQueue.get(0).getPerformer());
				}
				actionQueue.get(0).getPerformer().startTurn();
				actionQueue.get(0).getPerformer().sendStatChangeMessages(this);
			}
			actionQueue.get(0).nextStep();
			if (actionQueue.get(0).isFinished()) {
				//Send Turn end
				for (BattleEffect effect : new ArrayList<>(effects)) {
					effect.endTurn(actionQueue.get(0).getPerformer());
				}
				actionQueue.get(0).getPerformer().endTurn();
				actionQueue.remove(0);
				sendBattleStatus();
				//End Round
				if (actionQueue.isEmpty()) {
					timer = 50;
					//Send Round end
					for (BattleEffect effect : new ArrayList<>(effects)) {
						effect.endRound();
					}
					for (PokemonContainer poke : getPokemonContainers()) {
						poke.setLastDamage(null);
						poke.endRound();
					}
					switchFaintedPokemon();
				}
			}
		}
		updateEpList();
		timer++;
	}
	
	private void requestBattleActions () {
		for (PokemonContainer poke : getPokemonContainers()) {
			if (poke.getNextAction(this) == null) {
//				Player player = (Player) poke.getPokemon().getOwner().getEntity();
//				if (handler.getPlugin().getGUIHandler().getPlayersGUI(player.getPlayer()) == null) {
//					BattleGUI gui = new BattleGUI(player, poke.getPokemon().getOwner(), this, poke, handler.getPlugin());
//					handler.getPlugin().getGUIHandler().addGUI(gui);
//					gui.show();
//				}
				poke.requestNextAction(this);
			}
		}
	}
	
	private void switchFaintedPokemon () {
		PokemonContainer[] pokemon = getPokemonContainers();
		for (int i = 0; i < pokemon.length; i++) {
			if (pokemon[i].getPokemon().getHealth() <= 0) {
				String text = pokemon[i].getPokemon().getName() + " fainted!";
				Pokemon nextPokemon = pokemon[i].getPokemon().getOwner().getNextUsablePokemon();
				//Give EP
				giveEpToPokemon(pokemon[i].getPokemon());
				//Battle ends
				if (nextPokemon == null) {
					onBattleWin(getOwner(pokemon[i].getPokemon()));
					end();
				}
				else {
					//Let the trainer choose who battles next
					if (pokemon[i].getPokemon().getOwner().getEntity() instanceof Player) {
						DeathSwitchGUI gui = new DeathSwitchGUI((Player) pokemon[i].getPokemon().getOwner().getEntity(), pokemon[i].getPokemon().getOwner(), pokemon[i]);
						handler.getPlugin().getGUIHandler().addGUI(gui);
						switchGuis.add(gui);
						gui.show();
					}
					//Or just switch the next pokemon on the list if its not a player
					else {
						switchPokemon(pokemon[i], nextPokemon, true);
					}
//					switchPokemon(pokemon[i], nextPokemon, true);
//					PokemonEntityHandler handler = this.handler.getPlugin().getPokemonEntityHandler();
//					PokemonUtil.switchPokemon(handler , pokemon[i].getPokemon(),  nextPokemon);
//					giveEpToPokemon(pokemon[i].getPokemon());
//					pokemon[i].getPokemon().setBattling(false);
//					nextPokemon.setBattling(true);
//					pokemon[i].setPokemon(nextPokemon);
				}
				sendBattleMessage(text);
				sendBattleTitle(text, "");
			}
		}
	}
	
	private void onBattleWin (BattleAttendant loser) {
		for (BattleAttendant attendant : trainers.keySet()) {
			attendant.endBattle(this, loser);
			if (attendant != loser) {
				sendBattleMessage("<" + attendant.getName() + "> " + attendant.getLooseMessage());
			}
			else {
				sendBattleMessage("<" + attendant.getName() + "> " + attendant.getWinMessage());
			}
		}
		//Give the winner badges
//		if (pokemon[i].getPokemon().getOwner().getRank().getBadge() != null) {
//			for (Pokemon poke : getPokemon()) {
//				if (poke.getOwner() != pokemon[i].getPokemon().getOwner()) {
//					poke.getOwner().recieveBadge(pokemon[i].getPokemon().getOwner().getRank().getBadge(), pokemon[i].getPokemon().getOwner().getLevel());
//				}
//			}
//		}
//		//Give money to the winner
//		for (Pokemon poke : getPokemon()) {
//			if (poke.getOwner() != pokemon[i].getPokemon().getOwner()) {
//				poke.getOwner().addMoney(MathUtil.calcPrizeMoney(pokemon[i].getPokemon().getOwner()));
//			}
//		}
//		//Win/Lose message
//		for (Trainer trainer : getTrainers()) {
//			if (pokemon[i].getPokemon().getOwner() == trainer) {
//			}
//			else if (! (trainer instanceof WildPokemonTrainer)) {
//				sendBattleMessage("<" + trainer.getName() + "> " + trainer.getRank().getRandomWinMessage());
//			}
//		}
//		//Set champ defeated
//		for (Pokemon poke : getPokemon()) {
//			if (poke.getOwner() != pokemon[i].getPokemon().getOwner()) {
//				if (pokemon[i].getPokemon().getOwner().isChamp()) {
//					poke.getOwner().setDefeatedChamp(true);
//				}
//				else {
//					poke.getOwner().setEliteFourDefeatLevel(pokemon[i].getPokemon().getOwner().getEliteFourLevel());							}
//			}
//		}
	}
	
	private void updateEpList () {
		for (Pokemon poke : getPokemon()) {
			if (!epPokemon.contains(poke)) {
				epPokemon.add(poke);
			}
		}
	}
	
	public void giveEpToPokemon (Pokemon defeated) {
		Set<Pokemon> expShare = new HashSet<>();
		//Normal Pokemon
		for (Pokemon poke : epPokemon) {
			if (poke.getHealth() > 0 && poke.getOwner() != defeated.getOwner()) {
				if (poke.getOwner().hasExpShare()) {
					expShare.addAll(poke.getOwner().getParty());
				}
				giveEpToPokemon(poke, defeated, false);
			}
		}
		expShare.removeAll(epPokemon);
		//Exp share Pokemon
		for (Pokemon pokemon : expShare) {
			giveEpToPokemon(pokemon, defeated, true);
		}
	}
	
	private void giveEpToPokemon (Pokemon pokemon, Pokemon defeated, boolean expShare) {
		int ep = MathUtil.calcWinEp(pokemon, defeated) / (expShare ? 2 : 1) * PokeminePlugin.getBattleExperienceMultiplier();
		pokemon.setHealthEV(pokemon.getHealthEV() + defeated.getSpecies().getHealthFp());
		pokemon.setAttackEV(pokemon.getAttackEV() + defeated.getSpecies().getAttackFp());
		pokemon.setDefenseEV(pokemon.getDefenseEV() + defeated.getSpecies().getDefenseFp());
		pokemon.setSpecialAttackEV(pokemon.getSpecialAttackEV() + defeated.getSpecies().getSpecialAttackFp());
		pokemon.setSpecialDefenseEV(pokemon.getSpecialDefenseEV() + defeated.getSpecies().getSpecialDefenseFp());
		pokemon.setSpeedEV(pokemon.getSpeedEV() + defeated.getSpecies().getSpeedFp());
		pokemon.getOwner().message(pokemon.getName() + " gained " + ep + "XP!");
		//Moves
		boolean leveledUp = pokemon.gainEp(ep);
		if (leveledUp) {
			if (pokemon.getOwner().getEntity() instanceof Player) {
				MoveLearnGUI.learnMoves(handler.getPlugin().getGUIHandler(), pokemon);
			}
		}
//		if (poke.setEp(poke.getEp() + ep) && poke.getOwner() instanceof PlayerTrainer) {
//			int newlevel = poke.getLevel();
//			List<Move> moves = new ArrayList<>();
//			for (MoveLearnCondition condition : poke.getSpecies().getLeranableMoves()) {
//				if (oldlevel < condition.getLevel() && newlevel >= condition.getLevel()) {
//					moves.add(condition.getMove());
//				}
//			}
//			poke.learnMoves(moves, handler.getPlugin().getGUIHandler());
//		}
		playBattleSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
	}
	
	public void switchPokemon (PokemonContainer oldPokemon, Pokemon newPokemon, boolean force) {
		if (!handler.isBattling(newPokemon) && newPokemon.getOwner() == oldPokemon.getPokemon().getOwner() && (oldPokemon.canSwitch() || force)) {
			//Switch Entities
			PokemonEntity entity = handler.getPlugin().getEntityHandler().getEntity(new PokemonCriteria(oldPokemon.getPokemon()));
			if (entity != null) {
				entity.switchPokemon(newPokemon);
			}
			//Set Pokemon
//			oldPokemon.getPokemon().setBattling(false);
			for (PokemonContainer pokemon : getPokemonContainers()) {
				//TODO: rename
				pokemon.switchPokemon(oldPokemon, newPokemon);
			}
//			oldPokemon.getPokemon().switchPokemon(oldPokemon.getPokemon(), newPokemon);
			
//			oldPokemon.setNextAction(null);
//			oldPokemon.getPokemon().clearStatChanges();
//			oldPokemon.getPokemon().clearStatValueChanges();
			oldPokemon.setPokemon(newPokemon);
//			newPokemon.setBattling(true);
		}
	}
	
	public void switchPokemon (Pokemon oldPokemon, Pokemon newPokemon, boolean force) {
		PokemonContainer poke = null;
		for (int i = 0; i < pokemon.length && poke == null; i++) {
			if (pokemon[i].getPokemon() == oldPokemon) {
				poke = pokemon[i];
			}
		}
		if (poke != null) {
			switchPokemon(poke, newPokemon, force);
		}
	}
	
	public void sendBattleMessage (String message) {
		for (Trainer trainer : getTrainers()) {
			trainer.message(message);
		}
	}
	
	public void sendBattleTitle (String title, String subtitle) {
		for (Trainer trainer : getTrainers()) {
			if (trainer.getEntity() instanceof Player) {
				Player player = (Player) trainer.getEntity();
				player.sendTitle(title, subtitle, 2, 80, 2);
			}
		}
	}
	
	public void sendBattleStatus () {
		sendBattleMessage("--------------------------------------------------");
		for (Pokemon pkmn : getPokemon()) {
			sendBattleMessage(pkmn.toString() + " " + pkmn.getHealth() + "/" + pkmn.getMaxHealth());
		}
		sendBattleMessage("--------------------------------------------------");
		sendBattleMessage("");
	}
	
	public void playBattleSound (Sound sound) {
		for (Trainer trainer : getTrainers()) {
			if (trainer.getEntity() instanceof Player) {
				Player player = (Player) trainer.getEntity();
				player.playSound(player.getPlayer().getLocation(), sound, 1, 1);
			}
		}
	}
	
	public void playBattleSound (String sound) {
		for (Trainer trainer : getTrainers()) {
			if (trainer.getEntity() instanceof Player) {
				Player player = (Player) trainer.getEntity();
				player.playSound(player.getPlayer().getLocation(), sound, 1, 1);
			}
		}
	}
	
	public void stopBattleSound (String sound) {
		for (Trainer trainer : getTrainers()) {
			if (trainer.getEntity() instanceof Player) {
				Player player = (Player) trainer.getEntity();
				player.stopSound( sound);
			}
		}
	}
	
	public void playBattleMusic (Music music, boolean repeat) {
		for (Trainer trainer : getTrainers()) {
			if (trainer.getEntity() instanceof Player) {
				Player player = (Player) trainer.getEntity();
				handler.getPlugin().getMusicHandler().playMusic(music, player, repeat);
			}
		}
	}
	
	public Pokemon[] getPokemon() {
		Pokemon[] pokemon = new Pokemon[this.pokemon.length];
		for (int i = 0; i < pokemon.length; i++) {
			pokemon[i] = this.pokemon[i].getPokemon();
		}
		return pokemon.clone();
	}
	
	public PokemonContainer[] getPokemonContainers() {
		return pokemon.clone();
	}
	
	public PokemonContainer containerForPokemon (Pokemon pokemon) {
		PokemonContainer container = null;
		for (int i = 0; i < this.pokemon.length && container == null; i++) {
			if (this.pokemon[i].getPokemon() == pokemon) {
				container = this.pokemon[i];
			}
		}
		return container;
	}
	
	private Music getMusic () {
		Music music = POKEMON_BATTLE_THEME;
		if (Arrays.stream(getPokemon()).anyMatch((poke) -> poke.getOwner().getRank().getBadge() != null)) {
			music = GYM_BATTLE_THEME;
		}
		else if (Arrays.stream(getPokemon()).allMatch((poke) -> !poke.isWild())) {
			music = TRAINER_BATTLE_THEME;
		}
		return music;
	}

	public void start() {
		for (PokemonContainer pokemon : getPokemonContainers()) {
//			pokemon.setNextAction(null);
//			pokemon.setBattling(true);
//			pokemon.getPokemon().clearStatValueChanges();
//			pokemon.getPokemon().clearStatChanges();
			if (pokemon.getPokemon().isWild()) {
				pokemon.getPokemon().heal();
			}
		}
		//Start message
		for (Trainer trainer : getTrainers()) {
			if (!(trainer instanceof WildPokemonTrainer)) {
				sendBattleMessage("<" + trainer.getName() + "> " + trainer.getRank().getRandomStartMessage());
			}
		}
		handler.startBattle(this);
		playBattleMusic(getMusic(), true);
	}

	public void end() {
		for (PokemonContainer container : getPokemonContainers()) {
			Pokemon pokemon = container.getPokemon();
//			container.setNextAction(null);
//			pokemon.setBattling(false);
			//Clear Temp Stats
//			for (Pokemon poke : pokemon.getOwner().getParty()) {
//				if (poke != null) {
//					poke.clearStatValueChanges();
//					poke.clearStatChanges();
//				}
//			}
			boolean evolved = pokemon.evolve(PokemonHandler.getInstance(), null, false);
			if (evolved) {
				pokemon.getOwner().message("Your " + pokemon.getName() + " evolved!");
				MoveLearnGUI.learnMoves(handler.getPlugin().getGUIHandler(), pokemon);
			}
			if ((pokemon.isWild() || pokemon.getOwner().getRank().getBadge() != null) && pokemon.getHealth() > 0) {
				pokemon.heal();
			}
		}
		handler.endBattle(this);
		playBattleMusic(WIN_THEME, false);
	}
	
	public BattleHandler getBattleHandler () {
		return handler;
	}
	
	public boolean addBattleEffect (BattleEffect effect) {
		return effects.add(effect);
	}
	
	public boolean removeBattleEffect (BattleEffect effect) {
		return effects.remove(effect);
	}
	
	public boolean removeBattleEffect (String effect) {
		boolean deleted = false;
		for (BattleEffect e : new ArrayList<>(effects)) {
			if (e.getScriptName().equals(effect)) {
				removeBattleEffect(e);
				deleted = true;
			}
		}
		return deleted;
	}
	
	private BattleAttendant getOwner (Pokemon pokemon) {
		for (BattleAttendant attendant : trainers.keySet()) {
			if (Arrays.stream(trainers.get(attendant)).anyMatch(p -> p.getPokemon() == pokemon)) {
				return attendant;
			}
		}
		return null;
	}
	
//
//	public Pokemon getPokemon1() {
//		return pokemon1;
//	}
//
//
//	public void setPokemon1(Pokemon pokemon1) {
//		this.pokemon1 = pokemon1;
//	}
//
//
//	public Pokemon getPokemon2() {
//		return pokemon2;
//	}
//
//
//	public void setPokemon2(Pokemon pokemon2) {
//		this.pokemon2 = pokemon2;
//	}
	
}
