package at.jojokobi.pokemine.pokemon;

import java.util.List;

import at.jojokobi.mcutil.Handler;


public class PokemonHandler extends Handler<PokemonSpecies>{
	
	//Constants
//	public static final String POKEDEX_ELEMENT = "pokedex";
//	public static final String POKEMON_SPECIES_NAME_ELEMENT = "name";
//	public static final String POKEMON_IDENTIFIER_ELEMENT = "identifier";
//	public static final String POKEMON_INDEX_ELEMENT = "index";
//	public static final String POKEMON_TYPES_ELEMENT = "types";
//	public static final String POKEMON_TYPE_ELEMENT = "type";
//	public static final String POKEMON_MALE_CHANCE_ELEMENT = "male_chance";
//	public static final String POKEMON_EGG_GROUP_ELEMENT = "egg_group";
//	public static final String POKEMON_EGG_STEPS_ELEMENT = "egg_steps";
//	public static final String POKEMON_CATEGORY_ELEMENT = "category";
//	public static final String POKEMON_SIZE_ELEMENT = "size";
//	public static final String POKEMON_WEIGHT_ELEMENT = "weight";
//	public static final String POKEMON_COLOR_ELEMENT = "color";
//	public static final String POKEMON_DESCRIPTION_ELEMENT = "description";
//	public static final String POKEMON_LEGENDARY_ELEMENT = "legendary";
//	
//	public static final String BASE_VALUES_ELEMENT = "base_values";
//	public static final String POKEMON_HEALTH_ELEMENT = "health";
//	public static final String POKEMON_ATTACK_ELEMENT = "attack";
//	public static final String POKEMON_DEFENSE_ELEMENT = "defense";
//	public static final String POKEMON_SPECIAL_ATTACK_ELEMENT = "special_attack";
//	public static final String POKEMON_SPECIAL_DEFENSE_ELEMENT = "special_defense";
//	public static final String POKEMON_SPEED_ELEMENT = "speed";
//	public static final String POKEMON_FRIENDSHIP_ELEMENT = "friendship";
//	public static final String POKEMON_CATCH_RATE_ELEMENT = "catch_rate";
//	public static final String POKEMON_LEVEL_EP_ELEMENT = "level_ep";
//	public static final String POKEMON_WILD_LEVEL_ELEMENT = "wild_level";
//	
//	public static final String MOVE_ELEMENT = "move";
//	public static final String MOVE_LEVEL_ELEMENT = "level";
//	
//	public static final String EVOLUTION_ELEMENT = "evolution";
//	public static final String EVOLUTION_SPECIES_ELEMENT = "species";
//	public static final String EVOLUTION_ITEM_ELEMENT = "item";
//	public static final String EVOLUTION_TRADE_ELEMENT = "trade";
//	public static final String EVOLUTION_FRIENDSHIP_ELEMENT = "friendship";
//	public static final String EVOLUTION_HELD_ITEM_ELEMENT = "held_item";
//	
//	public static final String POKEMON_FIELDMOVES_ELEMENT = "fieldmoves";
//	public static final String POKEMON_WALK_ELEMENT = "walking";
//	public static final String POKEMON_SURF_ELEMENT = "surfing";
//	public static final String POKEMON_FLY_ELEMENT = "flying";
//	public static final String POKEMON_LAVA_SURF_ELEMENT = "lava_surfing";
//	public static final String POKEMON_JUMP_ELEMENT = "jumping";
//	public static final String POKEMON_CLIMB_ELEMENT = "climbing";
//	
//	public static final String POKEMON_LOOT_ELEMENT = "loot";
//	
//	public static final String POKEMON_WIN_ELEMENT = "win";
//	public static final String POKEMON_WIN_EP_ELEMENT = "ep";
//	
//	public static final String POKEMON_SPAWNS_ELEMENT = "spawns";
//	public static final String POKEMON_SPAWN_ELEMENT = "spawn";
//	public static final String POKEMON_BIOME_ELEMENT = "biome";
//	public static final String POKEMON_CHANCE_ELEMENT = "chance";
//	public static final String POKEMON_DAY_ELEMENT = "day";
//	public static final String POKEMON_RAIN_ELEMENT = "rain";
	
	
	private static PokemonHandler instance = null;
	
//	private MoveHandler moveHandler;
//	
//	public PokemonHandler() {
//		this.moveHandler = moveHandler;
//	}
	
