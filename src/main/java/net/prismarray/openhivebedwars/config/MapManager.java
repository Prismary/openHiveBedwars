package net.prismarray.openhivebedwars.config;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MapManager {

    private final JavaPlugin plugin;
    private final Map<String, MapConfig> mapConfigs;

    public MapManager(JavaPlugin plugin) {

        this.mapConfigs = new HashMap<>();
        this.plugin = plugin;
    }

    public void loadMaps() {

        this.mapConfigs.clear();

        File mapsDirectory = new File(this.plugin.getDataFolder(), "maps");

        createMapsDirIfNonexistent(mapsDirectory);

        File[] mapConfigFiles = getYMLFilesInDirectory(mapsDirectory);

        if (Objects.isNull(mapConfigFiles) || mapConfigFiles.length < 1) {
            this.plugin.getLogger().warning(
                    "Could not find any map configuration files in '" + mapsDirectory.getPath() + "'."
            );
            return;
        }

        String configFiles = Arrays.stream(mapConfigFiles).map(File::getName).collect(Collectors.joining(", "));
        this.plugin.getLogger().info(
                "Found the following map config files in directory '" + mapsDirectory.getPath() + "': " +
                        configFiles
        );

        for (File configFile : mapConfigFiles) {

            String mapID = FilenameUtils.removeExtension(configFile.getName());

            this.plugin.getLogger().info(
                    "Attempting to load map '" + mapID + "' from file '" + configFile.getName() + "'..."
            );

            try {
                MapConfig mapConfig = new MapConfig(this.plugin.getLogger(), configFile);
                mapConfig.loadConfig();
                mapConfigs.put(mapID, mapConfig);

                this.plugin.getLogger().info(
                        "Successfully loaded map '" + mapID + "' from file '" + configFile.getName() + "'."
                );

            } catch (ConfigValidationException | IOException e) {
                this.plugin.getLogger().warning(
                        "Could not parse config file '" + configFile.getName() + "'. Skipping map..."
                );
                this.plugin.getLogger().warning("Error message: " + e.getMessage());
            }
        }
    }

    public MapConfig getMapConfig(String mapID) {
        return mapConfigs.get(mapID);
    }

    public Map<String, MapConfig> getMapConfigs() {
        return mapConfigs;
    }

    public void clear() {
        mapConfigs.clear();
    }

    private static File[] getYMLFilesInDirectory(File directory) {

        if (!directory.isDirectory()) {
            return null;
        }

        return directory.listFiles(
                (file) -> file.isFile() && file.getName().toLowerCase().endsWith(".yml")
        );
    }

    private void createMapsDirIfNonexistent(File mapsDirectory) {

        if (!mapsDirectory.exists()) {

            this.plugin.getLogger().info(
                    "Maps directory '" + mapsDirectory.getPath() + "' does not exist. " +
                            "Creating a new one with an example config..."
            );

            try {
                if (!mapsDirectory.mkdirs()) {
                    this.plugin.getLogger().warning("Directory creation failed.");
                }
            } catch (SecurityException e) {
                this.plugin.getLogger().warning("Directory creation failed due to missing permissions:");
                this.plugin.getLogger().warning(e.getMessage());
            }

            try {
                File sampleConfigFile = new File(mapsDirectory, "sample_map.yml");

                if (!sampleConfigFile.createNewFile()) {
                    this.plugin.getLogger().warning("Creation of sample config file failed.");
                }

                Scanner scanner = new Scanner(this.plugin.getResource("sample_map.yml"));
                StringBuilder strb = new StringBuilder();

                while (scanner.hasNext()) {
                    strb.append(scanner.nextLine());
                    strb.append("\n");
                }

                FileWriter writer = new FileWriter(sampleConfigFile);
                writer.write(strb.toString());
                writer.close();

            } catch (IOException e) {
                this.plugin.getLogger().warning(
                        "Could not create sample config due to IOException: " + e.getMessage()
                );
            }
        }
    }
}
