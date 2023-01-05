package net.prismarray.openhivebedwars.commands.openhivebedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class SubCommandVersion implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        PluginDescriptionFile description = OpenHiveBedwars.getInstance().getDescription();
        sender.sendMessage(description.getName() + " " + description.getVersion());
        return true;
    }
}
