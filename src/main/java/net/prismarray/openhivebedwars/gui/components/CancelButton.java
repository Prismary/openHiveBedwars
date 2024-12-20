package net.prismarray.openhivebedwars.gui.components;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CancelButton extends InventoryGUIItem {

    public CancelButton(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.BARRIER,
                1,
                "§c§lCancel",
                Stream.of(
                        "",
                        "§7Click here to",
                        "§7close this",
                        "§7menu."
                ).collect(Collectors.toList())
        );

        addActionListenerToContainingInventory(new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUIClickAction a) {
                Bukkit.getScheduler().runTask(OpenHiveBedwars.getInstance(), () -> a.getPlayer().closeInventory());
            }
        });
    }
}
