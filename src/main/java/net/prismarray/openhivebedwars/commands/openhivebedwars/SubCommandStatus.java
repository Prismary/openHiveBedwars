package net.prismarray.openhivebedwars.commands.openhivebedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.commands.PluginBoundCommandExecutor;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SubCommandStatus extends PluginBoundCommandExecutor {

    public SubCommandStatus(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("§aCurrent status: §2" + plugin.game.getStatus());
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

        plugin.game.setStatus(status);
        sender.sendMessage("§aStatus set to §4" + status + "§a.");

        return true;
    }
}
