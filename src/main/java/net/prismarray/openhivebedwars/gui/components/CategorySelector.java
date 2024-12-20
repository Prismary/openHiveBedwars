package net.prismarray.openhivebedwars.gui.components;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.gui.InventoryGUIManager;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import net.prismarray.openhivebedwars.util.ItemNameBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.List;

public class CategorySelector extends InventoryGUIItem {

    public CategorySelector(InventoryGUIBase gui, int slot, Material material, short damage, String name, List<String> lore, String destinationGUI) {
        super(
                gui,
                slot,
                material,
                damage,
                1,
                name,
                ItemNameBuilder.getCategorySelectorLore(name, lore)
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
