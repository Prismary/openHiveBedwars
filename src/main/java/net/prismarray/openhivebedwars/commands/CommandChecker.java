package net.prismarray.openhivebedwars.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public abstract class CommandChecker implements CommandExecutor {

    String errorMessage;

    public CommandChecker() {
        this(null);
    }

    public CommandChecker(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    protected void sendErrorMessage(CommandSender sender) {

        if (Objects.isNull(errorMessage)) {
            return;
        }

        sender.sendMessage(errorMessage);
    }
}
