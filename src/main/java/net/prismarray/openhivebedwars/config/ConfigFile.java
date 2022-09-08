package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public abstract class ConfigFile {

    protected final Logger logger;
    private final File configFile;

    public ConfigFile(Logger logger, File configFile) {

        if (configFile.isDirectory()) {
            throw new IllegalArgumentException(
                    "The specified File object denotes a directory. File objects for ConfigFiles must be actual files."
            );
        }

        this.logger = logger;
        this.configFile = configFile;
    }

    /**
     * This method loads the contents of the underlying config file using the BoostedYAML library.
     *
     * @throws IOException if an error occurs in the process of reading the config file
     * @throws ConfigValidationException if an error occurs in the process of parsing or validating the config data
     */
    public void loadConfig() throws IOException, ConfigValidationException {
        this.parseAndValidateConfig(YamlDocument.create(configFile));
    }

    /**
     * This method loads the contents of the underlying config file using the BoostedYAML library and the default
     * values provided in parameter `defaults` as an InputStream.
     *
     * @param defaults InputStream containing a valid config file with default configuration options that should be
     *                 used, if they are not specified by the file to be loaded
     * @throws IOException if an error occurs in the process of reading the config file
     * @throws ConfigValidationException if an error occurs in the process of parsing or validating the config data
     */
    public void loadConfig(InputStream defaults) throws IOException, ConfigValidationException {
        this.parseAndValidateConfig(YamlDocument.create(configFile, defaults));
    }

    /**
     * This method needs to be implemented by all config classes and will be used to parse and validate the contents of
     * the content file after it has been read. Any subclass should utilize this method to  populate its data
     * attributes with configuration options from the BoostedYAML YamlDocument provided in parameter `yamlContent`.
     *
     * If the config options contained in `yamlContent` are invalid or insufficient, the implementation of this method
     * should throw a ConfigValidationException.
     *
     * @throws ConfigValidationException if a provided config file deviates from the expected contents in a way that
     * can not be compensated by omissions or default values.
     */
    protected abstract void parseAndValidateConfig(YamlDocument yamlContent) throws ConfigValidationException;
}
