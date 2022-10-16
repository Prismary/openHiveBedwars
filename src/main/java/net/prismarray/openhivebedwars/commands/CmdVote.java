package net.prismarray.openhivebedwars.commands;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Broadcast;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdVote extends CommandBase {

    public CmdVote(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Forbid usage from console
        if (Bukkit.getPlayer(sender.getName()) == null) {
            System.out.println("This command can not be used from the console.");
            return true;
        }

        Player player = Bukkit.getPlayer(sender.getName());

        if (args.length == 0) {
            Broadcast.toPlayer(player, "§cPlease specify the map you want to vote for!");
            return true;
        }

        int index;
        try { // Error-check index
            index = Integer.parseInt(args[0]);
        } catch (Exception e) {
            Broadcast.toPlayer(player, "§cThe map index must be a number!");
            return true;
        }

        plugin.game.getMapVoting().vote(player, index);

        return true;
    }
}
