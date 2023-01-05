package net.prismarray.openhivebedwars.config;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Mode;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MapManager {

    private static final MapManager instance = new MapManager();

    private final Map<String, MapConfig> mapConfigs;

    private MapManager() {
        this.mapConfigs = new HashMap<>();
    }

    public static MapManager getInstance() {
        return instance;
    }


    public static void loadMaps(Mode gameMode) {

        instance.mapConfigs.clear();

        File mapsDirectory = new File(OpenHiveBedwars.getInstance().getDataFolder(), "maps");

        createMapsDirIfNonexistent(mapsDirectory);

        File[] mapConfigFiles = getYMLFilesInDirectory(mapsDirectory);

        if (Objects.isNull(mapConfigFiles) || mapConfigFiles.length < 1) {
            OpenHiveBedwars.getInstance().getLogger().warning(
                    "Could not find any map configuration files in '" + mapsDirectory.getPath() + "'."
            );
            return;
        }

        String configFiles = Arrays.stream(mapConfigFiles).map(File::getName).collect(Collectors.joining(", "));
        OpenHiveBedwars.getInstance().getLogger().info(
                "Found the following map config files in directory '" + mapsDirectory.getPath() + "': " +
                        configFiles
        );

        for (File configFile : mapConfigFiles) {

            String mapID = FilenameUtils.removeExtension(configFile.getName());

            OpenHiveBedwars.getInstance().getLogger().info(
                    "Attempting to load map '" + mapID + "' from file '" + configFile.getName() + "'..."
            );

            try {
                MapConfig mapConfig = new MapConfig(OpenHiveBedwars.getInstance().getLogger(), configFile);
                mapConfig.loadConfig();

                if (gameMode == null || Objects.equals(mapConfig.getMode(), gameMode)) {

                    instance.mapConfigs.put(mapID, mapConfig);

                    OpenHiveBedwars.getInstance().getLogger().info(
                            "Successfully loaded map '" + mapID + "' from file '" + configFile.getName() + "'."
                    );

                } else {

                    OpenHiveBedwars.getInstance().getLogger().info(
                            "Skipping map '" + mapID + "' from file '" + configFile.getName() + "' due to Mode " +
                                    "missmatch: Map has Mode '" + mapConfig.getMode() + "', " +
                                    "expected Mode: '" + gameMode + "'"
                    );
                }

            } catch (ConfigValidationException | IOException e) {
                OpenHiveBedwars.getInstance().getLogger().warning(
                        "Could not parse config file '" + configFile.getName() + "'. Skipping map..."
                );
                OpenHiveBedwars.getInstance().getLogger().warning("Error message: " + e.getMessage());
            }
        }
    }

    public static MapConfig getMapConfig(String mapID) {
        return instance.mapConfigs.get(mapID);
    }

    public static Map<String, MapConfig> getMapConfigs() {
        return instance.mapConfigs;
    }

    public static void clear() {
        instance.mapConfigs.clear();
    }

    private static File[] getYMLFilesInDirectory(File directory) {

        if (!directory.isDirectory()) {
            return null;
        }

        return directory.listFiles(
                (file) -> file.isFile() && file.getName().toLowerCase().endsWith(".yml")
        );
    }

    private static void createMapsDirIfNonexistent(File mapsDirectory) {

        if (!mapsDirectory.exists()) {

            OpenHiveBedwars.getInstance().getLogger().info(
                    "Maps directory '" + mapsDirectory.getPath() + "' does not exist. " +
                            "Creating a new one with an example config..."
            );

            try {
                if (!mapsDirectory.mkdirs()) {
                    OpenHiveBedwars.getInstance().getLogger().warning("Directory creation failed.");
                }
            } catch (SecurityException e) {
                OpenHiveBedwars.getInstance().getLogger().warning("Directory creation failed due to missing permissions:");
                OpenHiveBedwars.getInstance().getLogger().warning(e.getMessage());
            }

            try {
                File sampleConfigFile = new File(mapsDirectory, "sample_map.yml");

                if (!sampleConfigFile.createNewFile()) {
                    OpenHiveBedwars.getInstance().getLogger().warning("Creation of sample config file failed.");
                }

                Scanner scanner = new Scanner(OpenHiveBedwars.getInstance().getResource("sample_map.yml"));
                StringBuilder strb = new StringBuilder();

                while (scanner.hasNext()) {
                    strb.append(scanner.nextLine());
                    strb.append("\n");
                }

                FileWriter writer = new FileWriter(sampleConfigFile);
                writer.write(strb.toString());
                writer.close();

            } catch (IOException e) {
                OpenHiveBedwars.getInstance().getLogger().warning(
                        "Could not create sample config due to IOException: " + e.getMessage()
                );
            }
        }
    }
}
