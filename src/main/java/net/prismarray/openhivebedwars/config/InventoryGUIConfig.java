package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.prismarray.openhivebedwars.gui.InventoryGUIContext;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIFramed;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.DyeColor;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;

public class InventoryGUIConfig extends ConfigFile {

    private String GUIIdentifier;

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

        // ToDo: remove debug prints
        for (Object o : yamlContent.getSection("contents").getKeys()) {
            logger.info(o.toString());
        }

        this.contents = new HashMap<>();
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

                // ToDo: add contents

                applyLockStatus(gui);

                return gui;
            };

        } else {
            return (context) -> {
                InventoryGUIBase gui = new InventoryGUIBase(title, size);

                // ToDo: add contents

                applyLockStatus(gui);

                return gui;
            };
        }
    }

    public void applyLockStatus(InventoryGUIBase gui) {

        lockedSlots.forEach(gui::lockSlot);

        if (locked) {
            gui.lock();
        }
    }

    static class InventoryGUIItemConfig {

        private InventoryGUIItemConfig(YamlDocument yamlContent) throws ConfigValidationException {
            this.parseAndValidateConfig(yamlContent);
        }

        private void parseAndValidateConfig(YamlDocument config) throws ConfigValidationException {
            // ToDo
        }
    }
}
