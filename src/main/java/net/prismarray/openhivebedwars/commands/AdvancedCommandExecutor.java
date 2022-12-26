package net.prismarray.openhivebedwars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class AdvancedCommandExecutor implements CommandExecutor {

    private CommandExecutor executor;
    private int minimumArguments;
    private int maximumArguments;
    private String subCommandLabel;


    /**
     * Constructs a new AdvancedCommandExecutor object that will apply no additional filters and match any
     * given command.
     */
    public AdvancedCommandExecutor(CommandExecutor executor) {
        this(executor, -1);
    }

    /**
     * Constructs a new AdvancedCommandExecutor object that is able to filter commands by number of arguments.
     *
     * @param arguments       Specifies the exact number of arguments required and allowed in order for this
     *                        AdvancedCommandExecutor to match a given command.
     */
    public AdvancedCommandExecutor(CommandExecutor executor, int arguments) {
        this(executor, arguments, arguments, null);
    }

    /**
     * Constructs a new AdvancedCommandExecutor object that is able to filter commands by number of arguments (upper
     * and lower limit).
     *
     * @param minimumArguments Specifies the minimum number of arguments required for this AdvancedCommandExecutor to
     *                         match a given command. Negative values will result in no lower limit.
     * @param maximumArguments Specifies the maximum number of arguments allowed for this AdvancedCommandExecutor to
     *                         match a given command. Negative values will result in no upper limit (Integer.MAX_VALUE).
     */
    public AdvancedCommandExecutor(CommandExecutor executor, int minimumArguments, int maximumArguments) {
        this(executor, minimumArguments, maximumArguments, null);
    }

    /**
     * Constructs a new AdvancedCommandExecutor object that is able to filter commands by sub command label and
     * number of arguments.
     *
     * @param arguments       Specifies the exact number of arguments required and allowed in order for this
     *                        AdvancedCommandExecutor to match a given command.
     * @param subCommandLabel Specifies a String that a given command must contain as the first argument in order to
     *                        be matched by this AdvancedCommandExecutor. The comparison is case-insensitive and takes
     *                        precedence over the limits on the number of arguments.
     */
    public AdvancedCommandExecutor(CommandExecutor executor, int arguments, String subCommandLabel) {
        this(executor, arguments, arguments, subCommandLabel);
    }

    /**
     * Constructs a new AdvancedCommandExecutor object that is able to filter commands by sub command label and
     * number of arguments (upper and lower limit).
     *
     * @param minimumArguments Specifies the minimum number of arguments required for this AdvancedCommandExecutor to
     *                         match a given command. Negative values will result in no lower limit.
     * @param maximumArguments Specifies the maximum number of arguments allowed for this AdvancedCommandExecutor to
     *                         match a given command. Negative values will result in no upper limit (Integer.MAX_VALUE).
     * @param subCommandLabel  Specifies a String that a given command must contain as the first argument in order to
     *                         be matched by this AdvancedCommandExecutor. The comparison is case-insensitive and takes
     *                         precedence over the limits on the number of arguments.
     */
    public AdvancedCommandExecutor(CommandExecutor executor, int minimumArguments, int maximumArguments, String subCommandLabel) {

        if (maximumArguments >= 0 && maximumArguments < minimumArguments) {
            throw new IllegalArgumentException(
                    "Maximum number of arguments must be greater or equal to the minumum number of arguments."
            );
        }

        this.executor = executor;
        this.minimumArguments = Math.max(minimumArguments, 0);
        this.maximumArguments = maximumArguments < 0 ? Integer.MAX_VALUE : maximumArguments;
        this.subCommandLabel = subCommandLabel;
    }


    /**
     * Applies the AdvancedCommandExecutor's filters for sub command label and number of arguments, if specified,
     * and returns the result as a boolean value.
     *
     * @param sender  CommandSender object
     * @param command Command object
     * @param label   label or alias of the command
     * @param args    arguments of the command
     * @return        true, if all filters match the given command; false otherwise
     */
    public boolean matchesCommand(CommandSender sender, Command command, String label, String[] args) {

        if (
                Objects.nonNull(subCommandLabel)
                        && (args.length == 0 || !args[0].equalsIgnoreCase(subCommandLabel))
        ) {
            return false;
        }

        return args.length >= minimumArguments && args.length <= maximumArguments;
    }

    public boolean isSubCommand() {
        return Objects.nonNull(subCommandLabel);
    }

    public boolean hasLowerArgumentLimit() {
        return minimumArguments > 0;
    }

    public boolean hasUpperArgumentLimit() {
        return maximumArguments >= 0 && maximumArguments < Integer.MAX_VALUE;
    }

    public boolean hasArgumentLimit() {
        return hasLowerArgumentLimit() || hasUpperArgumentLimit();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return executor.onCommand(sender, command, label, args);
    }

    @Override
    public String toString() {
        return "AdvancedCommandExecutor{" +
                "executor=" + executor +
                ", minimumArguments=" + minimumArguments +
                ", maximumArguments=" + maximumArguments +
                ", subCommandLabel='" + subCommandLabel + '\'' +
                '}';
    }
}
