package net.prismarray.openhivebedwars.commands.openhivebedwars;

import net.prismarray.openhivebedwars.commands.AdvancedCommandExecutor;
import net.prismarray.openhivebedwars.commands.CommandBase;

public class CommandOpenHiveBedwars extends CommandBase {

    public CommandOpenHiveBedwars() {

        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandHelp(), 1, "help"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandNik(), 1, "nik"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandReload(), 1, "reload"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandStatus(), 1, 2, "status"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandVersion(), 1, "version"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandVersion(), 0));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandTest(), 2, "test"));
    }
}
