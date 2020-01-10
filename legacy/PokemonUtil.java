package at.jojokobi.pokemine.pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.mcutil.gui.InventoryGUIHandler;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.BattleAction;
import at.jojokobi.pokemine.battle.MoveAction;
import at.jojokobi.pokemine.battle.PokemonContainer;
import at.jojokobi.pokemine.battle.RunAction;
import at.jojokobi.pokemine.gui.MoveLearnGUI;
import at.jojokobi.pokemine.items.AbstractPokeball;
import at.jojokobi.pokemine.moves.Move;
import at.jojokobi.pokemine.moves.MoveInstance;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;
import at.jojokobi.pokemine.trainer.PlayerTrainer;
import at.jojokobi.pokemine.trainer.Trainer;

@Deprecated
public final class PokemonUtil {
	
	public static final Material POKEMON_MATERIAL = Material.DIAMOND_SHOVEL;
	public static final String EVOLUTION_MUSIC = "pokemon.evolution";
	
	private PokemonUtil() {
		
	}
	
	
	/**
	 * 
	 * Use {@link Pokemon#toItemStack()}
	 * 
	 * @param pokemon
	 * @return
	 */
	@Deprecated
	public static ItemStack itemFromPokemon (Pokemon pokemon) {
		ItemStack item  = new ItemStack(POKEMON_MATERIAL);
		ItemMeta meta = item.getItemMeta();
		if (meta instanceof Damageable) {
			((Damageable) meta).setDamage((short) pokemon.getSpecies().getIndex());
		}
		meta.setUnbreakable(true);
		meta.setDisplayName(pokemon.getName());
		List<String> lore = new ArrayList<String>();
		lore.add("Level " + pokemon.getLevel());
		lore.add("Experience " + pokemon.getEp() + "/" + pokemon.getSpecies().getLevelEP().getEpToLevelUp(pokemon.getLevel()));
		lore.add("Status " + (pokemon.getPrimStatChange() != null ? pokemon.getPrimStatChange().getName() : "OK"));
		lore.add("HP " + pokemon.getHealth() + "/" + pokemon.getMaxHealth());
		lore.add("Attack " + pokemon.getAttack() + " (" + pokemon.getAttackLevel() + ")");
		lore.add("Defense " + pokemon.getDefense() + " (" + pokemon.getDefenseLevel() + ")");
		lore.add("Special Attack " + pokemon.getSpecialAttack() + " (" + pokemon.getSpecialAttackLevel() + ")");
		lore.add("Special Defense " + pokemon.getSpecialDefense() + " (" + pokemon.getSpecialDefenseLevel() + ")");
		lore.add("Speed " + pokemon.getSpeed() + " (" + pokemon.getSpeedLevel() + ")");
		lore.add("Friendship " + pokemon.getFriendship());
		lore.add("Distance " + pokemon.getWalkedDistance());
		lore.add("");
		lore.add("Moves:");
		for (MoveInstance move : pokemon.getMoves()) {
			if (move != null) {
				lore.add(move.toString());
			}
		}
		lore.add("");
		lore.add("DVs: " + Math.round(pokemon.getDVPercent() * 10000)/100);
		lore.add("CP: " + pokemon.getCP());
		lore.add("Trainer: " + pokemon.getOwner().getName());
		if (pokemon.isShiny()) {
			meta.addEnchant(Enchantment.LUCK, 1, false);
		}
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	/**
	 * 
	 * @param species
	 * @return
	 */
	@Deprecated
	public static ItemStack itemFromSpecies (PokemonSpecies species) {
		ItemStack item  = new ItemStack(POKEMON_MATERIAL);
		ItemMeta meta = item.getItemMeta();
		if (meta instanceof Damageable) {
			((Damageable) meta).setDamage((short) species.getIndex());
		}
		meta.setUnbreakable(true);
		meta.setDisplayName(species.getIndex() + ". " + species.getName());
		List<String> lore = new ArrayList<String>();
		//Types to String
		StringBuilder types = new StringBuilder();
		for (int i = 0; i < species.getTypes().length; i++) {
			types.append(species.getTypes()[i].getColor() + species.getTypes()[i].toString());
			if (i < species.getTypes().length - 1) {
				types.append("/");
			}
		}
		
		lore.add("Size: " + species.getSize());
		lore.add("Weight: " + species.getWeight());
		lore.add(species.getDescription());
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	@Deprecated
	public static void learnMoves (Pokemon poke, List<Move> moves, InventoryGUIHandler handler) {
		MoveLearnGUI lastgui = null;
		for (Move move : moves) {
			if (!poke.learnMove(move) && poke.getOwner() instanceof PlayerTrainer) {
				MoveLearnGUI gui = new MoveLearnGUI(((PlayerTrainer) poke.getOwner()).getPlayer(), poke, move);
				if (lastgui != null) {
					lastgui.setNext(gui);
				}
				else {
					handler.addGUI(gui);
					gui.show();
				}
				lastgui = gui;
			}
		}
	}
	
	/**
	 * 
	 * Use {@link Pokemon#getOppent(PokemonContainer[])};
	 * 
	 * @param opponents
	 * @param attacker
	 * @return
	 */
	@Deprecated
	public static PokemonContainer getOppent (PokemonContainer[] opponents, Pokemon attacker) {
		PokemonContainer opponent = null;
		for (int i = 0; i < opponents.length && opponent == null; i++) {
			if (opponents[i].getPokemon() != attacker) {
				opponent = opponents[i];
			}
		}
		return opponent;
	}
	
	
	/**
	 * 
	 * Use {@link Pokemon#chooseRandomAction(Battle)} (internal only);
	 * 
	 * @param opponents
	 * @param attacker
	 * @return
	 */
	@Deprecated
	public static BattleAction chooseRandomAction(Battle battle, Pokemon pokemon) {
		List<MoveInstance> cleanMoves = new ArrayList<>(Arrays.asList(pokemon.getMoves()));
		cleanMoves.removeAll(Collections.singleton(null));
		Random random = new Random();
		return cleanMoves.isEmpty() ? new RunAction(battle, pokemon, getOppent(battle.getPokemonContainers(), pokemon)) : new MoveAction(battle,cleanMoves.get(random.nextInt(cleanMoves.size())), pokemon, getOppent(battle.getPokemonContainers(), pokemon));
	}
	
	@Deprecated
	public static void trade (Pokemon pokemon1, Pokemon pokemon2, PokemonHandler handler, InventoryGUIHandler guiHandler) {
		Trainer owner1 = pokemon1.getOwner();
		Trainer owner2 = pokemon2.getOwner();
		owner1.removePokemon(pokemon1);
		owner2.removePokemon(pokemon2);
		owner2.givePokemon(pokemon1);
		owner1.givePokemon(pokemon2);
		List<Move> moves1 = pokemon1.evolve(handler, EvolutionCause.NO_ITEM, true);
		List<Move> moves2 = pokemon2.evolve(handler, EvolutionCause.NO_ITEM, true);
		if (moves1 != null) {
			learnMoves(pokemon1, moves1, guiHandler);
		}
		if (moves2 != null) {
			learnMoves(pokemon2, moves2, guiHandler);
		}
	}
	
	/**
	 * 
	 * Use {@link PokemonEntityHandler#switchPokemon(Pokemon, Pokemon)}
	 * 
	 * @param handler
	 * @param oldPoke
	 * @param newPoke
	 */
	@Deprecated
	public static void switchPokemon (EntityHandler handler, Pokemon oldPoke, Pokemon newPoke) {
//		Location place = null;
//		PokemonEntity entity = handler.getEntityForPokemon(oldPoke);
//		if (entity == null) {
//			place = newPoke.getOwner() instanceof PlayerTrainer ? ((PlayerTrainer) newPoke.getOwner()).getPlayer().getLocation() : null;
//		}
//		else {
//			place = entity.getEntity().getLocation();
//			entity.delete();
//		}
//		handler.addEntity(new PokemonEntity(place, newPoke, handler, handler.getPlugin()));
	}
	
//	public static ArmorStand pokeballStand (Location place, AbstractPokeball ball) {
//		ArmorStand stand = (ArmorStand) place.getWorld().spawnEntity(place, EntityType.ARMOR_STAND);
//		stand.setVisible(false);
//		ItemStack item = ball.getItem();
//		stand.setHelmet(item);
//		stand.setCanPickupItems(true);
//		return stand;
//	}
	
	/**
	 * 
	 * Use {@link AbstractPokeball#catchPokemon(PokemonEntity, Trainer)}
	 * 
	 * @param entity
	 * @param trainer
	 * @param pokeball
	 * @return
	 */
	@Deprecated
	public static boolean catchPokemon (PokemonEntity entity, Trainer trainer, double pokeball) {
		boolean caught = false;
		if  (entity != null) {
			Pokemon pokemon = entity.getPokemon();
			if (pokemon.isWild()) {
				if (trainer.getLevel() >= pokemon.getLevel() - 5 || pokemon.isShiny() || pokemon.getSpecies().isLegendary()) {
					int catchRate = pokemon.getSpecies().getCatchRate();
					int health = pokemon.getHealth();
					int maxHealth = pokemon.getMaxHealth();
					double x = ((3.0 * maxHealth - 2.0 * health) * catchRate * pokeball)/(3.0*maxHealth);
					double chance = x/255.0;
					double val = Math.random();
					if (val < chance) {
						entity.spawnDrops();
						entity.delete();
						trainer.givePokemon(pokemon);
						trainer.message("You caught " + pokemon.getName() + " on level " + pokemon.getLevel());
						caught = true;
					}
					else {
						trainer.message("The wild Pokemon broke free!");
					}
				}
				else {
					trainer.message("You are to weak to capture this pokémon!");
				}
			}
			else {
				trainer.message("You can't catch that Pokemon! It already belongs to somebody!");
			}
		}
		return caught;
	}
	
}
