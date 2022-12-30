package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.gui.actions.InventoryGUIAction;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;

import java.util.ArrayList;
import java.util.List;

public class InventoryGUIItem {

    private final List<InventoryGUIActionHandler<? extends InventoryGUIClickAction>> actionHandlers = new ArrayList<>();

    public void addClickActionHandler(InventoryGUIActionHandler<? extends InventoryGUIClickAction> handler) {
        actionHandlers.add(handler);
    }
}
