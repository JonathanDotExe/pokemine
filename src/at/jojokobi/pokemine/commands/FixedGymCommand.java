package at.jojokobi.pokemine.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.jojokobi.mcutil.generation.GenerationHandler;
import at.jojokobi.mcutil.generation.population.StructureInstance;
import at.jojokobi.pokemine.generation.PokemonGym;

public class FixedGymCommand implements CommandExecutor{
	
	public static final String COMMAND_NAME = "fixedgyms";
	
	private GenerationHandler genHandler;
	private PokemonGym gym;

	public FixedGymCommand(GenerationHandler genHandler, PokemonGym gym) {
		super();
		this.genHandler = genHandler;
		this.gym = gym;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String input, String[] args) {
		if (input.equalsIgnoreCase(COMMAND_NAME)) {
			if (sender instanceof Player && sender.isOp()) {
				World world = ((Player)sender).getWorld();
				for (PokemonGym.FixedGymPosition pos : gym.generateFixedGymPositions(world.getSeed())) {
					if (world.isChunkGenerated(pos.getChunkX(), pos.getChunkZ())) {
						sender.sendMessage("Generated " + pos.getType() + " - gym at " + pos.getChunkX() + "/" + pos.getChunkZ() + "!");
						for (StructureInstance<?> inst : gym.generate(pos.getChunk(world), world.getSeed())) {
							genHandler.addStructureInstance(inst);
						}
					}
					else {
						sender.sendMessage("The chunk " + pos.getChunkX() + "/" + pos.getChunkZ() + " hasn't been generated yet! As soon as you get there the " + pos.getType() + " - gym will be generated!");
					}
				}
				return true;
			}
			else {
				sender.sendMessage("You have to be an operator and a player to use this commmand!");
			}
		}
		return false;
	}

}
