package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

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

        if (!mapsDirectory.exists()) {

            this.plugin.getLogger().info(
                    "Maps directory '" + mapsDirectory.getPath() + "' does not exist. " +
                            "Creating a new one with an example config..."
            );
            mapsDirectory.mkdirs();

            try {
                File sampleConfigFile = new File(mapsDirectory, "sample_map.yml");
                sampleConfigFile.createNewFile();

                Scanner scanner = new Scanner(this.plugin.getResource("sample_map.yml"));
                StringBuilder strb = new StringBuilder();

                while (scanner.hasNext()) {
                    strb.append(scanner.nextLine() + "\n");
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

        //TODO: figure out, why this doesn't load anymore...

        File[] mapConfigFiles = getYMLFilesInDirectory(mapsDirectory);

        if (Objects.isNull(mapConfigFiles) || mapConfigFiles.length < 1) {
            this.plugin.getLogger().warning(
                    "Could not find any map configuration files in '" + mapsDirectory.getPath() + "'."
            );
            return;
        }

        for (File configFile : mapConfigFiles) {

            String mapID = FilenameUtils.removeExtension(configFile.getName());

            try {
                MapConfig mapConfig = new MapConfig(this.plugin.getLogger(), configFile);
                mapConfig.loadConfig();
                mapConfigs.put(mapID, mapConfig);

            } catch (IOException e) {
                this.plugin.getLogger().warning(
                        "Could not parse config file '" + configFile.getName() + "'. Skipping..."
                );

            } catch (IllegalArgumentException e) {
                this.plugin.getLogger().warning(
                        "Could not parse config file '" + configFile.getName() + "'. Skipping..."
                );
                this.plugin.getLogger().warning("Error message: " + e.getMessage());
            }
        }
    }

    public MapConfig getMapConfig(String mapID) {
        return mapConfigs.get(mapID);
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
}
