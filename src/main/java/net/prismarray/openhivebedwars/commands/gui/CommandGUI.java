package net.prismarray.openhivebedwars.commands.gui;

import net.prismarray.openhivebedwars.commands.AdvancedCommandExecutor;
import net.prismarray.openhivebedwars.commands.CommandBase;
import net.prismarray.openhivebedwars.commands.checkers.PlayerOnlyChecker;

public class CommandGUI extends CommandBase {

    public CommandGUI() {

        addGlobalCheck(new PlayerOnlyChecker("§cThis command can only be executed by a player."));

        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandOpen(), 1, -1, "open"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandTest(), 1, "test"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandOpen(), 0, -1));
    }
}
