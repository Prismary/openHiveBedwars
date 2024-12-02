package net.prismarray.openhivebedwars.gui.components;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.gui.InventoryGUIManager;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.components.InventoryGUICustomHead;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import org.bukkit.Bukkit;


public class PreviousButton extends InventoryGUICustomHead {

    public PreviousButton(InventoryGUIBase gui, int slot, String destinationGUI) {
        super(
                gui,
                slot,
                "http://textures.minecraft.net/texture/49b2bee39b6ef47e182d6f1dca9dea842fcd68bda9bacc6a6d66a8dcdf3ec",
                1,
                "§6§lPrevious Page"
        );

        addActionListenerToContainingInventory(new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUIClickAction a) {
                Bukkit.getScheduler().runTask(OpenHiveBedwars.getInstance(), () -> {
                    try {
                        InventoryGUIManager.openInventoryGUI(destinationGUI, a.getPlayer());
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                });
            }
        });
    }
}
