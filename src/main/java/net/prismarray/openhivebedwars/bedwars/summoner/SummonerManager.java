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

        if (gameTime == 0) { // Do not tick single summon summoners on 0 tick
            return;
        }

        instance.diamondSummoners.forEach(diamondSummoner -> {
            diamondSummoner.tickSummons(gameTime);
        });
        instance.emeraldSummoners.forEach(emeraldSummoner -> {
            emeraldSummoner.tickSummons(gameTime);
        });
    }

    public static TeamSummoner getTeamSummoner(TeamColor teamColor) {
        for (TeamSummoner teamSummoner : instance.teamSummoners) {
            if (teamSummoner.getTeamColor() == teamColor) {
                return teamSummoner;
            }
        }
        return null;
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

    public static void enableTeamSummoners() {
        instance.teamSummoners.forEach(TeamSummoner::enable);
    }

    public static void enableDiamondSummoners() {
        instance.diamondSummoners.forEach(DiamondSummoner::enable);
    }

    public static void enableEmeraldSummoners() {
        instance.emeraldSummoners.forEach(EmeraldSummoner::enable);
    }

    public static void disableTeamSummoners() {
        instance.teamSummoners.forEach(TeamSummoner::disable);
    }

    public static void disableDiamondSummoners() {
        instance.diamondSummoners.forEach(DiamondSummoner::disable);
    }

    public static void disableEmeraldSummoners() {
        instance.emeraldSummoners.forEach(EmeraldSummoner::disable);
    }

    public static void disableAllSummoners() {
        disableTeamSummoners();
        disableDiamondSummoners();
        disableEmeraldSummoners();
    }
}
