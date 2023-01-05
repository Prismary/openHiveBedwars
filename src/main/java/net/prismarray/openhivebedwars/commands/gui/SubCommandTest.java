package net.prismarray.openhivebedwars.commands.gui;

import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.bedwars.gui.TestGUI;
import net.prismarray.openhivebedwars.util.Broadcast;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommandTest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender; // guaranteed to be legal due to PlayerOnlyChecker

        InventoryGUIBase inv;
        try {
            inv = new TestGUI();

        } catch (IllegalArgumentException e) {
            Broadcast.toPlayer(player, "§cCan't create inventory: §4" + e.getMessage());
            return true;
        }

        inv.open(player);
        return true;
    }
}
