package net.prismarray.openhivebedwars.commands.vote;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.commands.AdvancedCommandExecutor;
import net.prismarray.openhivebedwars.commands.CommandBase;
import net.prismarray.openhivebedwars.commands.checkers.GameStatusChecker;
import net.prismarray.openhivebedwars.commands.checkers.PlayerOnlyChecker;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandVote extends CommandBase {

    public CommandVote(OpenHiveBedwars plugin) {

        addGlobalCheck(new PlayerOnlyChecker("§cThis command can not be used from the console."));
        addGlobalCheck(new GameStatusChecker("§cUnable to change team composition at this time.", plugin, Status.LOBBY));

        addCommandExecutor(new AdvancedCommandExecutor(new SubCommandVote(plugin), 0, 1));
    }
}
