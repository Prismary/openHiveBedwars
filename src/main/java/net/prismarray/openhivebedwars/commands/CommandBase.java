package net.prismarray.openhivebedwars.commands;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;

public abstract class CommandBase implements CommandExecutor {
    protected final OpenHiveBedwars plugin;

    public CommandBase(OpenHiveBedwars plugin) {
        this.plugin = plugin;
    }
}
