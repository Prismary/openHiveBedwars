package net.prismarray.openhivebedwars.commands.openhivebedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Format;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SubCommandReload implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        OpenHiveBedwars.getInstance().onDisable();
        OpenHiveBedwars.getInstance().reloadConfig();
        OpenHiveBedwars.getInstance().onEnable();
        sender.sendMessage(Format.color(OpenHiveBedwars.getBWConfig().getPrefix() + "Reload complete."));
        return true;
    }
}
