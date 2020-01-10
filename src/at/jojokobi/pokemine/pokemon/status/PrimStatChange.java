package at.jojokobi.pokemine.pokemon.status;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class PrimStatChange extends StatChange implements ConfigurationSerializable{

	public static final String STATUS_NAME_TAG = "name";
	public static final String STATUS_ROUND_TAG = "round";
	public static final String STATUS_DURATION_TAG = "duration";
	
	private String name = "STT";
	
	public PrimStatChange(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put(STATUS_ROUND_TAG, getRound());
		map.put(STATUS_DURATION_TAG, getDuration());
		return map;
	}
	
	public void load (Map<String, Object> map) {
		try {
			setRound(Integer.parseInt(map.get(STATUS_ROUND_TAG) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			setDuration(Integer.parseInt(map.get(STATUS_DURATION_TAG) + ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public void saveToXML(Element element, Document document) {
		element.setAttribute(STATUS_NAME_TAG, getScriptName());
		Element round = document.createElement(STATUS_ROUND_TAG);
		round.setTextContent(getRound() + "");
		element.appendChild(round);
		Element duration = document.createElement(STATUS_DURATION_TAG);
		duration.setTextContent(getDuration() + "");
		element.appendChild(duration);
	}
	
	public void parseXML (Element element) {
		setRound(Integer.parseInt(element.getElementsByTagName(STATUS_ROUND_TAG).item(0).getTextContent()));
		if (isHasDuration()) {
			Integer.parseInt(element.getElementsByTagName(STATUS_DURATION_TAG).item(0).getTextContent());
		}
	}
	
	public static PrimStatChange parsePrimStatChange (Element element) {
		PrimStatChange stat = null;
		switch (element.getAttribute(STATUS_NAME_TAG)) {
		case Burn.NAME:
			stat = new Burn();
			break;
		case Freeze.NAME:
			stat = new Freeze();
			break;
		case HeavyPoison.NAME:
			stat = new HeavyPoison();
			break;
		case Paralysis.NAME:
			stat = new Paralysis();
			break;
		case Poison.NAME:
			stat = new Poison();
			break;
		case Sleep.NAME:
			stat = new Sleep();
			break;
		}
		if (stat != null) {
			stat.parseXML(element);
		}
		return stat;
	}

}
