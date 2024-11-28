package net.prismarray.openhivebedwars.bedwars.shop.items.general;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.InventoryGUICustomHead;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import org.bukkit.Bukkit;

import java.util.concurrent.Callable;


public class NextButton extends InventoryGUICustomHead {

    public NextButton(InventoryGUIBase gui, int slot, Callable<? extends InventoryGUIBase> destinationGUIFactory) {
        super(
                gui,
                slot,
                "http://textures.minecraft.net/texture/141ff6bc67a481232d2e669e43c4f087f9d2306665b4f829fb86892d13b70ca",
                1,
                "§6§lNext Page"
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

