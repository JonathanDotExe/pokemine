package at.jojokobi.pokemine.trainer;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.mcutil.Handler;
import at.jojokobi.mcutil.general.ArrayUtil;
import at.jojokobi.pokemine.pokemon.PokemonType;

public class TrainerRankHandler extends Handler<TrainerRank>{	
	
	private static TrainerRankHandler instance;
	
	public static TrainerRankHandler getInstance () {
		if (instance == null) {
			instance = new TrainerRankHandler();
		}
		return instance;
	}
	
	private TrainerRankHandler() {
		
	}
	
//	@Override
//	public TrainerRank load(InputStream in) {
//		TrainerRank rank = null;
//		try (InputStream input = in) {
//			//Initialization
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			factory.setNamespaceAware(true);
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			Document document = builder.parse(input);
//			document.getDocumentElement().normalize();
//			//Load
//			//General Data
//			rank = new TrainerRank(PokeminePlugin.POKEMINE_NAMESPACE, document.getElementsByTagName(RANK_IDENTIFIER_ELEMENT).item(0).getTextContent());
//			rank.setName(document.getElementsByTagName(RANK_NAME_ELEMENT).item(0).getTextContent());
//			rank.setId(Integer.parseInt(document.getElementsByTagName(RANK_ID_ELEMENT).item(0).getTextContent()));
//			rank.setSpawnable(Boolean.parseBoolean(document.getElementsByTagName(RANK_SPAWNABLE_ELEMENT).item(0).getTextContent()));
//			rank.setBasePrizeMoney(Integer.parseInt(document.getElementsByTagName(RANK_PRIZE_ELEMENT).item(0).getTextContent()));
//			//Types
//			if (document.getElementsByTagName(PokemonFileProvider.POKEMON_TYPES_ELEMENT).getLength() > 0 && document.getElementsByTagName(PokemonFileProvider.POKEMON_TYPES_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
//				Element typesElement = (Element) document.getElementsByTagName(PokemonFileProvider.POKEMON_TYPES_ELEMENT).item(0);
//				NodeList typeNodes = typesElement.getElementsByTagName(PokemonFileProvider.POKEMON_TYPE_ELEMENT);
//				PokemonType[] types = new PokemonType[typeNodes.getLength()];
//				for (int i = 0; i < types.length; i++) {
//					types[i] = PokemonType.stringToType(typeNodes.item(i).getTextContent());
//				}
//				rank.setTypes(types);
//			}
//			//Names
//			if (document.getElementsByTagName(RANK_TRAINER_NAMES_ELEMENT).getLength() > 0 && document.getElementsByTagName(RANK_TRAINER_NAMES_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
//				Element namesElement = (Element) document.getElementsByTagName(RANK_TRAINER_NAMES_ELEMENT).item(0);
//				NodeList nameNodes = namesElement.getElementsByTagName(RANK_TRAINER_NAME_ELEMENT);
//				String[] names = new String[nameNodes.getLength()];
//				for (int i = 0; i < names.length; i++) {
//					names[i] = nameNodes.item(i).getTextContent();
//				}
//				rank.setNames(names);
//			}
//			//Used Pokemon
//			if (document.getElementsByTagName(RANK_USED_POKEMON_ELEMENT).getLength() > 0 && document.getElementsByTagName(RANK_USED_POKEMON_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
//				Element pokemonElement = (Element) document.getElementsByTagName(RANK_USED_POKEMON_ELEMENT).item(0);
//				NodeList pokemonNodes = pokemonElement.getElementsByTagName(RANK_POKEMON_ELEMENT);
//				PokemonSpecies[] pokemon = new PokemonSpecies[pokemonNodes.getLength()];
//				for (int i = 0; i < pokemon.length; i++) {
//					pokemon[i] = PokemonHandler.getInstance().getItem(PokeminePlugin.POKEMINE_NAMESPACE, pokemonNodes.item(i).getTextContent());
//				}
//				rank.setUsedPokemon(pokemon);
//			}
//			//Start Texts
//			if (document.getElementsByTagName(RANK_START_TEXTS_ELEMENT).getLength() > 0 && document.getElementsByTagName(RANK_START_TEXTS_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
//				Element textsElement = (Element) document.getElementsByTagName(RANK_START_TEXTS_ELEMENT).item(0);
//				NodeList textNodes = textsElement.getElementsByTagName(RANK_TEXT_ELEMENT);
//				String[] messages = new String[textNodes.getLength()];
//				for (int i = 0; i < messages.length; i++) {
//					messages[i] = textNodes.item(i).getTextContent();
//					System.out.println(messages[i]);
//				}
//				rank.setStartMessages(messages);
//			}
//			//Win Texts
//			if (document.getElementsByTagName(RANK_WIN_TEXTS_ELEMENT).getLength() > 0 && document.getElementsByTagName(RANK_WIN_TEXTS_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
//				Element textsElement = (Element) document.getElementsByTagName(RANK_WIN_TEXTS_ELEMENT).item(0);
//				NodeList textNodes = textsElement.getElementsByTagName(RANK_TEXT_ELEMENT);
//				String[] messages = new String[textNodes.getLength()];
//				for (int i = 0; i < messages.length; i++) {
//					messages[i] = textNodes.item(i).getTextContent();
//					System.out.println(messages[i]);
//				}
//				rank.setWinMessages(messages);
//			}
//			//Lose Texts
//			if (document.getElementsByTagName(RANK_LOSE_TEXTS_ELEMENT).getLength() > 0 && document.getElementsByTagName(RANK_LOSE_TEXTS_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
//				Element textsElement = (Element) document.getElementsByTagName(RANK_LOSE_TEXTS_ELEMENT).item(0);
//				NodeList textNodes = textsElement.getElementsByTagName(RANK_TEXT_ELEMENT);
//				String[] messages = new String[textNodes.getLength()];
//				for (int i = 0; i < messages.length; i++) {
//					messages[i] = textNodes.item(i).getTextContent();
//					System.out.println(messages[i]);
//				}
//				rank.setLoseMessages(messages);
//			}
//			//Badge
//			if (document.getElementsByTagName(RANK_BADGE_ELEMENT).getLength() > 0) {
//				rank.setBadge(PokemonType.stringToType(document.getElementsByTagName(RANK_BADGE_ELEMENT).item(0).getTextContent()));
//			}
//		}
//		catch (ParserConfigurationException | IOException | SAXException e) {
//			e.printStackTrace();
//		}
//		if (rank == null) {
//			rank = new TrainerRank(PokeminePlugin.POKEMINE_NAMESPACE, "trainer");
//		}
//		return rank;
//	}
	
	public List<TrainerRank> getRanksWithBadge (PokemonType badge) {
		List<TrainerRank> ranks = new ArrayList<>();
		for (TrainerRank rank : getItemList()) {
			if (rank.getBadge() == badge) {
				ranks.add(rank);
			}
		}
		return ranks;
	}
	
	public List<TrainerRank> getRanksWithType (PokemonType type, boolean ignoreBadge) {
		List<TrainerRank> ranks = new ArrayList<>();
		for (TrainerRank rank : getItemList()) {
			if ((ArrayUtil.arrayContains(rank.getTypes(), type)) && (!ignoreBadge || rank.getBadge() == null)) {
				ranks.add(rank);
			}
		}
		return ranks;
	}

	@Override
	protected TrainerRank getStandardInstance(String namespace, String identifier) {
		TrainerRank rank = new TrainerRank(namespace, identifier);
		return rank;
	}

}
