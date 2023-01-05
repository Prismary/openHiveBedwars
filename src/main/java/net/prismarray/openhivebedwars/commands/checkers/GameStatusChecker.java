package net.prismarray.openhivebedwars.commands.checkers;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.commands.CommandChecker;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class GameStatusChecker extends CommandChecker {

    private final Status gameStatus;

    public GameStatusChecker(Status gameStatus) {
        super();

        this.gameStatus = gameStatus;
    }

    public GameStatusChecker(String errorMessage, Status gameStatus) {
        super(errorMessage);

        this.gameStatus = gameStatus;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Objects.equals(Game.getStatus(), gameStatus)) {
            sendErrorMessage(sender);
            return false;
        }

        return true;
    }
}
