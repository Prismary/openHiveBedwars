package net.prismarray.openhivebedwars.commands.team;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Team;
import net.prismarray.openhivebedwars.bedwars.TeamHandler;
import net.prismarray.openhivebedwars.commands.PluginBoundCommandExecutor;
import net.prismarray.openhivebedwars.util.Broadcast;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SubCommandLeave extends PluginBoundCommandExecutor {

    public SubCommandLeave(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        TeamHandler teamHandler = plugin.game.getTeamHandler();

        Player player = (Player) sender; // guaranteed to be legal due to PlayerOnlyChecker
        Team playerTeam = teamHandler.getPlayerTeam(player);

        if (Objects.isNull(playerTeam)) {
            player.sendMessage("§cYou are not currently in a team.");
            return true;
        }

        if (!teamHandler.removePlayer(player)) {
            player.sendMessage("§cUnable to remove you from your team.");
            return true;
        }

        player.sendMessage("§cYou have left your team.");
        Broadcast.broadcastPlayerLeave(playerTeam, player);
        plugin.game.getTeamHandler().tryDissolution(playerTeam);

        return true;
    }
}
