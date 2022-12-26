package net.prismarray.openhivebedwars.commands.openhivebedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.commands.PluginBoundCommandExecutor;
import net.prismarray.openhivebedwars.util.Format;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SubCommandReload extends PluginBoundCommandExecutor {

    public SubCommandReload(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        plugin.onDisable();
        plugin.reloadConfig();
        plugin.onEnable();
        sender.sendMessage(Format.color(plugin.config.getPrefix() + "Reload complete."));
        return true;
    }
}
