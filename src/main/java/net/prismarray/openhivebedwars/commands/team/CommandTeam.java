package net.prismarray.openhivebedwars.commands.team;

import net.prismarray.openhivebedwars.commands.AdvancedCommandExecutor;
import net.prismarray.openhivebedwars.commands.CommandBase;
import net.prismarray.openhivebedwars.commands.checkers.GameStatusChecker;
import net.prismarray.openhivebedwars.commands.checkers.PlayerOnlyChecker;
import net.prismarray.openhivebedwars.util.Status;


public class CommandTeam extends CommandBase {

    public CommandTeam() {

        addGlobalCheck(new PlayerOnlyChecker("§cThis command can not be used from the console."));
        addGlobalCheck(new GameStatusChecker("§cUnable to change team composition at this time.", Status.LOBBY));

        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandAccept(), 1, 2, "accept"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandInvite(), 2, "invite"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandLeave(), 1, "leave"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandShow(), 1, "show"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandShow(), 0));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandInvite(), 1));
    }
}
