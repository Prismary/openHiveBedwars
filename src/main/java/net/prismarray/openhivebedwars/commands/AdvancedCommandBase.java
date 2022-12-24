package net.prismarray.openhivebedwars.commands;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AdvancedCommandBase extends SimpleCommandBase {

    private final HashMap<String, CommandExecutor> subCommands = new HashMap<>();
    private final List<String> playerOnlyCommands = new ArrayList<>();
    private final List<String> nonPlayerOnlyCommands = new ArrayList<>();

    private String defaultSubCommand;

    public AdvancedCommandBase(OpenHiveBedwars plugin) {
        super(plugin);
        
        this.defaultSubCommand = null;
    }

    protected void addSubCommand(String subCommand, CommandExecutor executor) {
        this.addSubCommand(subCommand, executor, false, false);
    }

    protected void addSubCommand(String subCommand, CommandExecutor executor, boolean isPlayerOnly, boolean isNonPlayerOnly) {

        if (isPlayerOnly && isNonPlayerOnly) {
            throw new IllegalArgumentException(
                    "A sub-command can not be restricted to players and non-player entities simultaneously."
            );
        }

        this.subCommands.put(subCommand, executor);

        if (isPlayerOnly) {
            this.playerOnlyCommands.add(subCommand);
        }

        if (isNonPlayerOnly) {
            this.nonPlayerOnlyCommands.add(subCommand);
        }
    }

    protected void setDefaultSubCommand(String subCommand) {

        subCommand = subCommand.toLowerCase();

        if (!this.subCommands.containsKey(subCommand)) {
            throw new IllegalArgumentException(
                    String.format("The specified sub-command '%s' does not exist.", subCommand)
            );
        }

        this.defaultSubCommand = subCommand;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String executedCommand = this.defaultSubCommand;

        if (args.length > 0) {
            executedCommand = args[0].toLowerCase();
        }

        if (Objects.isNull(executedCommand) || !this.subCommands.containsKey(executedCommand)) {
            return false;
        }

        if ((sender instanceof Player) && this.nonPlayerOnlyCommands.contains(executedCommand)) {
            sender.sendMessage("§cThis command may only be executed by non-player entities.");
            return true;
        }

        if (!(sender instanceof Player) && this.playerOnlyCommands.contains(executedCommand)) {
            sender.sendMessage("§cThis command may only be executed by player entities.");
            return true;
        }

        return this.subCommands.get(executedCommand).onCommand(sender, command, label, args);
    }
}
