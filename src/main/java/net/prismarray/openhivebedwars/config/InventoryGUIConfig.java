package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.gui.InventoryGUIContext;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIFramed;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InventoryGUIConfig extends ConfigFile {

    private final String GUIIdentifier;

    private String baseclass;
    private boolean locked;
    private Set<Integer> lockedSlots;
    private String title;
    private int size;
    private Map<Integer, InventoryGUIItemConfig> contents;

    private DyeColor frameColor;
    private boolean hasCancelButton;
    private String previousButtonDestination;
    private String nextButtonDestination;

    public InventoryGUIConfig(Logger logger, File configFile) {
        super(logger, configFile);

        this.GUIIdentifier = FilenameUtils.getBaseName(configFile.getName());
    }

    @Override
    protected void parseAndValidateConfig(YamlDocument yamlContent) throws ConfigValidationException {

        this.baseclass = parseString(yamlContent.getString("baseclass", "InventoryGUIBase"));
        this.locked = yamlContent.getBoolean("locked", false);
        this.lockedSlots = yamlContent.getIntList("locked_slots", null) == null ?
                null : new HashSet<>(yamlContent.getIntList("locked_slots", null));
        this.title = parseString(yamlContent.getString("title"));
        this.size = yamlContent.getInt("size");

        this.frameColor = parseDyeColor(yamlContent.getString("frame_color", "WHITE"));
        this.hasCancelButton = yamlContent.getBoolean("cancel_button", false);
        this.previousButtonDestination = parseString(yamlContent.getString("previous_button_destination", ""));
        this.nextButtonDestination = parseString(yamlContent.getString("previous_button_destination", ""));

        this.contents = yamlContent.getSection("contents").getKeys().stream()
                .map(o -> {
                    try {
                        return Integer.parseInt((String) o);
                    } catch (NumberFormatException | ClassCastException ignored) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Function.identity(),
                        slot -> new InventoryGUIItemConfig(yamlContent, slot)
                ));
    }

    public String getGUIIdentifier() {
        return this.GUIIdentifier;
    }

    public Function<InventoryGUIContext, ? extends InventoryGUIBase> getInventoryGUIFactroy() {
        
        if (Objects.equals(this.baseclass, "InventoryGUIFramed")) {
            return (context) -> {
                InventoryGUIFramed gui = new InventoryGUIFramed(
                        title,
                        (int) Math.ceil(size / 9.0),
                        frameColor,
                        hasCancelButton,
                        Objects.equals(previousButtonDestination, "") ? null : previousButtonDestination,
                        Objects.equals(nextButtonDestination, "") ? null : nextButtonDestination
                );

                addContents(gui, context);
                applyLockStatus(gui);

                return gui;
            };

        } else {
            return (context) -> {
                InventoryGUIBase gui = new InventoryGUIBase(title, size);

                addContents(gui, context);
                applyLockStatus(gui);

                return gui;
            };
        }
    }

    public void applyLockStatus(InventoryGUIBase gui) {

        if (Objects.nonNull(lockedSlots)) {
            lockedSlots.forEach(gui::lockSlot);
        }

        if (locked) {
            gui.lock();
        }
    }

    public void addContents(InventoryGUIBase gui, InventoryGUIContext context) {

        contents.forEach((slot, itemConfig) -> itemConfig.createGUIItem(gui, slot, context));
    }

    static class InventoryGUIItemConfig {

        private String baseclass;

        private Material material;
        private short damage;
        private int amount;
        private String name;
        private String[] lore;
        private boolean enchanted;
        private ItemFlag[] itemFlags;

        private String customHeadUrl;

        // ToDo: add fields for all custom baseclasses

        private InventoryGUIItemConfig(YamlDocument yamlContent, @Nonnull Integer slot) throws ConfigValidationException {
            try {
                this.parseAndValidateConfig(yamlContent, slot);

            } catch (ConfigValidationException e) {

                OpenHiveBedwars.getInstance().getLogger().warning(String.format("Could not parse item at slot %s. Using default placeholder...", slot));
                OpenHiveBedwars.getInstance().getLogger().warning(String.format("Error message: %s", e.getMessage()));

                baseclass = "InventoryGUICustomHead";
                customHeadUrl = "http://textures.minecraft.net/texture/bf2f871936c65aef31d715cb189271ef9113aa2eea1d7063a47c55c4692f223";

                material = Material.SKULL_ITEM;
                damage = (short) 3;
                amount = 1;
                name = "missing item";
                lore = new String[]{"could not parse item config"};
                enchanted = false;
                itemFlags = null;
            }
        }

        private void parseAndValidateConfig(YamlDocument config, @Nonnull Integer slot) throws ConfigValidationException {
            // ToDo
        }

        public InventoryGUIItem createGUIItem(InventoryGUIBase gui, Integer slot, InventoryGUIContext context) {

            // ToDo: implement all custom baseclasses
            throw new NotImplementedException();
        }
    }
}
