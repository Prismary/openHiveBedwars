package net.prismarray.openhivebedwars.config;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Mode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

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

    public boolean mergeTeams() {
        return config.getBoolean("merge-teams");
    }

    public String getPrefix() {
        return config.getString("prefix");
    }

    public String getLobbyName() {
        return config.getString("lobby_name");
    }

    public String getArenaName() {
        return config.getString("arena_name");
    }

    public List<Material> getBreakables() {
        List<String> strBreakables =  config.getStringList("breakables");

        List<Material> matBreakables = new ArrayList<Material>();

        // copy and convert to material type
        for (int i = 0; i < strBreakables.size(); i++) {
            matBreakables.add(Material.getMaterial(strBreakables.get(i)));
        }

        return matBreakables;
    }
}
