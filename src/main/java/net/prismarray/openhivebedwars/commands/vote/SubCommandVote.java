package net.prismarray.openhivebedwars.commands.vote;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.util.Broadcast;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommandVote implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender; // guaranteed to be legal due to PlayerOnlyChecker

        if (args.length == 0) {
            Broadcast.toPlayer(player, "§cPlease specify the number of the map you want to vote for!");
            return true;
        }

        int index;
        try {
            index = Integer.parseInt(args[0]);

        } catch (NumberFormatException e) {
            Broadcast.toPlayer(player, "§cThe map index must be a number!");
            return true;
        }

        Game.getMapVoting().vote(player, index);
        Broadcast.toPlayer(player, "§aYou voted for map §2#" + index + "§a.");

        return true;
    }
}
