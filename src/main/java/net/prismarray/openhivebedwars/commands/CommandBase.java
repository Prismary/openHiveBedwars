package net.prismarray.openhivebedwars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommandBase implements CommandExecutor {

    private final List<AdvancedCommandExecutor> executors = new ArrayList<>();
    private final List<CommandExecutor> globalCheckers = new ArrayList<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        AdvancedCommandExecutor subCommandMatch = executors.stream()
                .filter(AdvancedCommandExecutor::isSubCommand)
                .filter(e -> e.matchesCommand(sender, command, label, args))
                .findFirst()
                .orElse(null);

        AdvancedCommandExecutor topLevelCommandMatch = executors.stream()
                .filter(e -> !e.isSubCommand())
                .filter(e -> e.matchesCommand(sender, command, label, args))
                .findFirst()
                .orElse(null);

        AdvancedCommandExecutor match = Objects.nonNull(subCommandMatch) ? subCommandMatch : topLevelCommandMatch;

        if (Objects.isNull(match)) {
            return false;
        }

        if (globalCheckers.stream().anyMatch(c -> !c.onCommand(sender, command, label, args))) {
            return false;
        }

        if (!match.isSubCommand()) {
            return match.onCommand(sender, command, label, args);
        }

        String[] newArgs = Arrays.stream(args).skip(1).toArray(String[]::new);
        return match.onCommand(sender, command, label, newArgs);
    }

    protected void addCommandExecutor(AdvancedCommandExecutor executor) {
        executors.add(executor);
    }

    protected void addGlobalCheck(CommandExecutor checker) {
        globalCheckers.add(checker);
    }
}
