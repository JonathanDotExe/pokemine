package at.jojokobi.pokemine.items;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.battle.Battle;
import at.jojokobi.pokemine.battle.PokemonWrapper;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.entity.PokemonCriteria;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;
import at.jojokobi.pokemine.trainer.Trainer;

public abstract class AbstractPokeball extends BattleItem {
	
	private double catchRate = 1;

	
	public AbstractPokeball(String namespace, String identifier, PokeminePlugin plugin) {
		super(namespace, identifier, plugin);
		setHideFlags(true);
		setMaxStackSize(16);
	}
	
	public double getCatchRate() {
		return catchRate;
	}

	protected void setCatchRate(double catchRate) {
		this.catchRate = catchRate;
	}
	
	public void catchPokemonAnimation (PokemonEntity entity, Trainer trainer) {
		entity.getEntity().getEquipment().setHelmet(createItem());
		entity.getEntity().setVelocity(new Vector());
		Bukkit.getScheduler().runTaskLater(getPlugin(), new Runnable() {
			@Override
			public void run() {
				entity.getEntity().getEquipment().setHelmet(entity.getPokemon().toItemStack());
				catchPokemon(entity, trainer);
			}
		}, 40L);
	}
	
	protected boolean catchPokemon (PokemonEntity entity, Trainer trainer) {
		boolean caught = false;
		if  (entity != null) {
			Pokemon pokemon = entity.getPokemon();
			if (pokemon.isWild()) {
				if (trainer.getLevel() >= pokemon.getLevel() - 5 || pokemon.isShiny() || pokemon.getSpecies().isLegendary()) {
					int catchRate = pokemon.getSpecies().getCatchRate();
					int health = pokemon.getHealth();
					int maxHealth = pokemon.getMaxHealth();
					double x = ((3.0 * maxHealth - 2.0 * health) * catchRate * this.catchRate)/(3.0*maxHealth);
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
	
	@Override
	public void onHit(ItemStack item, Entity damager, Entity defender) {
		
	}
	
//	public void catchPokemonAnimation (PokemonEntity entity, PlayerTrainer trainer) {
//		Location place = entity.getEntity().getLocation();
//		entity.delete();
//		ArmorStand pokeballStand = PokemonUtil.pokeballStand(place, this);
//		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
//			@Override
//			public void run() {
//				pokeballStand.remove();
//				entity.respawn(place);
//				PokemonUtil.catchPokemon(entity, trainer);
//			}
//		}, 40L);
//	}
	
	@Override
	public void onTrainerUse(ItemStack held, Trainer trainer, Player player) {
		Snowball ball = player.launchProjectile(Snowball.class);
		ball.getVelocity().multiply(5);
		ItemStack ballItem = held.clone();
		ballItem.setAmount(1);
		Item pokeball = player.getWorld().dropItemNaturally(ball.getLocation(), ballItem);
		if (player.getGameMode() != GameMode.CREATIVE) {
			held.setAmount(held.getAmount() - 1);
		}
		pokeball.setItemStack(ballItem);
		ball.addPassenger(pokeball);
	}
	
//	@EventHandler
//	public void onPlayerInteract (PlayerInteractEvent event) {
//		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
//			Player player = event.getPlayer();
//			ItemStack held = player.getInventory().getItemInMainHand();
//			if (held != null && isItem(held)) {
//				Snowball ball = player.launchProjectile(Snowball.class);
//				ball.getVelocity().multiply(5);
//				ItemStack ballItem = held.clone();
//				ballItem.setAmount(1);
//				Item pokeball = player.getWorld().dropItemNaturally(ball.getLocation(), ballItem);
//				if (player.getGameMode() != GameMode.CREATIVE) {
//					held.setAmount(held.getAmount() - 1);
//				}
//				pokeball.setItemStack(ballItem);
//				ball.addPassenger(pokeball);
//			}
//		}
//	}
	
	@EventHandler
	public void onProjectileHit (ProjectileHitEvent event) {
		if (event.getEntity() instanceof Snowball) {
			Snowball ball = (Snowball) event.getEntity();
			if (ball.getShooter() instanceof Player) {
				Item pokeballItem = null;
				Player player = (Player) ball.getShooter();
				List<Entity> passengers = ball.getPassengers();
				for (int i = 0; i < passengers.size() && pokeballItem == null; i++) {
					if (passengers.get(i) instanceof Item) {
						Item item = (Item) passengers.get(i);
						if (isItem(item.getItemStack())) {
							pokeballItem = item;
						}
					}
				}
				if (pokeballItem != null) {
					if (event.getHitEntity() != null && event.getHitEntity() instanceof ArmorStand) {
						ArmorStand stand = (ArmorStand) event.getHitEntity();
						PokemonEntity pokemon = getPlugin().getEntityHandler().getCustomEntityForEntity(stand, PokemonEntity.class);
						if (pokemon != null) {
							Trainer trainer = getPlugin().getPlayerTrainerHandler().getTrainer(player);
							catchPokemonAnimation(pokemon, trainer);
							pokeballItem.remove();
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onArmorStandManipulate (PlayerArmorStandManipulateEvent event) {
		ItemStack item = event.getArmorStandItem();
		if (item != null && isItem(item)) {
			event.setCancelled(true);
		}
	}

	@Override
	public void startUse(PokemonWrapper user, PokemonWrapper defender, Battle battle) {
		PokemonEntity entity = getPlugin().getEntityHandler().getEntity(new PokemonCriteria(defender.getPokemon()));
		if (entity != null) {
			entity.getEntity().getEquipment().setHelmet(createItem());
		}
	}
	
	@Override
	public void endUse(PokemonWrapper user, PokemonWrapper defender, Battle battle) {
		PokemonEntity entity = getPlugin().getEntityHandler().getEntity(new PokemonCriteria(defender.getPokemon()));
		if (entity != null) {
			entity.getEntity().getEquipment().setHelmet(defender.getPokemon().toItemStack());
		}
		if (catchPokemon(entity, user.getPokemon().getOwner())) {
			battle.sendBattleMessage("Gotcha!");
			battle.giveEpToPokemon(defender.getPokemon());
			battle.end();
		}
		else {
			battle.sendBattleMessage("Oh no the wild Pokemon broke free!");
		}

	}
	
}
