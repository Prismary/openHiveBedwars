package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public abstract class ConfigFile {

    protected final Logger logger;
    private final File configFile;
    protected YamlDocument yamlContent;

    public ConfigFile(Logger logger, File configFile) throws IOException {

        this.logger = logger;
        this.configFile = configFile;
    }

    public void loadConfig() throws IOException {
        this.yamlContent = YamlDocument.create(configFile);
        this.parseAndValidateConfig();
    }

    public void loadConfig(InputStream defaults) throws IOException {
        this.yamlContent = YamlDocument.create(configFile, defaults);
        this.parseAndValidateConfig();
    }

    protected abstract void parseAndValidateConfig() throws IllegalArgumentException;
}
