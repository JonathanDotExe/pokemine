package at.jojokobi.pokemine.moves;

import at.jojokobi.mcutil.Handler;

public class MoveHandler extends Handler<Move>{
	
//	public static final String MOVE_NAME_ELEMENT = "name";
//	public static final String MOVE_IDENTIFIER_ELEMENT = "identifier";
//	public static final String MOVE_TYPE_ELEMENT = "type";
//	public static final String MOVE_DAMAGE_CLASS_ELEMENT = "damage_class";
//	public static final String MOVE_LEARNING_METHOD_ELEMENT = "learning_method";
//	public static final String MOVE_PRICE_ELEMENT = "price";
//	public static final String MOVE_MIN_LEVEL_ELEMENT = "min_level";
//	public static final String MOVE_POWER_ELEMENT = "power";
//	public static final String MOVE_ACCURACY_ELEMENT = "accuracy";
//	public static final String MOVE_PP_ELEMENT = "pp";
//	public static final String MOVE_MAXPP_ELEMENT = "maxpp";
//	public static final String MOVE_PRIORITY_ELEMENT = "priority";
//	public static final String MOVE_CRITICAL_RATE_ELEMENT = "critical_rate";
//	public static final String MOVE_ANIMATION_ELEMENT = "animation";
//	
//	public static final String MOVE_EFFECTS_ELEMENT = "effects";
//	public static final String MOVE_INITIALIZE_ELEMENT = "initialize";
//	public static final String MOVE_PRE_EXECUTE_ELEMENT = "pre_execute";
	
	//Command Constants
	@Deprecated
	public static final int ATTACK = 0;
	@Deprecated
	public static final int DEFENSE = 1;
	@Deprecated
	public static final int SPECIAL_ATTACK = 2;
	@Deprecated
	public static final int SPECIAL_DEFENSE = 3;
	@Deprecated
	public static final int SPEED = 4;
	
	@Deprecated
	public static final int BURN = 5;
	@Deprecated
	public static final int FREEZE = 6;
	@Deprecated
	public static final int HEAVY_POISON = 7;
	@Deprecated
	public static final int POISON = 8;
	@Deprecated
	public static final int PARALYSIS = 9;
	@Deprecated
	public static final int SLEEP = 10;
	
	@Deprecated
	public static final int FLINCH = 11;
	@Deprecated
	public static final int CONFUSION = 12;
	@Deprecated
	public static final int DROWSINESS = 13;
	@Deprecated
	public static final int PLANTED = 14;
	@Deprecated
	public static final int WRAPPED = 19;
	
	@Deprecated
	public static final int POWER = 15;
	@Deprecated
	public static final int HP = 16;
	@Deprecated
	public static final int PERCENT = 17;
	@Deprecated
	public static final int WHOLE_PERCENT = 18;
	
	
	public static final String PERFORMER = "performer";
	public static final String DEFENDER = "defender";
	public static final String DEFENDER_CONTAINER = "defender_container";
	public static final String MOVE = "move";
	public static final String BATTLE = "battle";
	public static final String ROUND = "round";
	
	public static final String INITIALZER = "init";
	public static final String MOVE_EXECUTION = "move";
	
	public static final String POKEMON_LEVEL = "level";
	
	private static MoveHandler instance = null;
	
	public static MoveHandler getInstance () {
		if (instance == null) {
			instance = new MoveHandler();
		}
		return instance;
	}
	