	public static PokemonHandler getInstance () {
		if (instance == null) {
			instance = new PokemonHandler();
		}
		return instance;
	}
	
	
	private PokemonHandler () {
		
	}
	
//	@Override
//	public PokemonSpecies load (InputStream in) {
//		PokemonSpecies species = null;
//		try (InputStream input = in) {
//			//Initialization
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			factory.setNamespaceAware(true);
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			Document document = builder.parse(input);
//			document.getDocumentElement().normalize();
//			
//			//Load
//			
//			//Pokedex
//			Element pokedex = (Element) document.getElementsByTagName(POKEDEX_ELEMENT).item(0);
//			//Identifier
//			species = new PokemonSpecies(PokeminePlugin.POKEMINE_NAMESPACE, document.getElementsByTagName(POKEMON_IDENTIFIER_ELEMENT).item(0).getTextContent());
//			//Name
//			species.setName(pokedex.getElementsByTagName(POKEMON_SPECIES_NAME_ELEMENT).item(0).getTextContent());
//			//Identifier
//			//Index
//			species.setIndex(Integer.parseInt(pokedex.getElementsByTagName(POKEMON_INDEX_ELEMENT).item(0).getTextContent()));
//			//Types
//			Element typesNode = (Element) document.getElementsByTagName(POKEMON_TYPES_ELEMENT).item(0);
//			NodeList typeNodes = typesNode.getElementsByTagName(POKEMON_TYPE_ELEMENT);
//			if (typeNodes.getLength() > 0) {
//				PokemonType[] types = new PokemonType[typeNodes.getLength()];
//				for (int i = 0; i < types.length; i++) {
//					types[i] = PokemonType.stringToType(typeNodes.item(i).getTextContent());
//				}
//				species.setTypes(types);
//			}
//			//Breeding
//			species.setMaleChance(Float.parseFloat(pokedex.getElementsByTagName(POKEMON_MALE_CHANCE_ELEMENT).item(0).getTextContent()));
//			species.setEggGroups(nodeListToArray(pokedex.getElementsByTagName(POKEMON_EGG_GROUP_ELEMENT)));
//			species.setEggSteps(Integer.parseInt(pokedex.getElementsByTagName(POKEMON_EGG_STEPS_ELEMENT).item(0).getTextContent()));
//			//Apperance
//			species.setCategory(pokedex.getElementsByTagName(POKEMON_CATEGORY_ELEMENT).item(0).getTextContent());
//			species.setSize(Float.parseFloat(pokedex.getElementsByTagName(POKEMON_SIZE_ELEMENT).item(0).getTextContent()));
//			species.setWeight(Float.parseFloat(pokedex.getElementsByTagName(POKEMON_WEIGHT_ELEMENT).item(0).getTextContent()));
//			species.setColor(pokedex.getElementsByTagName(POKEMON_COLOR_ELEMENT).item(0).getTextContent());
//			species.setDescription(pokedex.getElementsByTagName(POKEMON_DESCRIPTION_ELEMENT).item(0).getTextContent());
//			Node legendary = pokedex.getElementsByTagName(POKEMON_LEGENDARY_ELEMENT).item(0);
//			if (legendary != null) {
//				species.setLegendary(Boolean.parseBoolean(legendary.getTextContent()));
//			}
//			//Base Values
//			Element baseValues = (Element) document.getElementsByTagName(BASE_VALUES_ELEMENT).item(0);
//			//Stat Values
//			species.setHealth(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_HEALTH_ELEMENT).item(0).getTextContent()));
//			species.setAttack(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_ATTACK_ELEMENT).item(0).getTextContent()));
//			species.setDefense(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_DEFENSE_ELEMENT).item(0).getTextContent()));
//			species.setSpecialAttack(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_SPECIAL_ATTACK_ELEMENT).item(0).getTextContent()));
//			species.setSpecialDefense(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_SPECIAL_DEFENSE_ELEMENT).item(0).getTextContent()));
//			species.setSpeed(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_SPEED_ELEMENT).item(0).getTextContent()));
//			species.setFriendship(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_FRIENDSHIP_ELEMENT).item(0).getTextContent()));
//			species.setCatchRate(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_CATCH_RATE_ELEMENT).item(0).getTextContent()));
//			species.setLevelEP(LevelSpeed.stringToLevelSpeed(baseValues.getElementsByTagName(POKEMON_LEVEL_EP_ELEMENT).item(0).getTextContent()));
//			species.setWildLevel(Integer.parseInt(baseValues.getElementsByTagName(POKEMON_WILD_LEVEL_ELEMENT).item(0).getTextContent()));
//			//Moves
//			NodeList moves = document.getElementsByTagName(MOVE_ELEMENT);
//			species.clearMoves();
//			for (int i = 0; i < moves.getLength(); i++) {
//				if (moves.item(i).getNodeType() == Node.ELEMENT_NODE) {
//					Element moveElement = (Element) moves.item(i);
//					String identifier = moveElement.getElementsByTagName(MOVE_IDENTIFIER_ELEMENT).item(0).getTextContent();
//					int level = Integer.parseInt(moveElement.getElementsByTagName(MOVE_LEVEL_ELEMENT).item(0).getTextContent());
//					species.addMove(new MoveLearnCondition(MoveHandler.getInstance().getItem(PokeminePlugin.POKEMINE_NAMESPACE, identifier), level));
//				}
//			}
//			//Evolutions
//			NodeList evolutions = document.getElementsByTagName(EVOLUTION_ELEMENT);
//			for (int i = 0; i < evolutions.getLength(); i++) {
//				if (evolutions.item(i).getNodeType() == Node.ELEMENT_NODE) {
//					Element evoElement = (Element) evolutions.item(i);
//					EvolutionCause evolution = new EvolutionCause(evoElement.getElementsByTagName(EVOLUTION_SPECIES_ELEMENT).item(0).getTextContent(), Byte.parseByte(evoElement.getElementsByTagName(MOVE_LEVEL_ELEMENT).item(0).getTextContent()));
//					if (evoElement.getElementsByTagName(EVOLUTION_ITEM_ELEMENT).getLength() > 0) {
//						evolution.setItem(evoElement.getElementsByTagName(EVOLUTION_ITEM_ELEMENT).item(0).getTextContent());
//					}
//					if (evoElement.getElementsByTagName(EVOLUTION_TRADE_ELEMENT).getLength() > 0) {
//						evolution.setTrade(Boolean.parseBoolean(evoElement.getElementsByTagName(EVOLUTION_TRADE_ELEMENT).item(0).getTextContent()));
//					}
//					if (evoElement.getElementsByTagName(EVOLUTION_FRIENDSHIP_ELEMENT).getLength() > 0) {
//						evolution.setFriendship(Integer.parseInt(evoElement.getElementsByTagName(EVOLUTION_FRIENDSHIP_ELEMENT).item(0).getTextContent()));
//					}
//					if (evoElement.getElementsByTagName(EVOLUTION_HELD_ITEM_ELEMENT).getLength() > 0) {
//						evolution.setHeldItem(evoElement.getElementsByTagName(EVOLUTION_HELD_ITEM_ELEMENT).item(0).getTextContent());
//					}
//					species.addEvolution(evolution);
//				}
//			}
//			//Field Moves
//			NodeList fieldMoveResults = document.getElementsByTagName(POKEMON_FIELDMOVES_ELEMENT);
//			if (fieldMoveResults.getLength() > 0 && fieldMoveResults.item(0).getNodeType() == Node.ELEMENT_NODE) {
//				Element fieldMovesElement = (Element) fieldMoveResults.item(0);
//				species.setWalkSpeed(Float.parseFloat(fieldMovesElement.getElementsByTagName(POKEMON_WALK_ELEMENT).item(0).getTextContent()));
//				species.setSurfSpeed(Float.parseFloat(fieldMovesElement.getElementsByTagName(POKEMON_SURF_ELEMENT).item(0).getTextContent()));
//				species.setFlySpeed(Float.parseFloat(fieldMovesElement.getElementsByTagName(POKEMON_FLY_ELEMENT).item(0).getTextContent()));
//				species.setLavaSurfSpeed(Float.parseFloat(fieldMovesElement.getElementsByTagName(POKEMON_LAVA_SURF_ELEMENT).item(0).getTextContent()));
//				species.setJumpSpeed(Float.parseFloat(fieldMovesElement.getElementsByTagName(POKEMON_JUMP_ELEMENT).item(0).getTextContent()));
//				species.setClimbSpeed(Float.parseFloat(fieldMovesElement.getElementsByTagName(POKEMON_CLIMB_ELEMENT).item(0).getTextContent()));
//			}
//			//Loot
//			Node loot = document.getElementsByTagName(POKEMON_LOOT_ELEMENT).item(0);
//			if (loot != null && loot.getNodeType() == Node.ELEMENT_NODE) {
//				species.setLoot(LootInventory.fromXML((Element) loot));
//			}
//			//Win
//			NodeList winResults = document.getElementsByTagName(POKEMON_WIN_ELEMENT);
//			if (winResults.getLength() > 0 && winResults.item(0).getNodeType() == Node.ELEMENT_NODE) {
//				Element winElement = (Element) winResults.item(0);
//				species.setWinXp(Integer.parseInt(winElement.getElementsByTagName(POKEMON_WIN_EP_ELEMENT).item(0).getTextContent()));
//				species.setHealthFp(Integer.parseInt(winElement.getElementsByTagName(POKEMON_HEALTH_ELEMENT).item(0).getTextContent()));
//				species.setAttackFp(Integer.parseInt(winElement.getElementsByTagName(POKEMON_ATTACK_ELEMENT).item(0).getTextContent()));
//				species.setDefenseFp(Integer.parseInt(winElement.getElementsByTagName(POKEMON_DEFENSE_ELEMENT).item(0).getTextContent()));
//				species.setSpecialAttackFp(Integer.parseInt(winElement.getElementsByTagName(POKEMON_SPECIAL_ATTACK_ELEMENT).item(0).getTextContent()));
//				species.setSpecialDefenseFp(Integer.parseInt(winElement.getElementsByTagName(POKEMON_SPECIAL_DEFENSE_ELEMENT).item(0).getTextContent()));
//				species.setSpeedFp(Integer.parseInt(winElement.getElementsByTagName(POKEMON_SPEED_ELEMENT).item(0).getTextContent()));
//			}
//			//Spawns
//			NodeList spawnResults = document.getElementsByTagName(POKEMON_SPAWNS_ELEMENT);
//			if (spawnResults.getLength() > 0 && spawnResults.item(0).getNodeType() == Node.ELEMENT_NODE) {
//				Element spawnsElement = (Element) spawnResults.item(0);
//				NodeList spawns = spawnsElement.getElementsByTagName(POKEMON_SPAWN_ELEMENT);
//				for (int i = 0; i < spawns.getLength(); i++) {
//					if (spawns.item(i).getNodeType() == Node.ELEMENT_NODE) {
//						Element spawn = (Element) spawns.item(i);
//						String biomeText = spawn.getElementsByTagName(POKEMON_BIOME_ELEMENT).item(0).getTextContent().toUpperCase();
//						Biome biome = null;
//						try {
//							biome = Biome.valueOf(biomeText);
//						}
//						catch (IllegalArgumentException e) {
//							System.err.println("Error while loading pokemon species " + species.getName() + "!");
//							System.err.println("The biome " + biomeText + " does not exist!");
//							e.printStackTrace();
//						}
//						int chance = Integer.parseInt(spawn.getElementsByTagName(POKEMON_CHANCE_ELEMENT).item(0).getTextContent());
//						Boolean day = null;
//						Boolean rain = null;
//						if (biome != null) {
//							//Check if rain and day are defined
//							if (spawn.getElementsByTagName(POKEMON_DAY_ELEMENT).getLength() > 0) {
//								day = Boolean.valueOf(spawn.getElementsByTagName(POKEMON_DAY_ELEMENT).item(0).getTextContent());
//							}
//							if (spawn.getElementsByTagName(POKEMON_RAIN_ELEMENT).getLength() > 0) {
//								rain = Boolean.valueOf(spawn.getElementsByTagName(POKEMON_RAIN_ELEMENT).item(0).getTextContent());
//							}
//							//Add
//							if (day == null && rain == null) {
//								species.addSpawn(new EnvironmentSituation(biome, false, false), chance);
//								species.addSpawn(new EnvironmentSituation(biome, true, false), chance);
//								species.addSpawn(new EnvironmentSituation(biome, false, true), chance);
//								species.addSpawn(new EnvironmentSituation(biome, true, true), chance);
//							}
//							else if (day == null) {
//								species.addSpawn(new EnvironmentSituation(biome, rain, true), chance);
//								species.addSpawn(new EnvironmentSituation(biome, rain, true), chance);
//							}
//							else if (rain == null) {
//								species.addSpawn(new EnvironmentSituation(biome, false, day), chance);
//								species.addSpawn(new EnvironmentSituation(biome, true, day), chance);
//							}
//							else {
//								species.addSpawn(new EnvironmentSituation(biome, rain, day), chance);
//							}
//						}
//					}
//				}
//			}
//		}
//		catch (ParserConfigurationException | IOException | SAXException e) {
//			e.printStackTrace();
//		}
//		if (species == null) {
//			species = new PokemonSpecies(PokeminePlugin.POKEMINE_NAMESPACE, "missingno");
//		}
//		return species;
//	}
	
	@Override
	public List<PokemonSpecies> getItemList() {
		List<PokemonSpecies> species = super.getItemList();
		species.sort((poke1, poke2) -> poke1.getIndex() - poke2.getIndex());
		return species;
	}

//	private static String[] nodeListToArray (NodeList list) {
//		String[] array = new String[list.getLength()];
//		for (int i = 0; i < list.getLength(); i++) {
//			array[i] = list.item(i).getTextContent();
//		}
//		return array;
//	}

	@Override
	protected PokemonSpecies getStandardInstance(String namespace, String identifier) {
		PokemonSpecies species = new PokemonSpecies(namespace, identifier);
		return species;
	}

}
