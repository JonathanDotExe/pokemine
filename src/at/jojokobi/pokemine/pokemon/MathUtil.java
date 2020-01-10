package at.jojokobi.pokemine.pokemon;

import java.util.Random;

import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;
import at.jojokobi.pokemine.trainer.PlayerTrainerHandler;
import at.jojokobi.pokemine.trainer.Trainer;
import at.jojokobi.pokemine.trainer.TrainerUtil;

public final class MathUtil {

	private MathUtil() {
		
	}
	
	public static int calcWinEp (Pokemon winner, Pokemon loser) {
		double level = loser.getLevel();
		double win_level = winner.getLevel();
		double base = loser.getSpecies().getWinXp();
		int ep = (int) Math.floor(((base*level/5.0) * (Math.pow(2*level+10, 2.5))/(Math.pow(level+win_level+10,2.5)) + 1));
		return ep;
	}
	
	public static byte calcPokemonLevel (PokemonSpecies species) {
		Random random = new Random();
		double percent = (random.nextGaussian() + random.nextGaussian())/2.0;
		byte level = (byte) species.getWildLevel();
//		if (percent > 0.5) {
//			level += (int) ((percent - 0.5) * 2 * (50 - level));
//		}
//		else {
//			level -= (int) ((0.5 - percent) * 2 * (50 - level));
//		}
		level = (byte)((percent + 1) * level);
		if (level < 1) {
			level = (byte) species.getWildLevel();
		}
		return level;
	}
	
	public static float calcDamage (int level, float power, int attack, int defense) {
		int z = 100 - (int)(Math.random()*16);
		float damage = ((level * (2.0f/5.0f) + 2) * power * (attack/(50.0f*defense))*1 + 2) * (z/100f);
		return damage;
	}
	
	public static byte calcPokemonLevel (PokemonEntity entity, PlayerTrainerHandler handler) {
		byte level = entity.getPokemon().getLevel();
//		List<Player> players = EntityUtil.getNearbyPlayers(entity.getEntity(), 40, 40, 40);
//		if (players.isEmpty()) {
//			//Nearest Player
//			Player player = null;
//			double distance = 0;
//			for (Player pl : Bukkit.getOnlinePlayers()) {
//				if (pl.getWorld() == entity.getEntity().getWorld()) {
//					double dst = entity.getEntity().getLocation().distance(pl.getLocation());
//					if (dst >= distance) {
//						distance = dst;
//						player = pl;
//					}
//				}
//			}
//			if (player != null) {
//				players.add(player);
//			}
//		}
//		int count = 0;
//		int sum = 0;
//		for (Player player : players) {
//			PlayerTrainer trainer = handler.getTrainer(player);
//			if (trainer.getParty()[0] != null) {
//				sum += trainer.getParty()[0].getLevel();
//				count++;
//			}
//		}
		int trainerLevel = TrainerUtil.averageTrainerLevel(entity.getEntity(), handler);
		level = trainerLevel == -1 ? level : (byte) Math.round((level +trainerLevel*3)/(4));
		return level;
	}
	
	public static float getStatMultiplier (int level) {
		float multiplier = level < 0 ? 2.0f/(2-level) : (2+level)/2.0f;
		return multiplier;
	}
	
	public static float getAccuracyMultiplier (int level) {
		float multiplier = level < 0 ? 3.0f/(3-level) : (3+level)/3.0f;
		return multiplier;
	}
	
	public static int calcPrizeMoney (Trainer trainer) {
		return calcPrizeMoney(trainer.getLevel(), trainer.getRank().getBasePrizeMoney());
	}
	
	public static int calcPrizeMoney (byte level, int base) {
		return level * base;
	}

}
