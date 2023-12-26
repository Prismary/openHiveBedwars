package net.prismarray.openhivebedwars.commands.openhivebedwars;

import net.prismarray.openhivebedwars.bedwars.bridgebuilder.BridgeBuilderItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Objects;

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

        for (byte i = 0; i < 16; i++) {
            BridgeBuilderItem bbItem = new BridgeBuilderItem(material, 32, i);
            bbItem.setAmount(8);
            Map<Integer, ItemStack> notAdded = p.getInventory().addItem(bbItem);

            if (!notAdded.isEmpty()) {
                p.getLocation().getWorld().dropItemNaturally(p.getLocation(), bbItem);
            }

            if (!Objects.equals(material, Material.WOOL) && !Objects.equals(material, Material.STAINED_CLAY)) {
                break;
            }
        }

        return true;
    }
}
