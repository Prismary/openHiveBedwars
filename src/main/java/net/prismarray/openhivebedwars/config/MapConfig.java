package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.prismarray.openhivebedwars.util.Mode;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class MapConfig extends ConfigFile {

    private final String mapID;
    private String mapDisplayName;
    private Mode mode;

    private World arenaWorld;

    private Location spectatorSpawn;
    private Set<Location> emeraldSummonerLocations;
    private Set<Location> diamondSummonerLocations;
    private Set<Location> enchanterNPCLocations;
    private Set<Location> specialistNPCLocations;

    private final Map<TeamColor, TeamConfig> teamConfigs;


    public MapConfig(Logger logger, File configFile) {
        this(logger, configFile, null);
    }

    public MapConfig(Logger logger, File configFile, World arenaWorld) {

        super(logger, configFile);

        this.mapID = FilenameUtils.removeExtension(configFile.getName());
        this.arenaWorld = arenaWorld;
        this.teamConfigs = new HashMap<>();
    }

    public void updateWorld(World arenaWorld) {

        this.arenaWorld = arenaWorld;

        updateLocationWorld();
        updateSetLocationWorld();

        teamConfigs.values().forEach(teamConfig -> teamConfig.updateWorld(arenaWorld));
    }

    private void updateLocationWorld() {

        Stream.of(
                this.spectatorSpawn
        ).forEach(location -> location.setWorld(this.arenaWorld));
    }

    private void updateSetLocationWorld() {

        Stream.of(
                this.emeraldSummonerLocations,
                this.diamondSummonerLocations,
                this.enchanterNPCLocations,
                this.specialistNPCLocations
        ).forEach(set -> set.forEach(location -> location.setWorld(this.arenaWorld)));
    }

    private void offsetEntityLocations() {

        Stream.of(
                this.spectatorSpawn
        ).forEach(MapConfig::offsetEntityLocation);

        Stream.of(
                this.emeraldSummonerLocations,
                this.diamondSummonerLocations,
                this.enchanterNPCLocations,
                this.specialistNPCLocations
        ).forEach(set -> set.forEach(MapConfig::offsetEntityLocation));

        teamConfigs.values().forEach(TeamConfig::offsetEntityLocations);
    }

    private static void offsetEntityLocation(Location location) {
        location.setX(location.getX() + 0.5 * Math.signum(location.getX()));
        location.setZ(location.getZ() + 0.5 * Math.signum(location.getZ()));
    }

    @Override
    protected void parseAndValidateConfig(YamlDocument yamlContent) throws ConfigValidationException {

        this.mapDisplayName = parseString(yamlContent.getString("settings.name"));
        this.mode = parseMode(yamlContent.getString("settings.mode"));

        this.spectatorSpawn = parseLocation(yamlContent.getString("locations.general.spectator_spawn"));

        this.emeraldSummonerLocations = parseLocationSet(yamlContent.getStringList("locations.general.emerald_summoner_locations"));
        this.diamondSummonerLocations = parseLocationSet(yamlContent.getStringList("locations.general.diamond_summoner_locations"));
        this.enchanterNPCLocations = parseLocationSet(yamlContent.getStringList("locations.general.enchanter_npc_locations"));
        this.specialistNPCLocations = parseLocationSet(yamlContent.getStringList("locations.general.specialist_npc_locations"));

        switch (this.mode) {

            case TEAMS:

                this.teamConfigs.clear();
                Arrays.stream(TeamColor.getTeamsModeColours())
                        .forEach(teamColor -> this.teamConfigs.put(teamColor, new TeamConfig(yamlContent, teamColor)));
                break;

            case DUOS:

                this.teamConfigs.clear();
                Arrays.stream(TeamColor.getDuosModeColours())
                        .forEach(teamColor -> this.teamConfigs.put(teamColor, new TeamConfig(yamlContent, teamColor)));
                break;

            case SOLO:

                this.teamConfigs.clear();
                Arrays.stream(TeamColor.getSoloModeColours())
                        .forEach(teamColor -> this.teamConfigs.put(teamColor, new TeamConfig(yamlContent, teamColor)));
                break;

            default:

                this.teamConfigs.clear();
                Arrays.stream(TeamColor.values())
                        .forEach(teamColor -> this.teamConfigs.put(teamColor, new TeamConfig(yamlContent, teamColor)));
                break;
        }

        this.offsetEntityLocations();
    }

    public Location getTeamSpawn(TeamColor teamColor) {
        return this.teamConfigs.containsKey(teamColor) ? this.teamConfigs.get(teamColor).getSpawn() : null;
    }

    public Location getTeamBedFootLocation(TeamColor teamColor) {
        return this.teamConfigs.containsKey(teamColor) ? this.teamConfigs.get(teamColor).getBedFootLocation() : null;
    }

    public Location getTeamBedHeadLocation(TeamColor teamColor) {
        return this.teamConfigs.containsKey(teamColor) ? this.teamConfigs.get(teamColor).getBedHeadLocation() : null;
    }

    public Set<Location> getTeamSummonerLocations(TeamColor teamColor) {
        return this.teamConfigs.containsKey(teamColor) ? this.teamConfigs.get(teamColor).getSummonerLocations() : null;
    }

    public Set<Location> getTeamItemNPCLocations(TeamColor teamColor) {
        return this.teamConfigs.containsKey(teamColor) ? this.teamConfigs.get(teamColor).getItemNPCLocations() : null;
    }

    public Set<Location> getTeamUpgradesNPCLocations(TeamColor teamColor) {
        return this.teamConfigs.containsKey(teamColor) ? this.teamConfigs.get(teamColor).getUpgradesNPCLocations() : null;
    }

    public Set<Location> getTeamColorIndicatorLocations(TeamColor teamColor) {
        return this.teamConfigs.containsKey(teamColor) ? this.teamConfigs.get(teamColor).getColorIndicatorLocations() : null;
    }

    public String getMapID() {
        return mapID;
    }

    public String getMapDisplayName() {
        return mapDisplayName;
    }

    public Mode getMode() {
        return mode;
    }

    public World getArenaWorld() {
        return arenaWorld;
    }

    public Location getSpectatorSpawn() {
        return spectatorSpawn;
    }

    public Set<Location> getEmeraldSummonerLocations() {
        return emeraldSummonerLocations;
    }

    public Set<Location> getDiamondSummonerLocations() {
        return diamondSummonerLocations;
    }

    public Set<Location> getEnchanterNPCLocations() {
        return enchanterNPCLocations;
    }

    public Set<Location> getSpecialistNPCLocations() {
        return specialistNPCLocations;
    }

    static class TeamConfig {

        private final TeamColor teamColor;

        private Location spawn;
        private Location bedFootLocation;
        private Location bedHeadLocation;

        private Set<Location> summonerLocations;
        private Set<Location> itemNPCLocations;
        private Set<Location> upgradesNPCLocations;
        private Set<Location> colorIndicatorLocations;


        private TeamConfig(YamlDocument yamlContent, TeamColor teamColor) throws ConfigValidationException {

            this.teamColor = teamColor;

            this.parseAndValidateConfig(yamlContent);
        }

        private void parseAndValidateConfig(YamlDocument config) throws ConfigValidationException {

            Section teamSection = config.getSection("locations.teams." + this.teamColor.name().toLowerCase());

            if (Objects.isNull(teamSection)) {
                throw new ConfigValidationException(
                        "Could not parse team '" + this.teamColor.name() + "'. Configuration section is missing."
                );
            }

            this.spawn = parseLocation(teamSection.getString("spawn"));
            this.bedFootLocation = parseLocation(teamSection.getString("bed.foot_location"));
            this.bedHeadLocation = parseLocation(teamSection.getString("bed.head_location"));

            Vector bedDistance = this.bedHeadLocation.toVector().subtract(this.bedFootLocation.toVector());

            if (bedDistance.length() != 1) {
                throw new ConfigValidationException(
                        "Could not parse bed location of team '" + this.teamColor.name() + "'. The two " +
                                "locations are either equal or more than one block apart."
                );
            }

            if (bedDistance.getY() != 0) {
                throw new ConfigValidationException(
                        "Could not parse bed location of team '" + this.teamColor.name() + "'. The two " +
                                "locations are laid out vertically."
                );
            }

            this.summonerLocations = parseLocationSet(teamSection.getStringList("summoner_locations"));
            this.itemNPCLocations = parseLocationSet(teamSection.getStringList("item_npc_locations"));
            this.upgradesNPCLocations = parseLocationSet(teamSection.getStringList("upgrades_npc_locations"));
            this.colorIndicatorLocations = parseLocationSet(teamSection.getStringList("color_indicator_locations"));
        }

        public void updateWorld(World arenaWorld) {

            updateLocationWorld(arenaWorld);
            updateSetLocationWorld(arenaWorld);
        }

        private void updateLocationWorld(World arenaWorld) {

            Stream.of(
                    this.spawn,
                    this.bedFootLocation,
                    this.bedHeadLocation
            ).forEach(location -> location.setWorld(arenaWorld));
        }

        private void updateSetLocationWorld(World arenaWorld) {

            Stream.of(
                    this.summonerLocations,
                    this.itemNPCLocations,
                    this.upgradesNPCLocations,
                    this.colorIndicatorLocations
            ).forEach(set -> set.forEach(location -> location.setWorld(arenaWorld)));
        }

        private void offsetEntityLocations() {

            Stream.of(
                    this.spawn
            ).forEach(MapConfig::offsetEntityLocation);

            Stream.of(
                    this.summonerLocations,
                    this.itemNPCLocations,
                    this.upgradesNPCLocations,
                    this.colorIndicatorLocations
            ).forEach(set -> set.forEach(MapConfig::offsetEntityLocation));
        }

        public TeamColor getTeamColor() {
            return teamColor;
        }

        public Location getSpawn() {
            return spawn;
        }

        public Location getBedFootLocation() {
            return bedFootLocation;
        }

        public Location getBedHeadLocation() {
            return bedHeadLocation;
        }

        public Set<Location> getSummonerLocations() {
            return summonerLocations;
        }

        public Set<Location> getItemNPCLocations() {
            return itemNPCLocations;
        }

        public Set<Location> getUpgradesNPCLocations() {
            return upgradesNPCLocations;
        }

        public Set<Location> getColorIndicatorLocations() {
            return colorIndicatorLocations;
        }
    }
}
