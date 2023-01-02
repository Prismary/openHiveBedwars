package net.prismarray.openhivebedwars.commands.openhivebedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.commands.AdvancedCommandExecutor;
import net.prismarray.openhivebedwars.commands.CommandBase;

public class CommandOpenHiveBedwars extends CommandBase {

    public CommandOpenHiveBedwars(OpenHiveBedwars plugin) {

        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandHelp(), 1, "help"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandNik(), 1, "nik"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandReload(plugin), 1, "reload"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandStatus(plugin), 1, 2, "status"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandVersion(plugin), 1, "version"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandVersion(plugin), 0));
    }
}
