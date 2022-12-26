package net.prismarray.openhivebedwars.commands;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.command.CommandExecutor;

public abstract class PluginBoundCommandExecutor implements CommandExecutor {

    protected final OpenHiveBedwars plugin;

    public PluginBoundCommandExecutor(OpenHiveBedwars plugin) {
        this.plugin = plugin;
    }
}
