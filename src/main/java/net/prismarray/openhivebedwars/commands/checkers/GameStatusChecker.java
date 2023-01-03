package net.prismarray.openhivebedwars.commands.checkers;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.commands.CommandChecker;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class GameStatusChecker extends CommandChecker {

    private final OpenHiveBedwars plugin;
    private final Status gameStatus;

    public GameStatusChecker(OpenHiveBedwars plugin, Status gameStatus) {
        super();

        this.plugin = plugin;
        this.gameStatus = gameStatus;
    }

    public GameStatusChecker(String errorMessage, OpenHiveBedwars plugin, Status gameStatus) {
        super(errorMessage);

        this.plugin = plugin;
        this.gameStatus = gameStatus;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Objects.equals(plugin.game.getStatus(), gameStatus)) {
            sendErrorMessage(sender);
            return false;
        }

        return true;
    }
}
