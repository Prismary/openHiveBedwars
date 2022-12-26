package net.prismarray.openhivebedwars.commands.checkers;

import net.prismarray.openhivebedwars.commands.CommandChecker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NonPlayerOnlyChecker extends CommandChecker {

    public NonPlayerOnlyChecker() {
        super();
    }

    public NonPlayerOnlyChecker(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if ((sender instanceof Player)) {
            sendErrorMessage(sender);
            return false;
        }

        return true;
    }
}
