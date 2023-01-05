package net.prismarray.openhivebedwars.commands.openhivebedwars;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SubCommandStatus implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("§aCurrent status: §2" + Game.getStatus());
            return true;
        }

        String statusName = args[0];
        Status status;

        try {
            status = Status.valueOf(statusName.toUpperCase());

        } catch (IllegalArgumentException | NullPointerException e) {
            sender.sendMessage("§cInvalid status: §c" + statusName);
            return true;
        }

        Game.setStatus(status);
        sender.sendMessage("§aStatus set to §4" + status + "§a.");

        return true;
    }
}