	private MoveHandler() {
		
	}
	
//	@Override
//	public Move load(InputStream in) {
//		Move move = null;
//		try (InputStream input = in) {
//			//Initialization
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			factory.setNamespaceAware(true);
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			Document document = builder.parse(input);
//			document.getDocumentElement().normalize();
//			
//			//Load
//			//Identifier
//			move = new Move(PokeminePlugin.POKEMINE_NAMESPACE, document.getElementsByTagName(MOVE_IDENTIFIER_ELEMENT).item(0).getTextContent());
//			move.setName(document.getElementsByTagName(MOVE_NAME_ELEMENT).item(0).getTextContent());
//			move.setType(PokemonType.stringToType(document.getElementsByTagName(MOVE_TYPE_ELEMENT).item(0).getTextContent()));
//			move.setDamageClass(DamageClass.stringToDamageClass(document.getElementsByTagName(MOVE_DAMAGE_CLASS_ELEMENT).item(0).getTextContent()));
//			Node learningMethod = document.getElementsByTagName(MOVE_LEARNING_METHOD_ELEMENT).item(0);
//			if (learningMethod != null) {
//				try {
//					move.setLearningMethod(LearningMethod.valueOf(learningMethod.getTextContent().toUpperCase()));
//				}
//				catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				}
//			}
//			Node price = document.getElementsByTagName(MOVE_PRICE_ELEMENT).item(0);
//			if (price != null) {
//				try {
//					move.setPrice(Integer.parseInt(price.getTextContent()));
//				}
//				catch (NumberFormatException e) {
//					e.printStackTrace();
//				}
//			}
//			Node minLevel = document.getElementsByTagName(MOVE_MIN_LEVEL_ELEMENT).item(0);
//			if (minLevel != null) {
//				try {
//					move.setMinLevel(Byte.parseByte(minLevel.getTextContent()));
//				}
//				catch (NumberFormatException e) {
//					e.printStackTrace();
//				}
//			}
//			move.setPower(Integer.parseInt(document.getElementsByTagName(MOVE_POWER_ELEMENT).item(0).getTextContent()));
//			move.setAccuracy(Float.parseFloat(document.getElementsByTagName(MOVE_ACCURACY_ELEMENT).item(0).getTextContent()));
//			move.setPp(Integer.parseInt(document.getElementsByTagName(MOVE_PP_ELEMENT).item(0).getTextContent()));
//			move.setMaxPP(Integer.parseInt(document.getElementsByTagName(MOVE_MAXPP_ELEMENT).item(0).getTextContent()));
//			if (document.getElementsByTagName(MOVE_PRIORITY_ELEMENT).getLength() > 0) {
//				move.setPriority(Integer.parseInt(document.getElementsByTagName(MOVE_PRIORITY_ELEMENT).item(0).getTextContent()));
//			}
//			if (document.getElementsByTagName(MOVE_CRITICAL_RATE_ELEMENT).getLength() > 0) {
//				move.setCriticalRate(Integer.parseInt(document.getElementsByTagName(MOVE_CRITICAL_RATE_ELEMENT).item(0).getTextContent()));
//			}
//			move.setAnimation(document.getElementsByTagName(MOVE_ANIMATION_ELEMENT).item(0).getTextContent());
//			NodeList effects = document.getElementsByTagName(MOVE_EFFECTS_ELEMENT);
//			
//			MoveScriptProcedure procedure = new MoveScriptProcedure();
//			if (effects.getLength() > 0 && effects.item(0).getNodeType() == Node.ELEMENT_NODE) {
//				Element effect = (Element) effects.item(0);
//				procedure.setScript(effect.getTextContent());
////				NodeList commands = effect.getChildNodes();
////				
////				for (int i = 0; i < commands.getLength(); i++) {
////					if (commands.item(i).getNodeType() == Node.ELEMENT_NODE) {
////						move.addCommand(MovescriptParser.parse((Element) commands.item(i), move));
////					}
////				}
//			}
//			//Init Script
//			NodeList initializeNodes = document.getElementsByTagName(MOVE_INITIALIZE_ELEMENT);
//			if (initializeNodes.getLength() > 0 && initializeNodes.item(0).getNodeType() == Node.ELEMENT_NODE) {
//				Element initialize = (Element) initializeNodes.item(0);
//				procedure.setInitializeScript(initialize.getTextContent());
//			}
//			//Pre Execute Script
//			NodeList preExecuteNodes = document.getElementsByTagName(MOVE_PRE_EXECUTE_ELEMENT);
//			if (preExecuteNodes.getLength() > 0 && preExecuteNodes.item(0).getNodeType() == Node.ELEMENT_NODE) {
//				Element preExecute = (Element) preExecuteNodes.item(0);
//				procedure.setPreExecuteScript(preExecute.getTextContent());
//			}
//			move.setProcedure(procedure);
//		}
//		catch (ParserConfigurationException | IOException | SAXException e) {
//			e.printStackTrace();
//		}
//		if (move == null) {
//			move = new Move(PokeminePlugin.POKEMINE_NAMESPACE, "pound");
//		}
//		return move;
//	}
	
//	
//	private List<CommandPlaceholder> parseEffects (String text) {
//		Parser parser = new Parser();
//		//Register Commands
//		parser.addFunction(new DamageCommand());
//		parser.addFunction(new AddValueCommand());
//		parser.addFunction(new SetValueCommand());
//		parser.addFunction(new SetStatusCommand());
//		parser.addFunction(new AddStatusCommand());
//		parser.addFunction(new SwitchCommand());
//		parser.addFunction(new RepeatCommand());
//		
//		List<CommandPlaceholder> script = parser.parse(text);
//		StringBuilder txt = new StringBuilder();
//		for (CommandPlaceholder cmd : script) {
//			txt.append((cmd.getCommand() == null ? "null" : cmd) + ", ");
//		}
//		System.out.println(txt);
//		return script;
//	}

	@Override
	protected Move getStandardInstance(String namespace, String identifier) {
		Move move = new Move(namespace, identifier);
		return move;
	}
	
}
