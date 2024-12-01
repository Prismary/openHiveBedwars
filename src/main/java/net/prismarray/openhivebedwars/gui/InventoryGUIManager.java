package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

public class InventoryGUIManager {

    private static final InventoryGUIManager instance = new InventoryGUIManager();

    private final HashMap<String, Function<InventoryGUIContext, ? extends InventoryGUIBase>> registeredInventoryGUIs = new HashMap<>();

    public static boolean registerInventoryGUIFactory(@Nonnull String key, @Nonnull Function<InventoryGUIContext, ? extends InventoryGUIBase> guiFactory) {
        return registerInventoryGUIFactory(key, guiFactory, false);
    }

    public static boolean registerInventoryGUIFactory(@Nonnull String key, @Nonnull Function<InventoryGUIContext, ? extends InventoryGUIBase> guiFactory, boolean overwriteExisting) {

        if (!overwriteExisting && instance.registeredInventoryGUIs.containsKey(key)) {
            return false;
        }

        instance.registeredInventoryGUIs.put(key, guiFactory);
        return true;
    }

    public static boolean removeInventoryGUIFactory(@Nonnull String key) {
        return Objects.nonNull(instance.registeredInventoryGUIs.remove(key));
    }

    public static void clearInventoryGUIFactories() {
        instance.registeredInventoryGUIs.clear();
    }

    public static boolean hasInventoryGUI(@Nonnull String key) {
        return instance.registeredInventoryGUIs.containsKey(key);
    }

    public static void openInventoryGUI(@Nonnull String key, @Nonnull Player target) {

        Function<InventoryGUIContext, ? extends InventoryGUIBase> factory = instance.registeredInventoryGUIs.get(key);

        if (Objects.isNull(factory)) {
            return;
        }

        InventoryGUIBase inventoryGUI;
        try {
            inventoryGUI = factory.apply(new InventoryGUIContext(target));
        } catch (Exception ignored) {
            return;
        }

        if (Objects.isNull(inventoryGUI)) {
            return;
        }

        target.openInventory(inventoryGUI);
    }
}
