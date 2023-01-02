package net.prismarray.openhivebedwars.commands.openhivebedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.commands.AdvancedCommandExecutor;
import net.prismarray.openhivebedwars.commands.CommandBase;
import net.prismarray.openhivebedwars.util.Format;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandOpenHiveBedwars extends CommandBase {

    public CommandOpenHiveBedwars(OpenHiveBedwars plugin) {

        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandHelp(), 1, "help"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandNik(), 1, "nik"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandReload(plugin), 1, "reload"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandStatus(plugin), 1, 2, "status"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandVersion(plugin), 1, "version"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandVersion(plugin), 0));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandTest(plugin), 2, "test"));
    }
}
