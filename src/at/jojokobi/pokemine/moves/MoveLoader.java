package at.jojokobi.pokemine.moves;

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
import at.jojokobi.pokemine.moves.procedures.LegacyMoveScriptProcedure;
import at.jojokobi.pokemine.pokemon.PokemonType;

public final class MoveLoader implements FileLoader<Move>{
	
	public static final String MOVE_NAME_ELEMENT = "name";
	public static final String MOVE_IDENTIFIER_ELEMENT = "identifier";
	public static final String MOVE_TYPE_ELEMENT = "type";
	public static final String MOVE_DAMAGE_CLASS_ELEMENT = "damage_class";
	public static final String MOVE_LEARNING_METHOD_ELEMENT = "learning_method";
	public static final String MOVE_PRICE_ELEMENT = "price";
	public static final String MOVE_MIN_LEVEL_ELEMENT = "min_level";
	public static final String MOVE_POWER_ELEMENT = "power";
	public static final String MOVE_ACCURACY_ELEMENT = "accuracy";
	public static final String MOVE_PP_ELEMENT = "pp";
	public static final String MOVE_MAXPP_ELEMENT = "maxpp";
	public static final String MOVE_PRIORITY_ELEMENT = "priority";
	public static final String MOVE_CRITICAL_RATE_ELEMENT = "critical_rate";
	public static final String MOVE_ANIMATION_ELEMENT = "animation";
	
	public static final String MOVE_EFFECTS_ELEMENT = "effects";
	public static final String MOVE_INITIALIZE_ELEMENT = "initialize";
	public static final String MOVE_PRE_EXECUTE_ELEMENT = "pre_execute";

	private static MoveLoader instance;
	
	private MoveLoader () {
		
	}
	
	public static MoveLoader getInstance () {
		if (instance == null) {
			instance = new MoveLoader();
		}
		return instance;
	}
	
	public Move load (InputStream input) {
		return load(input, true);
	}
	
	public Move load (InputStream input, boolean loadProcedure) {
		Move move = null;
		try {
			//Initialization
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(input);
			document.getDocumentElement().normalize();
			
			//Load
			//Identifier
			move = new Move(PokeminePlugin.POKEMINE_NAMESPACE, document.getElementsByTagName(MOVE_IDENTIFIER_ELEMENT).item(0).getTextContent());
			move.setName(document.getElementsByTagName(MOVE_NAME_ELEMENT).item(0).getTextContent());
			move.setType(PokemonType.stringToType(document.getElementsByTagName(MOVE_TYPE_ELEMENT).item(0).getTextContent()));
			move.setDamageClass(DamageClass.stringToDamageClass(document.getElementsByTagName(MOVE_DAMAGE_CLASS_ELEMENT).item(0).getTextContent()));
			Node learningMethod = document.getElementsByTagName(MOVE_LEARNING_METHOD_ELEMENT).item(0);
			if (learningMethod != null) {
				try {
					move.setLearningMethod(LearningMethod.valueOf(learningMethod.getTextContent().toUpperCase()));
				}
				catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
			Node price = document.getElementsByTagName(MOVE_PRICE_ELEMENT).item(0);
			if (price != null) {
				try {
					move.setPrice(Integer.parseInt(price.getTextContent()));
				}
				catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			Node minLevel = document.getElementsByTagName(MOVE_MIN_LEVEL_ELEMENT).item(0);
			if (minLevel != null) {
				try {
					move.setMinLevel(Byte.parseByte(minLevel.getTextContent()));
				}
				catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			move.setPower(Integer.parseInt(document.getElementsByTagName(MOVE_POWER_ELEMENT).item(0).getTextContent()));
			move.setAccuracy(Float.parseFloat(document.getElementsByTagName(MOVE_ACCURACY_ELEMENT).item(0).getTextContent()));
			move.setPp(Integer.parseInt(document.getElementsByTagName(MOVE_PP_ELEMENT).item(0).getTextContent()));
			move.setMaxPp(Integer.parseInt(document.getElementsByTagName(MOVE_MAXPP_ELEMENT).item(0).getTextContent()));
			if (document.getElementsByTagName(MOVE_PRIORITY_ELEMENT).getLength() > 0) {
				move.setPriority(Integer.parseInt(document.getElementsByTagName(MOVE_PRIORITY_ELEMENT).item(0).getTextContent()));
			}
			if (document.getElementsByTagName(MOVE_CRITICAL_RATE_ELEMENT).getLength() > 0) {
				move.setCriticalRate(Integer.parseInt(document.getElementsByTagName(MOVE_CRITICAL_RATE_ELEMENT).item(0).getTextContent()));
			}
			move.setAnimation(document.getElementsByTagName(MOVE_ANIMATION_ELEMENT).item(0).getTextContent());
			NodeList effects = document.getElementsByTagName(MOVE_EFFECTS_ELEMENT);
			
			if (loadProcedure) {
				LegacyMoveScriptProcedure procedure = new LegacyMoveScriptProcedure();
				if (effects.getLength() > 0 && effects.item(0).getNodeType() == Node.ELEMENT_NODE) {
					Element effect = (Element) effects.item(0);
					procedure.setScript(effect.getTextContent());
//					NodeList commands = effect.getChildNodes();
//					
//					for (int i = 0; i < commands.getLength(); i++) {
//						if (commands.item(i).getNodeType() == Node.ELEMENT_NODE) {
//							move.addCommand(MovescriptParser.parse((Element) commands.item(i), move));
//						}
//					}
				}
				//Init Script
				NodeList initializeNodes = document.getElementsByTagName(MOVE_INITIALIZE_ELEMENT);
				if (initializeNodes.getLength() > 0 && initializeNodes.item(0).getNodeType() == Node.ELEMENT_NODE) {
					Element initialize = (Element) initializeNodes.item(0);
					procedure.setInitializeScript(initialize.getTextContent());
				}
				//Pre Execute Script
				NodeList preExecuteNodes = document.getElementsByTagName(MOVE_PRE_EXECUTE_ELEMENT);
				if (preExecuteNodes.getLength() > 0 && preExecuteNodes.item(0).getNodeType() == Node.ELEMENT_NODE) {
					Element preExecute = (Element) preExecuteNodes.item(0);
					procedure.setPreExecuteScript(preExecute.getTextContent());
				}
				move.setProcedure(procedure);
			}
		}
		catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
		if (move == null) {
			move = new Move(PokeminePlugin.POKEMINE_NAMESPACE, "pound");
		}
		return move;
	}

}
