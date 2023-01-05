package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.prismarray.openhivebedwars.util.Mode;
import org.bukkit.Material;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

public class Config extends ConfigFile {

    private Mode mode;

    private boolean mergeTeams;

    private String prefix;
    private String lobbyName;
    private String arenaName;

    private Set<Material> breakables;

    private boolean bridgeBuilderUseOptimizedPlacement;
    private boolean bridgeBuilderNoCollisionsWithPlayerPlacedBlocks;


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
        return this.breakables;
    }

    public boolean bridgeBuilderUseOptimizedPlacement() {
        return bridgeBuilderUseOptimizedPlacement;
    }

    public boolean bridgeBuilderNoCollisionsWithPlayerPlacedBlocks() {
        return bridgeBuilderNoCollisionsWithPlayerPlacedBlocks;
    }

    @Override
    protected void parseAndValidateConfig(YamlDocument yamlContent) throws ConfigValidationException {

        this.mode = parseMode(yamlContent.getString("mode"));
        this.prefix = parseString(yamlContent.getString("prefix"));
        this.lobbyName = parseString(yamlContent.getString("lobby_name"));
        this.arenaName = parseString(yamlContent.getString("arena_name"));

        this.mergeTeams = yamlContent.getBoolean("merge_teams");

        this.breakables = parseMaterialSet(yamlContent.getStringList("breakables"));

        this.bridgeBuilderUseOptimizedPlacement = yamlContent.getBoolean("bridge_builder.use_optimized_placement");
        this.bridgeBuilderNoCollisionsWithPlayerPlacedBlocks = yamlContent.getBoolean("bridge_builder.no_collisions_with_player_placed_blocks");
    }
}
