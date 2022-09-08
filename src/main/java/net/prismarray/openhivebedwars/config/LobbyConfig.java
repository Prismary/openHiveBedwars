package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.util.logging.Logger;

public class LobbyConfig extends ConfigFile {

    /*
    TODO: implement the following config options:

        - lobby player spawn
        - scoreboard location
        - results player spawn
        - podium gold location
        - podium silver location
        - podium bronze location
     */

    //TODO: instantiate this config in OpenHiveBedwars (on enable)

    public LobbyConfig(Logger logger, File configFile) {
        super(logger, configFile);
    }

    @Override
    protected void parseAndValidateConfig(YamlDocument yamlContent) throws ConfigValidationException {

    }
}
