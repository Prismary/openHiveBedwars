package net.prismarray.openhivebedwars.bedwars.stats;

import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.entity.Player;

public interface StatsManager {

    static String pointsMessage(int points, String reason) {
        return String.format("§2✚ §a%s Points §7[%s§7]", points, reason);
    }

    void playerJoin(Player player);
    void playerLeave(Player player);

    void kill(Player killer, Player victim);
    void finalKill(Player killer, Player victim);
    void bedBreak(TeamColor colorDestroyed, Player breaker);
    void unlockGenerator(Player purchaser);
    void upgradeGenerator(Player purchaser);

    int getTokens(Player player);
    int getPoints(Player player);
    int getKills(Player player);
    int getFinalKills(Player player);
    int getDeaths(Player player);
    int getBedsDestroyed(Player player);
    int getTeamsEliminated(Player player);
    int getGamesPlayed(Player player);
    int getVictories(Player player);
    int getWinStreak(Player player);

    void addTokens(Player player, int amount);
    void addPoints(Player player, int amount);
    void addKill(Player player);
    void addFinalKill(Player player);
    void addDeath(Player player);
    void addBedDestroyed(Player player);
    void addTeamEliminated(Player player);
    void addGamePlayed(Player player);
    void addVictory(Player player);
    void addWinStreak(Player player);
}
