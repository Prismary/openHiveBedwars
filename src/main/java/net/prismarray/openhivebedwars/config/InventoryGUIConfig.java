package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.util.logging.Logger;

public class InventoryGUIConfig extends ConfigFile {

    public InventoryGUIConfig(Logger logger, File configFile) {
        super(logger, configFile);
    }

    @Override
    protected void parseAndValidateConfig(YamlDocument yamlContent) throws ConfigValidationException {
        // ToDo: implement
    }
}
