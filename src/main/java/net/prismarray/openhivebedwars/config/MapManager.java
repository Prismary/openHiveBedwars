package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapManager {

    private final JavaPlugin plugin;
    private Map<String, YamlDocument> mapConfigs;

    public MapManager(JavaPlugin plugin) {

        this.mapConfigs = new HashMap<>();
        this.plugin = plugin;
    }

    public void loadMaps() {

        this.mapConfigs.clear();

        File[] mapConfigFiles = getYMLFilesInDirectory(this.plugin.getDataFolder());

        if (Objects.isNull(mapConfigFiles) || mapConfigFiles.length < 1) {
            return;
        }

        for (File configFile : mapConfigFiles) {
            YamlDocument.create(configFile, this.plugin.getResource("sample_map.yml"));
            //TODO: continue here
        }
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
