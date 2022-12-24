package net.prismarray.openhivebedwars.commands;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Format;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CmdOpenHiveBedwars extends AdvancedCommandBase {


    public CmdOpenHiveBedwars(OpenHiveBedwars plugin) {
        super(plugin);

        addSubCommand("help", this::help);
        addSubCommand("version", this::version);
        addSubCommand("reload", this::reload);
        addSubCommand("status", this::status);
        addSubCommand("nik", this::nik);

        setDefaultSubCommand("version");
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
            case "confirmation":
                plugin.game.setStatus(Status.CONFIRMATION);
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

    private boolean nik(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(":skull:");
        return true;
    }
}
