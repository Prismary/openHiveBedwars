package net.prismarray.openhivebedwars.config;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Mode;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    private OpenHiveBedwars plugin;
    private FileConfiguration config;

    public Config(OpenHiveBedwars plugin) {
        this.plugin = plugin;

        restoreConfig();

        readConfig();
    }

    public void restoreConfig() {
        plugin.saveDefaultConfig();
        plugin.getConfig().options().copyDefaults(true);
    }

    public void readConfig() {
        config = plugin.getConfig();
    }


    // Get config values
    public Mode getMode() {
        switch (config.getString("mode")) {
            case "solo":
                return Mode.SOLO;
            case "duos":
                return Mode.DUOS;
            case "teams":
                return Mode.TEAMS;
        }
        // TODO: throw error
        return Mode.TEAMS;
    }

    public String getPrefix() {
        return config.getString("prefix");
    }
}
