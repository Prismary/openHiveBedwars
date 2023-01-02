package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.gui.actions.InventoryGUIAction;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class InventoryGUIActionManager {

    private static final InventoryGUIActionManager instance = new InventoryGUIActionManager();

    private final Map<Inventory, InventoryGUIBase> openInventoryGUIs = new HashMap<>();

    private InventoryGUIActionManager() {}

    public static InventoryGUIActionManager getInstance() {
        return instance;
    }

    public static void registerInventoryGUI(InventoryGUIBase invGUI) {
        instance.openInventoryGUIs.put(invGUI.getInventory(), invGUI);
    }

    public static boolean isRegistered(Inventory inventory) {
        return instance.openInventoryGUIs.containsKey(inventory);
    }

    public static boolean isRegistered(InventoryGUIBase invGUI) {
        return instance.openInventoryGUIs.containsKey(invGUI.getInventory());
    }

    public static boolean unregisterInventoryGUI(InventoryGUIBase invGUI) {
        return Objects.nonNull(instance.openInventoryGUIs.remove(invGUI.getInventory()));
    }

    public static InventoryGUIBase getCorrespondingInventoryGUIBase(Inventory inventory) {
        return instance.openInventoryGUIs.get(inventory);
    }

    public static void handleInventoryGUIAction(InventoryGUIAction action) {
        instance.openInventoryGUIs.get(action.getInventoryGUI().getInventory()).handleAction(action);
    }
}
