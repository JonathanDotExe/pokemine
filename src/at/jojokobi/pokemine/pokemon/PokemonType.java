package at.jojokobi.pokemine.pokemon;

import org.bukkit.ChatColor;

public enum PokemonType {
	NORMAL, FIRE, FIGHTING, WATER, FLYING, GRASS, POISON,
	ELECTRIC, GROUND, PSYCHIC, ROCK, ICE, BUG, DRAGON, GHOST,
	DARK, STEEL, FAIRY;
	
	public static PokemonType stringToType(String string) {
		PokemonType type = PokemonType.NORMAL;
		switch (string.toLowerCase()) {
		case "fire": type = PokemonType.FIRE; break;
		case "fighting": type = PokemonType.FIGHTING; break;
		case "water": type = PokemonType.WATER; break;
		case "flying": type = PokemonType.FLYING; break;
		case "grass": type = PokemonType.GRASS; break;
		case "poison": type = PokemonType.POISON; break;
		case "electric": type = PokemonType.ELECTRIC; break;
		case "ground": type = PokemonType.GROUND; break;
		case "psychic": type = PokemonType.PSYCHIC; break;
		case "rock":  type = PokemonType.ROCK; break;
		case "ice": type = PokemonType.ICE; break;
		case "bug": type = PokemonType.BUG; break;
		case "dragon": type = PokemonType.DRAGON; break;
		case "ghost": type = PokemonType.GHOST; break;
		case "dark": type = PokemonType.DARK; break;
		case "steel": type = PokemonType.STEEL; break;
		case "fairy": type = PokemonType.FAIRY; break;
		}
		return type;
	}
	
	@Override
	public String toString () {
		String string = "";
		switch (this) {
		case NORMAL: string = "Normal"; break;
		case FIRE: string = "Fire"; break;
		case FIGHTING: string = "Fighting"; break;
		case WATER: string = "Water"; break;
		case FLYING: string = "Flying"; break;
		case GRASS: string = "Grass"; break;
		case POISON: string = "Poison"; break;
		case ELECTRIC: string = "Electric"; break;
		case GROUND: string = "Ground"; break;
		case PSYCHIC: string = "Psychic"; break;
		case ROCK: string = "Rock"; break;
		case ICE: string = "Ice"; break;
		case BUG: string = "Bug"; break;
		case DARK: string = "Dark"; break;
		case DRAGON: string = "Dragon"; break;
		case FAIRY: string = "Fairy"; break;
		case GHOST: string = "Ghost"; break;
		case STEEL: string = "Steel"; break;
		}
		return string;
	}
	
