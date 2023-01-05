package net.prismarray.openhivebedwars.commands.openhivebedwars;

import net.prismarray.openhivebedwars.bedwars.bridgebuilder.BridgeBuilderItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class SubCommandTest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;

        Material material = Material.WOOL;
        if (args.length >= 1) {
            try {
                material = Material.getMaterial(args[0]);
            } catch(IllegalArgumentException ignored) {}
        }

        BridgeBuilderItem bbItem = new BridgeBuilderItem(material, 32);
        bbItem.setAmount(64);
        Map<Integer, ItemStack> notAdded = p.getInventory().addItem(bbItem);

        if (!notAdded.isEmpty()) {
            p.getLocation().getWorld().dropItemNaturally(p.getLocation(), bbItem);
        }

        return true;
    }
}
