package at.jojokobi.pokemine.pokemon.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.entity.Attacker;
import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.mcutil.entity.EntityMapData;
import at.jojokobi.mcutil.entity.Ownable;
import at.jojokobi.mcutil.entity.ai.AttackTask;
import at.jojokobi.mcutil.entity.ai.FollowOwnerTask;
import at.jojokobi.mcutil.entity.ai.RandomTask;
import at.jojokobi.mcutil.entity.ai.ReturnToSpawnTask;
import at.jojokobi.mcutil.entity.ai.RidingTask;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.battle.animation.BattleAnimation;
import at.jojokobi.pokemine.moves.MoveHandler;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.trainer.PlayerTrainerHandler;
import at.jojokobi.pokemine.trainer.WildPokemonTrainer;

public class PokemonEntity extends CustomEntity<ArmorStand> implements Attacker, Ownable{

	
	public static final String POKEMON_KEY = "pokemon";
	public static final String BEHAVIOR_TYPE_KEY = "behaviorType";
	
	public static final int LIFETIME = 6000;
	public static final String SOUND_NAMESPACE = "pokemine";
	public static final String SOUND_AMBIENT = "ambient";
	public static final String SOUND_CATHEGORY = "pokemon";

	private Pokemon pokemon;
	private PokemonBehaviorType behaviorType = PokemonBehaviorType.NORMAL_POKEMON;

	public PokemonEntity(Location place, Pokemon pokemon, EntityHandler handler) {
		super(place, handler, PokemonEntityType.getInstance());
		this.pokemon = pokemon;
		setDespawnTicks(LIFETIME);
		addComponent(new FriendshipComponent());
		
//		if (pokemon != null && pokemon.isWild()) {
//			setAi(WildPokemonAI.INSTANCE);
//		} else {
//			setAi(TrainerPokemonAI.INSTANCE);
//		}
		
//		spawn();
//		if (handler.getEntityForPokemon(pokemon) != null) {
//			setDespawnTicks(0);
//		}
	}
	
	@Override
	protected void spawn() {
		super.spawn();
//		if (getAi() == null) {
//			if (pokemon.getSpecies().isLegendary()) {
//				setAi(StationaryPokemonAI.INSTANCE);
//			}
//			else {
//				setAi(AggressiveStationaryAI.INSTANCE);
//			}
//		}
		switch (behaviorType) {
		case NORMAL_POKEMON:
			addEntityTask(new RidingTask());
			addEntityTask(new AttackTask(e -> e instanceof Monster && !(e instanceof Creeper) && !(e instanceof PigZombie), 4));
			addEntityTask(new PlayWithFriendsTask(new FriendshipChecker() {
				@Override
				public boolean likes(CustomEntity<?> entity, CustomEntity<?> other) {
					return entity instanceof PokemonEntity && other instanceof PokemonEntity && !JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().isBattling(((PokemonEntity) entity).getPokemon()) && !JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().isBattling(((PokemonEntity) other).getPokemon()) && ((PokemonEntity) entity).getPokemon().likes(((PokemonEntity) other).getPokemon());
				}
				
				@Override
				public boolean dislikes(CustomEntity<?> entity, CustomEntity<?> other) {
					return entity instanceof PokemonEntity && other instanceof PokemonEntity && !JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().isBattling(((PokemonEntity) entity).getPokemon()) && !JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().isBattling(((PokemonEntity) other).getPokemon()) && ((PokemonEntity) entity).getPokemon().dislikes(((PokemonEntity) other).getPokemon());
				}
			}));
			addEntityTask(new FollowOwnerTask());
			addEntityTask(new RandomTask());
			break;
		case STATIONARY_AGGRESSIVE_POKEMON:
			addEntityTask(new AttackTask(e -> e instanceof Player, 10));
			addEntityTask(new ReturnToSpawnTask());
			break;
		case STATIONARY_POKEMON:
			addEntityTask(new ReturnToSpawnTask());
			break;
		case PLACED_POKEMON:
			addEntityTask(new AttackTask(e -> e instanceof Monster && !(e instanceof Creeper) && !(e instanceof PigZombie), 4));
			addEntityTask(new PlayWithFriendsTask(new FriendshipChecker() {
				@Override
				public boolean likes(CustomEntity<?> entity, CustomEntity<?> other) {
					return entity instanceof PokemonEntity && other instanceof PokemonEntity && !JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().isBattling(((PokemonEntity) entity).getPokemon()) && !JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().isBattling(((PokemonEntity) other).getPokemon()) && ((PokemonEntity) entity).getPokemon().likes(((PokemonEntity) other).getPokemon());
				}
				
				@Override
				public boolean dislikes(CustomEntity<?> entity, CustomEntity<?> other) {
					return entity instanceof PokemonEntity && other instanceof PokemonEntity && !JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().isBattling(((PokemonEntity) entity).getPokemon()) && !JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().isBattling(((PokemonEntity) other).getPokemon()) && ((PokemonEntity) entity).getPokemon().dislikes(((PokemonEntity) other).getPokemon());
				}
			}));
			addEntityTask(new ReturnToSpawnTask());
			break;
		}
	}