	public float getEffectivityMultiplier (PokemonType opposite) {
		float multiplier = 1;
		switch (this) {
		case NORMAL:
			switch (opposite) {
			case FIGHTING: multiplier = 2; break;
			case GHOST: multiplier = 0; break;
			default:
				break;
			}
			break;
		case FIRE:
			switch (opposite) {
			case FIRE: multiplier = 0.5f;break;
			case WATER: multiplier = 2;break;
			case GRASS: multiplier = 0.5f;break;
			case GROUND: multiplier = 2;break;
			case ROCK: multiplier = 2;break;
			case ICE: multiplier = 0.5f;break;
			case BUG: multiplier = 0.5f;break;
			case STEEL: multiplier = 0.5f;break;
			default:break;
			}
			break;
		case FIGHTING:
			switch (opposite) {
			case FLYING: multiplier = 2;break;
			case PSYCHIC: multiplier = 2;break;
			case ROCK: multiplier = 0.5f;break;
			case BUG: multiplier = 0.5f;break;
			case DARK: multiplier = 0.5f;break;
			case FAIRY: multiplier = 2;break;
			default:break;
			}
			break;
		case WATER:
			switch (opposite) {
			case FIRE: multiplier = 0.5f;break;
			case WATER: multiplier = 0.5f;break;
			case GRASS: multiplier = 2;break;
			case ELECTRIC: multiplier = 2;break;
			case ICE: multiplier = 0.5f;break;
			case STEEL: multiplier = 0.5f;break;
			default:break;
			}
			break;
		case FLYING:
			switch (opposite) {
			case NORMAL: multiplier = 1;break;
			case FIRE: multiplier = 1;break;
			case FIGHTING: multiplier = 0.5f;break;
			case WATER: multiplier = 1;break;
			case FLYING: multiplier = 1;break;
			case GRASS: multiplier = 0.5f;break;
			case POISON: multiplier = 1;break;
			case ELECTRIC: multiplier = 2;break;
			case GROUND: multiplier = 0;break;
			case PSYCHIC: multiplier = 1;break;
			case ROCK: multiplier = 2;break;
			case ICE: multiplier = 2;break;
			case BUG: multiplier = 0.5f;break;
			case DARK: multiplier = 1;break;
			case DRAGON: multiplier = 1;break;
			case FAIRY: multiplier = 1;break;
			case GHOST: multiplier = 1;break;
			case STEEL: multiplier = 1;break;
			default:break;
			}
			break;
		case GRASS:
			switch (opposite) {
			case FIRE: multiplier = 2;break;
			case WATER: multiplier = 0.5f;break;
			case FLYING: multiplier = 2;break;
			case GRASS: multiplier = 0.5f;break;
			case POISON: multiplier = 2;break;
			case ELECTRIC: multiplier = 0.5f;break;
			case GROUND: multiplier = 0.5f;break;
			case ICE: multiplier = 2;break;
			case BUG: multiplier = 2;break;
			default:break;
			}
			break;
		case POISON:
			switch (opposite) {
			case FIGHTING: multiplier = 0.5f;break;
			case GRASS: multiplier = 0.5f;break;
			case GROUND: multiplier = 2;break;
			case PSYCHIC: multiplier = 2;break;
			case BUG: multiplier = 0.5f;break;
			case FAIRY: multiplier = 0.5f;break;
			default:break;
			}
			break;
		case ELECTRIC:
			switch (opposite) {
			case FLYING: multiplier = 0.5f;break;
			case ELECTRIC: multiplier = 0.5f;break;
			case GROUND: multiplier = 2;break;
			case STEEL: multiplier = 0.5f;break;
			default:break;
			}
			break;
		case GROUND:
			switch (opposite) {
			case WATER: multiplier = 2;break;
			case GRASS: multiplier = 2;break;
			case POISON: multiplier = 0.5f;break;
			case ELECTRIC: multiplier = 0;break;
			case ROCK: multiplier = 0.5f;break;
			case ICE: multiplier = 2;break;
			default:break;
			}
			break;
		case PSYCHIC:
			switch (opposite) {
			case FIGHTING: multiplier = 0.5f;break;
			case PSYCHIC: multiplier = 0.5f;break;
			case BUG: multiplier = 2;break;
			case DARK: multiplier = 2;break;
			case GHOST: multiplier = 2;break;
			default:break;
			}
			break;
		case ROCK:
			switch (opposite) {
			case NORMAL: multiplier = 0.5f;break;
			case FIRE: multiplier = 0.5f;break;
			case FIGHTING: multiplier = 2;break;
			case WATER: multiplier = 2;break;
			case FLYING: multiplier = 0.5f;break;
			case GRASS: multiplier = 2;break;
			case POISON: multiplier = 0.5f;break;
			case GROUND: multiplier = 2;break;
			case STEEL: multiplier = 2;break;
			default:break;
			}
			break;
		case ICE:
			switch (opposite) {
			case FIRE: multiplier = 2;break;
			case FIGHTING: multiplier = 2;break;
			case ROCK: multiplier = 2;break;
			case ICE: multiplier = 0.5f;break;
			case STEEL: multiplier = 2;break;
			default:break;
			}
			break;
		case BUG:
			switch (opposite) {
			case FIRE: multiplier = 2;break;
			case FIGHTING: multiplier = 0.5f;break;
			case FLYING: multiplier = 2;break;
			case GRASS: multiplier = 0.5f;break;
			case GROUND: multiplier = 0.5f;break;
			case ROCK: multiplier = 2;break;
			default:break;
			}
			break;
		case DARK:
			switch (opposite) {
			case FIGHTING: multiplier = 2;break;
			case PSYCHIC: multiplier = 0;break;
			case BUG: multiplier = 2;break;
			case DARK: multiplier = 0.5f;break;
			case FAIRY: multiplier = 2;break;
			case GHOST: multiplier = 0.5f;break;
			default:break;
			}
			break;
		case DRAGON:
			switch (opposite) {
			case FIRE: multiplier = 0.5f;break;
			case WATER: multiplier = 0.5f;break;
			case GRASS: multiplier = 0.5f;break;
			case ELECTRIC: multiplier = 0.5f;break;
			case ICE: multiplier = 2;break;
			case DRAGON: multiplier = 2;break;
			case FAIRY: multiplier = 2;break;
			default:break;
			}
			break;
		case FAIRY:
			switch (opposite) {
			case FIGHTING: multiplier = 0.5f;break;
			case POISON: multiplier = 2;break;
			case BUG: multiplier = 0.5f;break;
			case DARK: multiplier = 0.5f;break;
			case DRAGON: multiplier = 0;break;
			case STEEL: multiplier = 2;break;
			default:break;
			}
			break;
		case GHOST:
			switch (opposite) {
			case NORMAL: multiplier = 0;break;
			case FIGHTING: multiplier = 0;break;
			case POISON: multiplier = 0.5f;break;
			case BUG: multiplier = 0.5f;break;
			case DARK: multiplier = 2;break;
			case GHOST: multiplier = 2;break;
			default:break;
			}
			break;
		case STEEL:
			switch (opposite) {
			case NORMAL: multiplier = 0.5f;break;
			case FIRE: multiplier = 2;break;
			case FIGHTING: multiplier = 2;break;
			case FLYING: multiplier = 0.5f;break;
			case GRASS: multiplier = 0.5f;break;
			case POISON: multiplier = 0;break;
			case GROUND: multiplier = 2;break;
			case PSYCHIC: multiplier = 0.5f;break;
			case ROCK: multiplier = 0.5f;break;
			case ICE: multiplier = 0.5f;break;
			case BUG: multiplier = 0.5f;break;
			case DRAGON: multiplier = 0.5f;break;
			case FAIRY: multiplier = 0.5f;break;
			case STEEL: multiplier = 0.5f;break;
			default:break;
			}
			break;
		default:
			break;
		}
		return multiplier;
		/*
			switch (opposite) {
			case NORMAL: multiplier = 1;break;
			case FIRE: multiplier = 1;break;
			case FIGHTING: multiplier = 1;break;
			case WATER: multiplier = 1;break;
			case FLYING: multiplier = 1;break;
			case GRASS: multiplier = 1;break;
			case POISON: multiplier = 1;break;
			case ELECRTIC: multiplier = 1;break;
			case GROUND: multiplier = 1;break;
			case PSYCHIC: multiplier = 1;break;
			case ROCK: multiplier = 1;break;
			case ICE: multiplier = 1;break;
			case BUG: multiplier = 1;break;
			case DARK: multiplier = 1;break;
			case DRAGON: multiplier = 1;break;
			case FAIRY: multiplier = 1;break;
			case GHOST: multiplier = 1;break;
			case STEEL: multiplier = 1;break;
			default:break;
			}
		*/
	}
	
