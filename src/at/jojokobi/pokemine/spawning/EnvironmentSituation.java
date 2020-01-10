package at.jojokobi.pokemine.spawning;

import org.bukkit.block.Biome;

public class EnvironmentSituation {
	
	private Biome biome;
	private boolean rain;
	private boolean day;
	
	public EnvironmentSituation(Biome biome, boolean rain, boolean day) {
		super();
		this.biome = biome;
		this.rain = rain;
		this.day = day;
	}

	public Biome getBiome() {
		return biome;
	}

	public boolean isRain() {
		return rain;
	}

	public boolean isDay() {
		return day;
	}

	@Override
	public int hashCode() {
		return (biome == null) ? 0 : biome.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnvironmentSituation other = (EnvironmentSituation) obj;
		if (biome != other.biome)
			return false;
		if (day != other.day)
			return false;
		if (rain != other.rain)
			return false;
		return true;
	}

}