	@Override
	public boolean canSpawn() {
		return getHandler().getEntity(new PokemonCriteria(pokemon)) == null;
	}

	@Override
	protected ArmorStand createEntity(Location place) {
		ArmorStand stand = (ArmorStand) place.getWorld().spawnEntity(place, EntityType.ARMOR_STAND);
		stand.setVisible(false);
		stand.setHelmet(pokemon.toItemStack());
		stand.setCustomName(getArmorStandName());
		stand.setCustomNameVisible(true);
		stand.setCanPickupItems(false);
		return stand;
	}
	
	public void switchPokemon (Pokemon newPokemon) {
		PokemonEntity entity = new PokemonEntity(getEntity().getLocation(), newPokemon, getHandler());
		entity.setSave(isSave());
		entity.setDespawnTicks(getDespawnTicks());
		getHandler().addEntity(entity);
		delete();
	}

	public void loop() {
		super.loop();
		if (getEntity().isVisible()) {
			getEntity().setVisible(false);
		}

		// Apply AI
//		if (getTask() == null) {
//			if (getEntity().getPassengers().size() > 0) {
//				setTask(new RidingTask());
//			} else if (pokemon.getOwner() instanceof PlayerTrainer) {
//				setTask(new FollowTask(((PlayerTrainer) pokemon.getOwner()).getPlayer()));
//			} else {
//				setTask(new RandomTask());
//			}
//		}
//		if (!(getTask() instanceof AttackTask) && !(getTask() instanceof RidingTask)) {
//			List<Entity> nearby = getEntity().getNearbyEntities(10, 10, 10);
//			Monster attack = null;
//			for (Entity entity : nearby) {
//				if (entity instanceof Monster && !(entity instanceof Creeper) && !(entity instanceof PigZombie)
//						&& (((Monster) entity).getTarget() != null || !pokemon.isWild())) {
//					attack = (Monster) entity;
//				}
//			}
//			if (attack != null) {
//				setTask(new AttackTask(attack));
//			}
//		}
		// Stat Particles
		if (pokemon.getPrimStatChange() != null) {
			generateParticle(pokemon.getPrimStatChange().getParticle());
		}
		//TODO: Ersatz finden
//		pokemon.getStatChanges().stream().forEach((stat) -> generateParticle(stat.getParticle()));
		// Sound
		if (Math.random() < 0.025) {
			playAmbientSound();
		}
//		
//		if (!pokemon.isBattling()) {
//			move();
//		}
		if (canDespawn() && (pokemon.getHealth() <= 0 || (!pokemon.isWild() && (pokemon.getOwner().getEntity() == null || !pokemon.getOwner().getEntity().isValid())))) {
			delete();
		}
		// Name
		getEntity().setCustomName(getArmorStandName());
	}

	@Override
	protected boolean canDespawn() {
		return (!JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().isBattling(pokemon) && getEntity().getPassengers().size() <= 0);
	}

	private void generateParticle(Particle particle) {
		if (particle != null) {
			Location place = getEntity().getLocation();
			getEntity().getWorld().spawnParticle(particle, place.getX() + Math.random() * 2 - 1,
					place.getY() + Math.random() * 2, place.getZ() + Math.random() * 2 - 1, 3);
		}
	}

	@Override
	protected void onGetMounted(EntityMountEvent event) {
		super.onGetMounted(event);
	}

	@Override
	protected void onGetDismounted(EntityDismountEvent event) {
		super.onGetDismounted(event);
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (!player.isSneaking()) {
				if (getEntity().getLocation().getBlock().getType() == Material.WATER) {
//					getEntity().addPassenger(player);
					event.setCancelled(true);
				}
			}
		}
	}
	
	@Override
	protected void onInteract(PlayerInteractEntityEvent event) {
		super.onInteract(event);
		
		if (JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getPlayerTrainerHandler().getTrainer(event.getPlayer()) == pokemon.getOwner()){
			Player player = event.getPlayer();
			if (player.isSneaking() && !JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().isBattling(pokemon)) {
				pokemon.getOwner().message("Come back " + pokemon.getName() + "! You did enough!");
				if (pokemon.getOwner().getPlacedPokemon().contains(pokemon)) {
					pokemon.getOwner().moveToPc(pokemon);
				}
				delete();
			}
			else {
				getEntity().addPassenger(player);
				pokemon.getOwner().message("Let's take a walk! "+ pokemon.getName() + "!");
			}
		}
		event.setCancelled(true);
	}