	public ChatColor getColor () {
		ChatColor color = ChatColor.WHITE;
		switch (this) {
		case NORMAL: color = ChatColor.WHITE; break;
		case FIRE: color = ChatColor.RED; break;
		case FIGHTING: color = ChatColor.DARK_RED; break;
		case WATER: color = ChatColor.BLUE; break;
		case FLYING: color = ChatColor.AQUA; break;
		case GRASS: color = ChatColor.GREEN; break;
		case POISON: color = ChatColor.DARK_PURPLE; break;
		case ELECTRIC: color = ChatColor.YELLOW; break;
		case GROUND: color = ChatColor.GOLD; break;
		case PSYCHIC: color = ChatColor.LIGHT_PURPLE; break;
		case ROCK: color = ChatColor.DARK_GRAY; break;
		case ICE: color = ChatColor.DARK_AQUA; break;
		case BUG: color = ChatColor.DARK_GREEN; break;
		case DARK: color = ChatColor.BLACK; break;
		case DRAGON: color = ChatColor.DARK_BLUE; break;
		case FAIRY: color = ChatColor.LIGHT_PURPLE; break;
		case GHOST: color = ChatColor.DARK_BLUE; break;
		case STEEL: color = ChatColor.GRAY; break;
		}
		return color;
	}
	
}
