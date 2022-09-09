package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Mode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class Config extends ConfigFile {

    private Mode mode;

    private boolean mergeTeams;

    private String prefix;
    private String lobbyName;
    private String arenaName;

    private Set<Material> breakables;


    public Config(Logger logger, File configFile) {
        super(logger, configFile);
    }

    public Mode getMode() {
        return this.mode;
    }

    public boolean mergeTeams() {
        return this.mergeTeams;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getLobbyName() {
        return this.lobbyName;
    }

    public String getArenaName() {
        return this.arenaName;
    }

    public Set<Material> getBreakables() {
        /*
        List<String> strBreakables =  config.getStringList("breakables");

        List<Material> matBreakables = new ArrayList<Material>();

        // copy and convert to material type
        for (int i = 0; i < strBreakables.size(); i++) {
            matBreakables.add(Material.getMaterial(strBreakables.get(i)));
        }

        return matBreakables;
         */
        return this.breakables;
    }

    @Override
    protected void parseAndValidateConfig(YamlDocument yamlContent) throws ConfigValidationException {
        // TODO: implement parser and validation
    }
}
