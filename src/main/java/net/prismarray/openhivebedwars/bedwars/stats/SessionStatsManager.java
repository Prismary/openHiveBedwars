package net.prismarray.openhivebedwars.bedwars.stats;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SessionStatsManager extends StatsManagerBase implements StatsManager {

    Map<Player, LocalPlayerStats> stats;

    public SessionStatsManager() {
        stats = new HashMap<>();
    }


    public void playerJoin(Player player) {
        stats.put(player, new LocalPlayerStats());
    }

    public void playerLeave(Player player) {
        stats.remove(player);
    }


    public int getTokens(Player player) {
        return stats.get(player).tokens;
    }

    public int getPoints(Player player) {
        return stats.get(player).points;
    }

    public int getKills(Player player) {
        return stats.get(player).kills;
    }

    public int getFinalKills(Player player) {
        return stats.get(player).finalKills;
    }

    public int getDeaths(Player player) {
        return stats.get(player).deaths;
    }

    public int getBedsDestroyed(Player player) {
        return stats.get(player).bedsDestroyed;
    }

    public int getTeamsEliminated(Player player) {
        return stats.get(player).teamsEliminated;
    }

    public int getGamesPlayed(Player player) {
        return stats.get(player).gamesPlayed;
    }

    public int getVictories(Player player) {
        return stats.get(player).victories;
    }

    public int getWinStreak(Player player) {
        return stats.get(player).winStreak;
    }




    public void addTokens(Player player, int amount) {
        stats.get(player).tokens += amount;
    }

    public void addPoints(Player player, int amount) {
        stats.get(player).points += amount;
    }

    public void addKill(Player player) {
        stats.get(player).kills++;
    }

    public void addFinalKill(Player player) {
        stats.get(player).finalKills++;
    }

    public void addDeath(Player player) {
        stats.get(player).deaths++;
    }

    public void addBedDestroyed(Player player) {
        stats.get(player).bedsDestroyed++;
    }

    public void addTeamEliminated(Player player) {
        stats.get(player).teamsEliminated++;
    }

    public void addGamePlayed(Player player) {
        stats.get(player).gamesPlayed++;
    }

    public void addVictory(Player player) {
        stats.get(player).victories++;
    }

    public void addWinStreak(Player player) {
        stats.get(player).winStreak++;
    }
}
