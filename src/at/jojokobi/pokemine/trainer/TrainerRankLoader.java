package at.jojokobi.pokemine.trainer;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import at.jojokobi.mcutil.FileLoader;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.pokemon.PokemonFileProvider;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.PokemonSpecies;
import at.jojokobi.pokemine.pokemon.PokemonType;

public class TrainerRankLoader implements FileLoader<TrainerRank>{

	public static final String RANK_NAME_ELEMENT = "name";
	public static final String RANK_IDENTIFIER_ELEMENT = "identifier";
	public static final String RANK_ID_ELEMENT = "id";
	public static final String RANK_SPAWNABLE_ELEMENT = "spawnable";
	public static final String RANK_PRIZE_ELEMENT = "prize";
	public static final String RANK_TRAINER_NAMES_ELEMENT = "names";
	public static final String RANK_TRAINER_NAME_ELEMENT = "name";
	public static final String RANK_USED_POKEMON_ELEMENT = "used_pokemon";
	public static final String RANK_POKEMON_ELEMENT = "pokemon";
	public static final String RANK_START_TEXTS_ELEMENT = "start_texts";
	public static final String RANK_WIN_TEXTS_ELEMENT = "win_texts";
	public static final String RANK_LOSE_TEXTS_ELEMENT = "lose_texts";
	public static final String RANK_TEXT_ELEMENT = "text";
	public static final String RANK_BADGE_ELEMENT = "badge";
	
	private static TrainerRankLoader instance = null;
	
	public static TrainerRankLoader getInstance () {
		if (instance == null) {
			instance = new TrainerRankLoader();
		}
		return instance;
	}

	@Override
	public TrainerRank load(InputStream input) throws ParserConfigurationException, IOException, SAXException {
		TrainerRank rank = null;
		try {
			//Initialization
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(input);
			document.getDocumentElement().normalize();
			//Load
			//General Data
			rank = new TrainerRank(PokeminePlugin.POKEMINE_NAMESPACE, document.getElementsByTagName(RANK_IDENTIFIER_ELEMENT).item(0).getTextContent());
			rank.setName(document.getElementsByTagName(RANK_NAME_ELEMENT).item(0).getTextContent());
			rank.setId(Integer.parseInt(document.getElementsByTagName(RANK_ID_ELEMENT).item(0).getTextContent()));
			rank.setSpawnable(Boolean.parseBoolean(document.getElementsByTagName(RANK_SPAWNABLE_ELEMENT).item(0).getTextContent()));
			rank.setBasePrizeMoney(Integer.parseInt(document.getElementsByTagName(RANK_PRIZE_ELEMENT).item(0).getTextContent()));
			//Types
			if (document.getElementsByTagName(PokemonFileProvider.POKEMON_TYPES_ELEMENT).getLength() > 0 && document.getElementsByTagName(PokemonFileProvider.POKEMON_TYPES_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
				Element typesElement = (Element) document.getElementsByTagName(PokemonFileProvider.POKEMON_TYPES_ELEMENT).item(0);
				NodeList typeNodes = typesElement.getElementsByTagName(PokemonFileProvider.POKEMON_TYPE_ELEMENT);
				PokemonType[] types = new PokemonType[typeNodes.getLength()];
				for (int i = 0; i < types.length; i++) {
					types[i] = PokemonType.stringToType(typeNodes.item(i).getTextContent());
				}
				rank.setTypes(types);
			}
			//Names
			if (document.getElementsByTagName(RANK_TRAINER_NAMES_ELEMENT).getLength() > 0 && document.getElementsByTagName(RANK_TRAINER_NAMES_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
				Element namesElement = (Element) document.getElementsByTagName(RANK_TRAINER_NAMES_ELEMENT).item(0);
				NodeList nameNodes = namesElement.getElementsByTagName(RANK_TRAINER_NAME_ELEMENT);
				String[] names = new String[nameNodes.getLength()];
				for (int i = 0; i < names.length; i++) {
					names[i] = nameNodes.item(i).getTextContent();
				}
				rank.setNames(names);
			}
			//Used Pokemon
			if (document.getElementsByTagName(RANK_USED_POKEMON_ELEMENT).getLength() > 0 && document.getElementsByTagName(RANK_USED_POKEMON_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
				Element pokemonElement = (Element) document.getElementsByTagName(RANK_USED_POKEMON_ELEMENT).item(0);
				NodeList pokemonNodes = pokemonElement.getElementsByTagName(RANK_POKEMON_ELEMENT);
				PokemonSpecies[] pokemon = new PokemonSpecies[pokemonNodes.getLength()];
				for (int i = 0; i < pokemon.length; i++) {
					pokemon[i] = PokemonHandler.getInstance().getItem(PokeminePlugin.POKEMINE_NAMESPACE, pokemonNodes.item(i).getTextContent());
				}
				rank.setUsedPokemon(pokemon);
			}
			//Start Texts
			if (document.getElementsByTagName(RANK_START_TEXTS_ELEMENT).getLength() > 0 && document.getElementsByTagName(RANK_START_TEXTS_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
				Element textsElement = (Element) document.getElementsByTagName(RANK_START_TEXTS_ELEMENT).item(0);
				NodeList textNodes = textsElement.getElementsByTagName(RANK_TEXT_ELEMENT);
				String[] messages = new String[textNodes.getLength()];
				for (int i = 0; i < messages.length; i++) {
					messages[i] = textNodes.item(i).getTextContent();
					System.out.println(messages[i]);
				}
				rank.setStartMessages(messages);
			}
			//Win Texts
			if (document.getElementsByTagName(RANK_WIN_TEXTS_ELEMENT).getLength() > 0 && document.getElementsByTagName(RANK_WIN_TEXTS_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
				Element textsElement = (Element) document.getElementsByTagName(RANK_WIN_TEXTS_ELEMENT).item(0);
				NodeList textNodes = textsElement.getElementsByTagName(RANK_TEXT_ELEMENT);
				String[] messages = new String[textNodes.getLength()];
				for (int i = 0; i < messages.length; i++) {
					messages[i] = textNodes.item(i).getTextContent();
					System.out.println(messages[i]);
				}
				rank.setWinMessages(messages);
			}
			//Lose Texts
			if (document.getElementsByTagName(RANK_LOSE_TEXTS_ELEMENT).getLength() > 0 && document.getElementsByTagName(RANK_LOSE_TEXTS_ELEMENT).item(0).getNodeType() == Node.ELEMENT_NODE) {
				Element textsElement = (Element) document.getElementsByTagName(RANK_LOSE_TEXTS_ELEMENT).item(0);
				NodeList textNodes = textsElement.getElementsByTagName(RANK_TEXT_ELEMENT);
				String[] messages = new String[textNodes.getLength()];
				for (int i = 0; i < messages.length; i++) {
					messages[i] = textNodes.item(i).getTextContent();
					System.out.println(messages[i]);
				}
				rank.setLoseMessages(messages);
			}
			//Badge
			if (document.getElementsByTagName(RANK_BADGE_ELEMENT).getLength() > 0) {
				rank.setBadge(PokemonType.stringToType(document.getElementsByTagName(RANK_BADGE_ELEMENT).item(0).getTextContent()));
			}
		}
		catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
		if (rank == null) {
			rank = new TrainerRank(PokeminePlugin.POKEMINE_NAMESPACE, "trainer");
		}
		return rank;
	}

}
