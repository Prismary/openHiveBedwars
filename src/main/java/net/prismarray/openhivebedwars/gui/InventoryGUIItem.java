package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryGUIItem extends ItemStack {

    private boolean isLocked = true;
    private final List<InventoryGUIActionListener> actionHandlers = new ArrayList<>();

    public void addClickActionHandler(InventoryGUIActionListener handler) {
        actionHandlers.add(handler);
    }

    public void handleClickAction(InventoryGUIClickAction action) {
        actionHandlers.forEach(
                l -> InventoryGUIBase.getMatchingHandlers(l, action).forEach(
                        m -> InventoryGUIBase.invokeMethod(m, l, action)
                )
        );
    }

    public void lock() {
        this.isLocked = true;
    }

    public void unlock() {
        this.isLocked = false;
    }

    public boolean isLocked() {
        return this.isLocked;
    }
}
