package net.prismarray.openhivebedwars.commands.team;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.commands.AdvancedCommandExecutor;
import net.prismarray.openhivebedwars.commands.CommandBase;
import net.prismarray.openhivebedwars.commands.checkers.GameStatusChecker;
import net.prismarray.openhivebedwars.commands.checkers.PlayerOnlyChecker;
import net.prismarray.openhivebedwars.util.Status;


public class CommandTeam extends CommandBase {

    public CommandTeam(OpenHiveBedwars plugin) {

        addGlobalCheck(new PlayerOnlyChecker("§cThis command can not be used from the console."));
        addGlobalCheck(new GameStatusChecker("§cUnable to change team composition at this time.", plugin, Status.LOBBY));

        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandAccept(plugin), 1, 2, "accept"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandInvite(plugin), 2, "invite"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandLeave(plugin), 1, "leave"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandShow(plugin), 1, "show"));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandShow(plugin), 0));
        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandInvite(plugin), 1));

    }
}
