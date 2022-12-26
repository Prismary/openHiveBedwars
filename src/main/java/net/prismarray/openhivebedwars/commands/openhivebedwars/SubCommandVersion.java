package net.prismarray.openhivebedwars.commands.openhivebedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.commands.PluginBoundCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SubCommandVersion extends PluginBoundCommandExecutor {

    public SubCommandVersion(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage(plugin.getDescription().getName() + " " + plugin.getDescription().getVersion());
        return true;
    }
}
