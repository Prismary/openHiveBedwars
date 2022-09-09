package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.prismarray.openhivebedwars.util.Mode;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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


    public static String parseString(String input) {

        if (Objects.isNull(input)) {
            throw new ConfigValidationException("Could not parse null-String.");
        }

        return input;
    }

    public static Mode parseMode(String input) {

        if (Objects.isNull(input)) {
            throw new ConfigValidationException("Could not parse null-Mode.");
        }

        return Mode.valueOf(input.toUpperCase());
    }

    public static Set<Location> parseLocationSet(List<String> input) throws ConfigValidationException {

        if (Objects.isNull(input)) {
            throw new ConfigValidationException("Could not parse null-List of locations.");
        }

        if (input.isEmpty()) {
            throw new ConfigValidationException("Could not parse empty List of locations.");
        }

        return input.stream().map(ConfigFile::parseLocation).collect(Collectors.toSet());
    }

    public static Location parseLocation(String input) throws ConfigValidationException {

        if (Objects.isNull(input)) {
            throw new ConfigValidationException("Could not parse null-Location.");
        }

        Pattern pattern = Pattern.compile("(-?\\d+),(-?\\d+),(-?\\d+)(?:;(-?\\d+)(?:,(-?\\d+))?)?");
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new ConfigValidationException(
                    "Location '" + input + "' could not be parsed. Make sure, location entries are formatted " +
                            "like '0,42,-3', '0,42,-3;180' or '0,42,-3;180,-90' (x,y,z[;yaw[,pitch]])"
            );
        }

        Location location;

        try {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            int z = Integer.parseInt(matcher.group(3));

            if (Objects.nonNull(matcher.group(4))) {

                int yaw = Integer.parseInt(matcher.group(4));
                yaw = (yaw % 360 + 360) % 360;

                int pitch = 0;

                if (Objects.nonNull(matcher.group(5))) {
                    pitch = Integer.parseInt(matcher.group(5));
                    pitch = Math.max(-90, Math.min(pitch, 90));
                }

                location = new Location(null, x, y, z, yaw, pitch);

            } else {

                location = new Location(null, x, y, z);
            }

        } catch (NumberFormatException e) {
            throw new ConfigValidationException(
                    "Location '" + input + "' could not be parsed. At least one coordinate is not a valid number."
            );
        }

        return location;
    }

    public static Material parseMaterial(String input) throws ConfigValidationException {

        Material material = Material.getMaterial(input.toUpperCase());

        if (Objects.isNull(material)) {
            throw new ConfigValidationException("Could not parse material '" + input + "'.");
        }

        return material;
    }

    public static Set<Material> parseMaterialSet(List<String> input) throws ConfigValidationException {

        if (Objects.isNull(input)) {
            throw new ConfigValidationException("Could not parse null-list of Materials.");
        }

        return input.stream().map(ConfigFile::parseMaterial).collect(Collectors.toSet());
    }
}
