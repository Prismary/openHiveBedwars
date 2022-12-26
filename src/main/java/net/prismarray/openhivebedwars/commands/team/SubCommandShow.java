package net.prismarray.openhivebedwars.commands.team;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.TeamHandler;
import net.prismarray.openhivebedwars.commands.PluginBoundCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommandShow extends PluginBoundCommandExecutor {

    public SubCommandShow(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        TeamHandler teamHandler = plugin.game.getTeamHandler();
        Player player = (Player) sender; // guaranteed to be legal due to PlayerOnlyChecker in CommandTeam

        if (!teamHandler.isInTeam(player)) {
            sender.sendMessage("§cYou're currently not in a team.");
            return true;
        }

        sender.sendMessage("§9Your current team:");
        teamHandler.getPlayerTeam(player).getPlayers().stream()
                .forEach(p -> player.sendMessage("§7" + p.getDisplayName()));
        return true;
    }
}
