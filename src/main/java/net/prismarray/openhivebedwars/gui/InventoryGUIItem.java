package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;

import java.util.ArrayList;
import java.util.List;

public class InventoryGUIItem {

    private final List<InventoryGUIActionListener> actionHandlers = new ArrayList<>();

    public void addClickActionHandler(InventoryGUIActionListener handler) {
        actionHandlers.add(handler);
    }
}
