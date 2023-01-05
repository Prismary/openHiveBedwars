package net.prismarray.openhivebedwars.commands.team;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.TeamHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommandShow implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        TeamHandler teamHandler = Game.getTeamHandler();
        Player player = (Player) sender; // guaranteed to be legal due to PlayerOnlyChecker in CommandTeam

        if (!teamHandler.isInTeam(player)) {
            sender.sendMessage("§cYou're currently not in a team.");
            return true;
        }

        sender.sendMessage("§9Your current team:");
        teamHandler.getPlayerTeam(player).getPlayers().forEach(p -> player.sendMessage("§7" + p.getDisplayName()));
        return true;
    }
}