//	
//	private void move () {
//		if (stand.getPassengers().size() > 0) {
//			Entity passenger = stand.getPassengers().get(0);
//			moveToGoal(passenger.getLocation().getDirection());
//		}
//		else if (!pokemon.isWild()) {
//			if (pokemon.getOwner() instanceof PlayerTrainer) {
//				Player player = ((PlayerTrainer) pokemon.getOwner()).getPlayer();
//				Vector velocity = player.getLocation().toVector().subtract(stand.getLocation().toVector());
//				if (velocity.distance(new Vector()) > 4) {
//					moveToGoal(velocity);
//				}
//			}
//		}
//		else {
//			moveToGoal(goal.clone());
//		}
//	}

//	private void moveToGoal(Vector velocity) {
//		Location place = stand.getLocation();
//		velocity.normalize();
//		if (pokemon.isWild()) {
//			velocity.multiply(0.5);
//		}
//		NMSEntityUtil.rotateVehicle(stand, velocity);
//		if (pokemon.getSpecies().canFly()) {
//			velocity.multiply(pokemon.getSpecies().getFlySpeed() * getFlySpeedModifier(velocity));
//			if (stand.getPassengers().size() > 0 && stand.getPassengers().get(0) instanceof Player) {
//				Player player = (Player) stand.getPassengers().get(0);
//				player.playSound(player.getLocation(), Sound.ITEM_ELYTRA_FLYING, 1, 1);
//			}
//		}
//		else {
//			if ((place.getBlock().getType() == Material.WATER || place.getBlock().getType() == Material.STATIONARY_WATER) && pokemon.getSpecies().canSurf()) {
//				velocity.multiply(pokemon.getSpecies().getSurfSpeed());
//				velocity.setY(0.2);
//			}
//			else {
//				velocity.multiply(pokemon.getSpecies().getWalkSpeed());
//				if (place.clone().add(0, -0.1, 0).getBlock().getType().isSolid() && (place.clone().add(velocity.getX(), 0, velocity.getZ()).getBlock().getType().isSolid() && (place.clone().add(velocity.getX(), 0, velocity.getZ()).getBlock().getType().isSolid() || place.clone().add(velocity.getX(), 0, 0).getBlock().getType().isSolid() || place.clone().add(0, 0, velocity.getZ()).getBlock().getType().isSolid()))) {
//					velocity.setY(0.5);
//				}
//				else {
//					velocity.setY(stand.getVelocity().getY());
//				}
//			}
//		}
//		try {
//			stand.setVelocity(velocity);
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private float getFlySpeedModifier (Vector velocity) {
//		float modifier = 1;
//		float pitch = new Location(null, 0, 0, 0).setDirection(velocity).getPitch();
//		if (pitch > -15) {
//			modifier = 1.5f;
//		}
//		else if (pitch > -35) {
//			modifier = 1.2f;
//		}
//		else if (pitch < -50) {
//			modifier = 0.8f;
//		}
//		return modifier;
//	}

	private void playAmbientSound() {
		getEntity().getWorld().playSound(getEntity().getLocation(),
						SOUND_CATHEGORY + "." + pokemon.getSpecies().getIdentifier() + "." + SOUND_AMBIENT, 0.5f, 1);
	}

	@Override
	public void delete() {
		if (getEntity() != null) {
			getEntity().eject();
			if (getEntity().getVehicle() != null) {
				getEntity().getVehicle().eject();
			}
		}
		// Loot
		if (pokemon.isWild() && pokemon.getHealth() <= 0) {
			spawnDrops();
		}

		super.delete();
		if (!pokemon.isWild()) {
			pokemon.getOwner().message("Your " + pokemon.getName() + " returned!");
		}
	}

	public void spawnDrops() {
		if (pokemon.getSpecies().getLoot() != null) {
			for (ItemStack item : pokemon.getSpecies().getLoot().populateLoot(new Random(), null)) {
				if (item.getType() != Material.AIR) {
					getEntity().getLocation().getWorld().dropItem(getEntity().getLocation(), item);
				}
			}
		}
	}

//	public void respawn (Location place) {
//		stand = (ArmorStand) place.getWorld().spawnEntity(place, EntityType.ARMOR_STAND);
//		createArmorStand();
//		if (!handler.getPkmnEntities().contains(this)) {
//			handler.addPokemonEntity(this);
//		}
//	}
//	
//	private void createArmorStand () {
//		stand.setVisible(false);
//		stand.setHelmet(PokemonUtil.itemFromPokemon(pokemon));
//		stand.setCustomName(getArmorStandName());
//		stand.setCustomNameVisible(true);
//		stand.setCanPickupItems(false);
//	}

	@Override
	protected void onDamage(EntityDamageEvent e) {
		super.onDamage(e);
		if (!JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().isBattling(pokemon)) {
			if (e instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
				//Ride Hat
				if (getPokemon().getOwner() != null && event.getDamager() == getPokemon().getOwner().getEntity()) {
					if (getEntity().getVehicle() != null) {		
						getEntity().getVehicle().eject();
						getPokemon().getOwner().message("Your " + getPokemon().getName() + " left your head!");
					}
					else {
						event.getDamager().addPassenger(getEntity());
						getPokemon().getOwner().message("Your " + getPokemon().getName() + " is now sitting on your head!");
					}
				}
				else {
					getPokemon().takeMobDamage(event.getDamage());
				}
			} else if (e.getCause() != DamageCause.FALL && (canLavaSwim() && e.getCause() != DamageCause.LAVA
					&& e.getCause() != DamageCause.FIRE && e.getCause() != DamageCause.FIRE_TICK)) {
				getPokemon().takeMobDamage(e.getDamage());
			}
		}
		e.setCancelled(true);
	}

	@Override
	public void attack(Damageable entity) {
		if (entity instanceof Mob) {
			((Mob) entity).setTarget(null);
		}
		double damage = pokemon.getMobDamage();
		entity.damage(damage, getEntity());
		if (getPokemon().getSelectedTerrainMove() != null) {
			JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getAnimationHandler().addAnimation(BattleAnimation
					.stringToAnimation(getPokemon().getSelectedTerrainMove().getAnimation(), getEntity(), entity));
		}
//		getEntity().damage(0, entity);
	}

	@Override
	public int getAttackDelay() {
		return pokemon.getMobAttackDelay();
	}

	private String getArmorStandName() {
		// Name
		StringBuilder name = new StringBuilder();
		name.append(pokemon.toString());
		name.append("[" + (pokemon.getPrimStatChange() != null ? pokemon.getPrimStatChange().getName() : "OK") + "]");
		name.append(" - ");
		// Color
		int health = Math.round(pokemon.getHealthInPercent() * 20);
		if (health < 5) {
			name.append(ChatColor.RED);
		} else if (health < 10) {
			name.append(ChatColor.YELLOW);
		} else {
			name.append(ChatColor.GREEN);
		}
		// Health
		for (int i = 0; i < health; i++) {
			name.append("I");
		}
		return name.toString();
	}

	@Override
	protected boolean canWalk() {
		return pokemon.getSpecies().canWalk();
	}

	@Override
	protected boolean canFly() {
		return pokemon.getSpecies().canFly()/*
											 * && (getEntity().getPassengers().isEmpty() ||
											 * pokemon.getOwner().getBadge(PokemonType.FLYING) >= 30)
											 */;
	}

	@Override
	protected boolean canSwim() {
		return pokemon.getSpecies().canSurf()/*
												 * && (getEntity().getPassengers().isEmpty() ||
												 * pokemon.getOwner().getBadge(PokemonType.WATER) >= 15)
												 */;
	}

	@Override
	protected boolean canLavaSwim() {
		return pokemon.getSpecies().canLavaSurf()/*
													 * && (getEntity().getPassengers().isEmpty() ||
													 * pokemon.getOwner().getBadge(PokemonType.FIRE) >= 25)
													 */;
	}

	@Override
	protected boolean canJump() {
		return pokemon.getSpecies().canJump();
	}

	@Override
	protected boolean canClimb() {
		return pokemon.getSpecies().canClimb()/*
												 * && (getEntity().getPassengers().isEmpty() ||
												 * pokemon.getOwner().getBadge(PokemonType.NORMAL) >= 10)
												 */;
	}

	@Override
	protected boolean canMove() {
		return !JavaPlugin.getPlugin(PokeminePlugin.class)/*TODO bad design*/.getBattleHandler().isBattling(pokemon);
	}

//	@Override
//	protected double getGeneralSpeedMultiplier() {
//		return pokemon.isWild() ? 0.2 : 1;
//	}
	
	@Override
	protected double getWalkSpeed() {
		return Math.min(getSprintSpeed(), 0.35);
	}

	@Override
	protected double getSprintSpeed() {
		return pokemon.getSpecies().getWalkSpeed();
	}

	@Override
	protected double getSwimSpeed() {
		return pokemon.getSpecies().getSurfSpeed();
	}

	@Override
	protected double getFlySpeed() {
		return pokemon.getSpecies().getFlySpeed();
	}

	@Override
	protected double getLavaSwimSpeed() {
		return pokemon.getSpecies().getLavaSurfSpeed();
	}

	@Override
	protected double getJumpSpeed() {
		return pokemon.getSpecies().getJumpSpeed();
	}

	@Override
	protected double getClimbSpeed() {
		return pokemon.getSpecies().getClimbSpeed();
	}

	public Pokemon getPokemon() {
		return pokemon;
	}
	
	@Override
	public void setSave(boolean save) {
		if (pokemon != null && !pokemon.isWild()) {
			save = false;
		}
		super.setSave(save);
	}
	
	@Override
	public void legacySaveData(Element element, Document document) {
		super.legacySaveData(element, document);
		Element pokemonElement = document.createElement(PlayerTrainerHandler.POKEMON_ELEMENT);
		PlayerTrainerHandler.savePokemon(pokemon, document, pokemonElement);
		element.appendChild(pokemonElement);
	}
	
	@Override
	public void legacyParseData(Element element) {
		super.legacyParseData(element);
		setDespawnTicks(-1);
		Node pokemonNode = element.getElementsByTagName(PlayerTrainerHandler.POKEMON_ELEMENT).item(0);
		if (pokemonNode != null && pokemonNode.getNodeType() == Node.ELEMENT_NODE) {
			Element pokemonElement = (Element) pokemonNode;
			pokemon = PlayerTrainerHandler.parsePokemon(new WildPokemonTrainer(), pokemonElement, PokemonHandler.getInstance(), MoveHandler.getInstance());
		}
		
		//Legacy AI to Behavior type
		Node ai = element.getElementsByTagName(AI_TAG).item(0);
		if (ai != null && ai instanceof Element) {
			Node key = ((Element) ai).getElementsByTagName(KEY_TAG).item(0);
			if ("stationary_aggressive".equals(key.getTextContent())) {
				behaviorType = PokemonBehaviorType.STATIONARY_AGGRESSIVE_POKEMON;
			}
			else if ("stationary_pokemon".equals(key.getTextContent())) {
				behaviorType = PokemonBehaviorType.STATIONARY_POKEMON;
			}
		}
	}

	@Override
	protected void loadData(EntityMapData data) {
		if (data.get(POKEMON_KEY) instanceof Pokemon) {
			pokemon = (Pokemon) data.get(POKEMON_KEY);
		}
		if (data.get(BEHAVIOR_TYPE_KEY) != null) {
			try {
				behaviorType = PokemonBehaviorType.valueOf(data.get(BEHAVIOR_TYPE_KEY) + "");
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		
		//Legacy AI to Behavior type
		if (data.get(AI_TAG) instanceof NamespacedEntry) {
			String key = ((NamespacedEntry) data.get(AI_TAG)).getIdentifier();
			if (key.equals("stationary_aggressive")) {
				behaviorType = PokemonBehaviorType.STATIONARY_AGGRESSIVE_POKEMON;
			}
			else if (key.equals("stationary_pokemon")) {
				behaviorType = PokemonBehaviorType.STATIONARY_POKEMON;
			}
		}
	}

	@Override
	protected EntityMapData saveData() {
		HashMap<String, Object> map = new HashMap<>();
		map.put(POKEMON_KEY, pokemon);
		map.put(BEHAVIOR_TYPE_KEY, behaviorType + "");
		
		return new EntityMapData(map);
	}
	
	public static PokemonEntity deserialize (Map<String, Object> map) {
		PokemonEntity entity = new PokemonEntity(null, null, null);
		entity.load(map);
		return entity;
	}

	@Override
	public UUID getOwner() {
		return pokemon.getOwner().getEntity() == null ? null : pokemon.getOwner().getEntity().getUniqueId();
	}

	@Override
	public Class<? extends JavaPlugin> getPlugin() {
		return PokeminePlugin.class;
	}

	public PokemonBehaviorType getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(PokemonBehaviorType behaviorType) {
		this.behaviorType = behaviorType;
	}

}
