package net.prismarray.openhivebedwars.gui.actions;

import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import org.bukkit.entity.Player;

import java.util.*;

public class InventoryGUIActionManager {

    private static final InventoryGUIActionManager instance = new InventoryGUIActionManager();

    private final Map<Player, InventoryGUIBase> openInventoryGUIs = new HashMap<>();

    private InventoryGUIActionManager() {}

    public static InventoryGUIActionManager getInstance() {
        return instance;
    }

    public static void registerInventoryGUI(Player player, InventoryGUIBase invGUI) {
        instance.openInventoryGUIs.put(player, invGUI);
    }

    public static boolean isRegistered(Player player) {
        return instance.openInventoryGUIs.containsKey(player);
    }

    public static boolean unregisterInventoryGUI(Player player) {
        return Objects.nonNull(instance.openInventoryGUIs.remove(player));
    }

    public static InventoryGUIBase getCorrespondingInventoryGUIBase(Player player) {
        return instance.openInventoryGUIs.get(player);
    }

    public static void handleInventoryGUIAction(InventoryGUIAction action) {
        instance.openInventoryGUIs.get(action.getPlayer()).handleAction(action);
    }
}
