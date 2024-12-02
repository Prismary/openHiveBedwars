package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.prismarray.openhivebedwars.gui.InventoryGUIContext;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import org.bukkit.DyeColor;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;

public class InventoryGUIConfig extends ConfigFile {

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
            System.out.println(o.toString());
        }

        this.contents = new HashMap<>();
    }

    public Function<InventoryGUIContext, ? extends InventoryGUIBase> getInventoryGUIFactroy() {
        // ToDo
        return null;
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
