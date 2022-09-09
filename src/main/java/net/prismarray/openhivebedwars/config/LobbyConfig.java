package net.prismarray.openhivebedwars.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.Location;

import java.io.File;
import java.util.logging.Logger;

public class LobbyConfig extends ConfigFile {

    private Location lobbyPlayerSpawnLocation;
    private Location resultsPlayerSpawnLocation;
    private Location scoreboardLocation;

    private Location podiumGoldLocation;
    private Location podiumSilverLocation;
    private Location podiumBronzeLocation;


    public LobbyConfig(Logger logger, File configFile) {
        super(logger, configFile);
    }

    @Override
    protected void parseAndValidateConfig(YamlDocument yamlContent) throws ConfigValidationException {

        this.lobbyPlayerSpawnLocation = parseLocation(yamlContent.getString("locations.lobby_player_spawn"));
        this.resultsPlayerSpawnLocation = parseLocation(yamlContent.getString("locations.results_player_spawn"));
        this.scoreboardLocation = parseLocation(yamlContent.getString("locations.scoreboard"));

        this.podiumGoldLocation = parseLocation(yamlContent.getString("locations.podium.gold"));
        this.podiumSilverLocation = parseLocation(yamlContent.getString("locations.podium.silver"));
        this.podiumBronzeLocation = parseLocation(yamlContent.getString("locations.podium.bronze"));
    }

    public Location getLobbyPlayerSpawnLocation() {
        return lobbyPlayerSpawnLocation;
    }

    public Location getResultsPlayerSpawnLocation() {
        return resultsPlayerSpawnLocation;
    }

    public Location getScoreboardLocation() {
        return scoreboardLocation;
    }

    public Location getPodiumGoldLocation() {
        return podiumGoldLocation;
    }

    public Location getPodiumSilverLocation() {
        return podiumSilverLocation;
    }

    public Location getPodiumBronzeLocation() {
        return podiumBronzeLocation;
    }
}
