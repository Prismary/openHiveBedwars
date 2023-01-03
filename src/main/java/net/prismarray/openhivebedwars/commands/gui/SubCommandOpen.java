package net.prismarray.openhivebedwars.commands.gui;

import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Broadcast;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SubCommandOpen implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender; // guaranteed to be legal by PlayerOnlyChecker

        int invSize = 3 * 9;
        try {
            invSize = Integer.parseInt(args[0]);
            args = Arrays.stream(args).skip(1).toArray(String[]::new);
        } catch (IndexOutOfBoundsException | NumberFormatException ignored) {}

        String title = null;
        if (args.length > 0) {
            title = String.join(" ", args);
        }

        InventoryGUIBase inv;
        try {
            inv = new InventoryGUIBase(title, invSize);

        } catch (IllegalArgumentException e) {
            Broadcast.toPlayer(player, "§cCan't create inventory: §4" + e.getMessage());
            return true;
        }

        inv.open(player);
        return true;
    }
}
