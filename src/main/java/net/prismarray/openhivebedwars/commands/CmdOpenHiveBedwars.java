package net.prismarray.openhivebedwars.commands;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Format;
import net.prismarray.openhivebedwars.util.GSU;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CmdOpenHiveBedwars extends CommandBase {

    public CmdOpenHiveBedwars(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            return version(sender, command, label, args);
        }

        switch (args[0]) {
            case "help":
                return help(sender, command, label, args);
            case "version":
                return version(sender, command, label, args);
            case "reload":
                return reload(sender, command, label, args);
            case "status":
                return status(sender, command, label, args);
            case "nik":
                sender.sendMessage(":skull:");
                return true;
            default:
                return false;
        }
    }

    private boolean help(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("gibt keine hilfe, good luck");
        return true;
    }

    private boolean version(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(plugin.getDescription().getName() + " " + plugin.getDescription().getVersion());
        return true;
    }

    private boolean reload(CommandSender sender, Command command, String label, String[] args) {
        plugin.onDisable();
        plugin.reloadConfig();
        plugin.onEnable();
        sender.sendMessage(Format.color(plugin.config.getPrefix() + "Reload complete."));
        return true;
    }

    private boolean status(CommandSender sender, Command command, String label, String[] args) {
        switch (args[1]) {
            case "startup":
                plugin.game.setStatus(Status.STARTUP);
                break;
            case "lobby":
                plugin.game.setStatus(Status.LOBBY);
                break;
            case "warmup":
                plugin.game.setStatus(Status.WARMUP);
                break;
            case "ingame":
                plugin.game.setStatus(Status.INGAME);
                break;
            case "concluded":
                plugin.game.setStatus(Status.CONCLUDED);
                break;
            case "results":
                plugin.game.setStatus(Status.RESULTS);
                break;
            case "shutdown":
                plugin.game.setStatus(Status.SHUTDOWN);
                break;
        }
        sender.sendMessage("Status set.");
        return true;
    }

}
