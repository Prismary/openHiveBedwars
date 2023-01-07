package net.prismarray.openhivebedwars.bedwars.shop.items;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.InventoryGUIItem;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.concurrent.Callable;

public abstract class CategorySelector extends InventoryGUIItem {

    public CategorySelector(InventoryGUIBase gui, int slot, Material material, short damage, String name, String[] lore, Callable<? extends InventoryGUIBase> destinationGUIFactory) {
        super(
                gui,
                slot,
                material,
                damage,
                1,
                name,
                getFullLore(name, lore)
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

    public static String[] getFullLore(String name, String[] lore) {
        String[] fullLore = new String[lore.length + 3];

        fullLore[0] = "";
        fullLore[lore.length + 1] = "";
        fullLore[lore.length + 2] = "§b► Click to view " + name;

        System.arraycopy(lore, 0, fullLore, 1, lore.length);

        return fullLore;
    }
}
