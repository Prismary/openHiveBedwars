package net.prismarray.openhivebedwars.bedwars.shop.items.general;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.InventoryGUICustomHead;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import org.bukkit.Bukkit;

import java.util.concurrent.Callable;

public class PreviousButton extends InventoryGUICustomHead {

    public PreviousButton(InventoryGUIBase gui, int slot, Callable<? extends InventoryGUIBase> destinationGUIFactory) {
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
                        destinationGUIFactory.call().open(a.getPlayer());
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                });
            }
        });
    }
}
