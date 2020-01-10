package at.jojokobi.pokemine.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.pokemine.PokeminePlugin;
import at.jojokobi.pokemine.pokemon.Pokemon;
import at.jojokobi.pokemine.pokemon.PokemonHandler;
import at.jojokobi.pokemine.pokemon.entity.PokemonEntity;

public class PokemineCommand implements CommandExecutor{
	
	private PokeminePlugin plugin;
	public static final String COMMAND_NAME = "pokemine";
	
	public PokemineCommand(PokeminePlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean success = true;
		if (label.equalsIgnoreCase(COMMAND_NAME)) {
			if (args.length > 0 && sender.isOp()) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					EntityHandler handler = plugin.getEntityHandler();
					Pokemon pokemon = new Pokemon(PokemonHandler.getInstance().getItem(PokeminePlugin.POKEMINE_NAMESPACE, args[0]));
					if (args.length > 1) {
						try {
							pokemon.setLevel(Byte.parseByte(args[1]));
						} catch (NumberFormatException e) {
							player.sendMessage(ChatColor.RED + "The pokemon's level isn't a valid number!");
						}
					}
					handler.addEntity(new PokemonEntity(player.getLocation(), pokemon, handler));
				}
				else {
					sender.sendMessage("This Command can only be used by Players!");
					success = false;
				}
			}
			else {
				sender.sendMessage(PokemonHandler.getInstance().getItems().toString());
			}
		}
		return success;
	}

}
