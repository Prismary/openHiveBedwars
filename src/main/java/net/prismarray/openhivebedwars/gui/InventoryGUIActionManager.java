package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.gui.actions.InventoryGUIAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryGUIActionManager {

    private static final InventoryGUIActionManager instance = new InventoryGUIActionManager();

    private List<InventoryGUIBase> openInventoryGUIs = new ArrayList<>();

    private InventoryGUIActionManager() {}

    public static InventoryGUIActionManager getInstance() {
        return instance;
    }

    public static void registerInventoryGUI(InventoryGUIBase invGUI) {
        instance.openInventoryGUIs.add(invGUI);
    }

    public static boolean unregisterInventoryGUI(InventoryGUIBase invGUI) {
        return instance.openInventoryGUIs.remove(invGUI);
    }

    public static void handleInventoryGUIAction(InventoryGUIAction action) {
        instance.openInventoryGUIs.stream()
                .filter(g -> Objects.equals(g, action.getInventoryGUI()))
                .forEach(g -> g.handleAction(action));
    }
}
