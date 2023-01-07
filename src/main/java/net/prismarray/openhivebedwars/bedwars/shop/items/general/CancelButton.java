package net.prismarray.openhivebedwars.bedwars.shop.items.general;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.InventoryGUIItem;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class CancelButton extends InventoryGUIItem {

    public CancelButton(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.BARRIER,
                1,
                "§c§lCancel",
                new String[]{"", "§7Click here to", "§7close this", "§7menu."}
        );

        addActionListenerToContainingInventory(new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUIClickAction a) {
                Bukkit.getScheduler().runTask(OpenHiveBedwars.getInstance(), () -> a.getPlayer().closeInventory());
            }
        });
    }
}
