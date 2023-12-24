package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.prismarray.openhivebedwars.util.Mode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

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
    private boolean bridgeBuilderNoHeadCollisionsWithBlocks;
    private boolean bridgeBuilderNoHeadCollisionsWithEntities;
    private double bridgeBuilderMovementSpeed;
    private Set<Material> bridgeBuilderReplaceableBlocks;
    private Set<EntityType> bridgeBuilderNonCollidingEntityTypes;


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

    public double getBridgeBuilderMovementSpeed() {
        return bridgeBuilderMovementSpeed;
    }

    public Set<Material> getBridgeBuilderReplaceableBlocks() {
        return bridgeBuilderReplaceableBlocks;
    }

    public boolean bridgeBuilderNoHeadCollisionsWithBlocks() {
        return bridgeBuilderNoHeadCollisionsWithBlocks;
    }

    public boolean bridgeBuilderNoHeadCollisionsWithEntities() {
        return bridgeBuilderNoHeadCollisionsWithEntities;
    }

    public Set<EntityType> getBridgeBuilderNonCollidingEntityTypes() {
        return bridgeBuilderNonCollidingEntityTypes;
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
        this.bridgeBuilderNoHeadCollisionsWithBlocks = yamlContent.getBoolean("bridge_builder.no_head_collisions_with_blocks");
        this.bridgeBuilderNoHeadCollisionsWithEntities = yamlContent.getBoolean("bridge_builder.no_head_collisions_with_entities");
        this.bridgeBuilderMovementSpeed = yamlContent.getDouble("bridge_builder.movement_speed");
        this.bridgeBuilderReplaceableBlocks = parseMaterialSet(yamlContent.getStringList("bridge_builder.replaceable_blocks"));
        this.bridgeBuilderNonCollidingEntityTypes = parseEntityTypeSet(yamlContent.getStringList("bridge_builder.non_colliding_entity_types"));
    }
}
