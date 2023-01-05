package net.prismarray.openhivebedwars.commands.vote;

import net.prismarray.openhivebedwars.commands.AdvancedCommandExecutor;
import net.prismarray.openhivebedwars.commands.CommandBase;
import net.prismarray.openhivebedwars.commands.checkers.GameStatusChecker;
import net.prismarray.openhivebedwars.commands.checkers.PlayerOnlyChecker;
import net.prismarray.openhivebedwars.util.Status;

public class CommandVote extends CommandBase {

    public CommandVote() {

        addGlobalCheck(new PlayerOnlyChecker("§cThis command can not be used from the console."));
        addGlobalCheck(new GameStatusChecker("§cUnable to change team composition at this time.", Status.LOBBY));

        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandVote(), 0, 1));
    }
}
