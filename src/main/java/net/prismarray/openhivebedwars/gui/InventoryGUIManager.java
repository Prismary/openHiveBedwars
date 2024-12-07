package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionManager;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

public class InventoryGUIManager {

    private static final String[] DEFAULT_GUI_CONFIG_FILENAMES = new String[]{
            "npc-items-root.yml",
            "npc-items-blocks.yml",
            "npc-items-armor.yml",
            "npc-items-weapons.yml",
            "npc-items-traps.yml",
            "npc-upgrades-root.yml",
            "npc-upgrades-summoner.yml",
            "npc-upgrades-team.yml",
            //"npc-specialist-root.yml",
    };

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

    public static void openInventoryGUI(@Nullable String key, @Nonnull Player target) {

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
        InventoryGUIActionManager.registerInventoryGUI(target, inventoryGUI);
    }

    public static void createSampleConfig(File guiDirectory) {

        for (String filename : DEFAULT_GUI_CONFIG_FILENAMES) {
            try {
                File sampleConfigFile = new File(guiDirectory, filename);

                if (!sampleConfigFile.createNewFile()) {
                    OpenHiveBedwars.getInstance().getLogger().warning("Creation of sample config file failed.");
                }

                Scanner scanner = new Scanner(OpenHiveBedwars.getInstance().getResource("sample_inventories/" + filename));
                StringBuilder strb = new StringBuilder();

                while (scanner.hasNext()) {
                    strb.append(scanner.nextLine());
                    strb.append("\n");
                }

                FileWriter writer = new FileWriter(sampleConfigFile);
                writer.write(strb.toString());
                writer.close();

            } catch (IOException e) {
                OpenHiveBedwars.getInstance().getLogger().warning(
                        "Could not create sample config due to IOException: " + e.getMessage()
                );
            }
        }
    }
}
