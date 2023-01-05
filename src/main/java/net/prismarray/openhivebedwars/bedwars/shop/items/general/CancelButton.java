package net.prismarray.openhivebedwars.bedwars.shop.items.general;

import net.prismarray.openhivebedwars.gui.InventoryGUIItem;
import org.bukkit.Material;

public class CancelButton extends InventoryGUIItem {

    public CancelButton() {
        super(
                Material.BARRIER,
                1,
                "§c§lCancel",
                new String[]{"", "§7Click here to", "§7close this", "§7menu."}
        );
    }
}
