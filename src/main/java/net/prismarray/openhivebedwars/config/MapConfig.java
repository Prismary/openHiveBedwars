package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.prismarray.openhivebedwars.bedwars.shop.npc.VillagerShop;
import net.prismarray.openhivebedwars.util.Mode;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Villager;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapConfig extends ConfigFile {

    private final String mapID;
    private String mapDisplayName;
    private Mode mode;

    private World arenaWorld;

    private int minBuildHeight;
    private int maxBuildHeight;
    private Location spectatorSpawn;
    private Set<Location> emeraldSummonerLocations;
    private Set<Location> diamondSummonerLocations;

    private final Map<TeamColor, TeamConfig> teamConfigs;
    private List<VillagerShopConfig> villagerShopConfigs;


    public MapConfig(Logger logger, File configFile) {
        this(logger, configFile, null);
    }

    public MapConfig(Logger logger, File configFile, World arenaWorld) {

        super(logger, configFile);

        this.mapID = FilenameUtils.removeExtension(configFile.getName());
        this.arenaWorld = arenaWorld;
        this.teamConfigs = new HashMap<>();
        this.villagerShopConfigs = new ArrayList<>();
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
                this.diamondSummonerLocations
        ).forEach(set -> set.forEach(location -> location.setWorld(this.arenaWorld)));

        this.villagerShopConfigs.forEach(conf -> conf.location.setWorld(this.arenaWorld));
    }

    private void offsetEntityLocations() {

        Stream.of(
                this.spectatorSpawn
        ).forEach(MapConfig::offsetEntityLocation);

        Stream.of(
                this.emeraldSummonerLocations,
                this.diamondSummonerLocations
        ).forEach(set -> set.forEach(MapConfig::offsetEntityLocation));

        teamConfigs.values().forEach(TeamConfig::offsetEntityLocations);

        this.villagerShopConfigs.stream().map(conf -> conf.location).forEach(MapConfig::offsetEntityLocation);
    }

    private static void offsetEntityLocation(Location location) {
        location.setX(location.getX() + 0.5 * Math.signum(location.getX()));
        location.setZ(location.getZ() + 0.5 * Math.signum(location.getZ()));
    }

    @Override
    protected void parseAndValidateConfig(YamlDocument yamlContent) throws ConfigValidationException {

        this.mapDisplayName = parseString(yamlContent.getString("settings.name"));
        this.mode = parseMode(yamlContent.getString("settings.mode"));

        int[] buildHeights = parseBuildLimits(
                yamlContent.getString("settings.min_build_height"),
                yamlContent.getString("settings.max_build_height")
        );
        this.minBuildHeight = buildHeights[0];
        this.maxBuildHeight = buildHeights[1];

        this.spectatorSpawn = parseLocation(yamlContent.getString("locations.general.spectator_spawn"));

        this.emeraldSummonerLocations = parseLocationSet(yamlContent.getStringList("locations.general.emerald_summoner_locations"));
        this.diamondSummonerLocations = parseLocationSet(yamlContent.getStringList("locations.general.diamond_summoner_locations"));

        if (Objects.isNull(yamlContent.getSection("locations.general.npcs"))) {
            this.villagerShopConfigs = new ArrayList<>();

        } else {
            this.villagerShopConfigs = yamlContent.getSection("locations.general.npcs").getKeys().stream()
                    .filter(Objects::nonNull)
                    .map(key -> new VillagerShopConfig(yamlContent, (String) key))
                    .collect(Collectors.toList());
        }

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

    public void spawnNPCs() {
        this.villagerShopConfigs.forEach(VillagerShopConfig::spawnVillagerShop);
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

    public int getMinBuildHeight() {
        return minBuildHeight;
    }

    public int getMaxBuildHeight() {
        return maxBuildHeight;
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

    public static class TeamConfig {

        private final TeamColor teamColor;

        private Location spawn;
        private Location bedFootLocation;
        private Location bedHeadLocation;

        private Set<Location> summonerLocations;
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
                    this.colorIndicatorLocations
            ).forEach(set -> set.forEach(location -> location.setWorld(arenaWorld)));
        }

        private void offsetEntityLocations() {

            Stream.of(
                    this.spawn
            ).forEach(MapConfig::offsetEntityLocation);

            Stream.of(
                    this.summonerLocations,
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

        public Set<Location> getColorIndicatorLocations() {
            return colorIndicatorLocations;
        }
    }

    public static class VillagerShopConfig {

        private Location location;
        private String inventoryGUI;
        private Villager.Profession profession;
        private String customName;

        public VillagerShopConfig(YamlDocument config, @Nonnull String shopKey) throws ConfigValidationException {
            this.parseAndValidateConfig(config, shopKey);
        }

        private void parseAndValidateConfig(YamlDocument config, @Nonnull String shopKey) throws ConfigValidationException {

            String baseRoute = String.join(".", "locations", "general", "npcs", shopKey);

            this.location = parseLocation(config.getString(String.join(".", baseRoute, "location")));
            this.inventoryGUI = parseString(config.getString(String.join(".", baseRoute, "inventoryGUI")));
            this.profession = parseVillagerProfession(config.getString(String.join(".", baseRoute, "profession")));
            this.customName = config.getString(String.join(".", baseRoute, "customName"));
        }

        public VillagerShop spawnVillagerShop() {
            return new VillagerShop(location, inventoryGUI, profession, customName);
        }
    }
}
