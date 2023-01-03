package net.prismarray.openhivebedwars.commands.openhivebedwars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SubCommandTest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Game.teamSummoners.get(0).upgrade(Integer.parseInt(args[0]));
        return true;
    }
}
