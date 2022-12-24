package net.prismarray.openhivebedwars.commands;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.command.CommandExecutor;

public abstract class SimpleCommandBase implements CommandExecutor {

    protected final OpenHiveBedwars plugin;

    public SimpleCommandBase(OpenHiveBedwars plugin) {
        this.plugin = plugin;
    }
}
