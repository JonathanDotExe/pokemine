package at.jojokobi.pokemine.trainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.mcutil.entity.EntityUtil;
import at.jojokobi.mcutil.general.ArrayUtil;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.PokemonSpecies;

public final class TrainerUtil {

	public static final Material TRAINER_MATERIAL = Material.DIAMOND_SWORD;
	
	private TrainerUtil() {
		
	}
	
	public static Pokemon[] generateParty (byte level, Trainer trainer, PokemonHandler handler, int count, Random random) {
		TrainerRank rank = trainer.getRank();
		Pokemon[] party = new Pokemon[Trainer.PARTY_SIZE];
		List<PokemonSpecies> pokemon = getUsablePokemon(handler, rank);
		for (int i = 0; i < count; i++) {
			Pokemon poke = new Pokemon(pokemon.get(random.nextInt(pokemon.size())), level);
			party[i] = poke;
		}
		return party;
	}
	
	public static Pokemon[] generateParty(byte level, Trainer trainer, PokemonHandler handler) {
		Random random = new Random();
		return generateParty(level, trainer, handler, random.nextInt(Trainer.PARTY_SIZE - 1) + 1, random);
	}
	
	public static List<PokemonSpecies> getUsablePokemon (PokemonHandler handler, TrainerRank rank) {
		List<PokemonSpecies> pokemon = new ArrayList<>();
		for (PokemonSpecies species : handler.getItemList()) {
			if ((rank.getUsedPokemon().length <= 0 || ArrayUtil.arrayContains(rank.getUsedPokemon(), species)) && (rank.getTypes().length <= 0 || Arrays.stream(rank.getTypes()).anyMatch((type) -> species.hasType(type)))) {
				pokemon.add(species);
			}
		}
		if (pokemon.isEmpty()) {
			pokemon.addAll(handler.getItemList());
		}
		return pokemon;
	}
	
	public static ItemStack itemFromNPC (int textureID, String name) {
		ItemStack item  = new ItemStack(TRAINER_MATERIAL);
		ItemMeta meta = item.getItemMeta();
		meta.setUnbreakable(true);
		meta.setDisplayName(name);
		meta.setCustomModelData(textureID);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack itemFromTrainer (Trainer trainer) {
		ItemStack item  = itemFromNPC(trainer.getRank().getId(), trainer.getName());
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("Level " + trainer.getLevel());
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static byte averageTrainerLevel (Entity entity, PlayerTrainerHandler handler) {
		List<Player> players = EntityUtil.getNearbyPlayers(entity, 40, 40, 40);
		if (players.isEmpty()) {
			//Nearest Player
			Player player = null;
			double distance = 0;
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.getWorld() == entity.getWorld()) {
					double dst = entity.getLocation().distance(pl.getLocation());
					if (dst >= distance) {
						distance = dst;
						player = pl;
					}
				}
			}
			if (player != null) {
				players.add(player);
			}
		}
		int count = 0;
		int sum = 0;
		for (Player player : players) {
			Trainer trainer = handler.getTrainer(player);
			if (trainer.getParty().size() > 0) {
				sum += trainer.getLevel();
				count++;
			}
		}
		return count == 0 ? -1 : (byte) (sum/count);
	}

}
