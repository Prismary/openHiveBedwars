package net.prismarray.openhivebedwars.bedwars.summoner;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.util.TeamColor;

import java.util.ArrayList;

public class SummonerManager {

    public static final SummonerManager instance = new SummonerManager();

    private final ArrayList<TeamSummoner> teamSummoners;
    private final ArrayList<DiamondSummoner> diamondSummoners;
    private final ArrayList<EmeraldSummoner> emeraldSummoners;

    private SummonerManager() {
        teamSummoners = new ArrayList<>();
        diamondSummoners = new ArrayList<>();
        emeraldSummoners = new ArrayList<>();
    }

    public static SummonerManager getInstance() {
        return instance;
    }

    public static void tickSummoners(int gameTime) { // Called by game timer
        instance.teamSummoners.forEach(teamSummoner -> teamSummoner.tickSummons(gameTime));
        instance.diamondSummoners.forEach(diamondSummoner -> {
            diamondSummoner.tickSummons(gameTime);
            diamondSummoner.tickProgressBar();
        });
        instance.emeraldSummoners.forEach(emeraldSummoner -> {
            emeraldSummoner.tickSummons(gameTime);
            emeraldSummoner.tickProgressBar();
        });
    }

    public static void spawnAllSummoners() {
        // Spawn team summoners
        Game.getTeamHandler().getTeams().forEach(team -> spawnTeamSummoners(team.getColor()));

        // Spawn single item summoners
        spawnDiamondSummoners();
        spawnEmeraldSummoners();
    }

    public static void spawnTeamSummoners(TeamColor teamColor) {
        Game.getMapConfig().getTeamSummonerLocations(teamColor).stream()
                .map(location -> new TeamSummoner(location, teamColor))
                .forEach(instance.teamSummoners::add);
    }

    public static void spawnDiamondSummoners() {
        Game.getMapConfig().getDiamondSummonerLocations().stream()
                .map(DiamondSummoner::new)
                .forEach(instance.diamondSummoners::add);
    }

    public static void spawnEmeraldSummoners() {
        Game.getMapConfig().getEmeraldSummonerLocations().stream()
                .map(EmeraldSummoner::new)
                .forEach(instance.emeraldSummoners::add);
    }
}
