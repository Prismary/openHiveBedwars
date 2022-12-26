package net.prismarray.openhivebedwars.commands;

import net.prismarray.openhivebedwars.OpenHiveBedwars;

public abstract class TabCompletedPluginBoundCommandExecutor extends TabCompletedCommandExecutor {

    protected final OpenHiveBedwars plugin;

    public TabCompletedPluginBoundCommandExecutor(OpenHiveBedwars plugin) {
        this.plugin = plugin;
    }
}
