package net.prismarray.openhivebedwars.bedwars.stats;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.entity.Player;

public abstract class StatsManagerBase implements StatsManager {

    static void awardPoints(Player player, int points, String reason) {
        Broadcast.toPlayer(player, StatsManager.pointsMessage(points, reason));
        Game.getStatsManager().addPoints(player, points);
    }

    public void kill(Player killer, Player victim) {
        addKill(killer);
        addDeath(victim);

        awardPoints(
                killer,
                5,
                String.format("Killing %s%s§7", Game.getTeamHandler().getPlayerTeamColor(victim), victim.getName())
        );
    }

    public void finalKill(Player killer, Player victim) {
        addKill(killer);
        addDeath(victim);
        addFinalKill(killer);

        awardPoints(
                killer,
                10, // TODO is this correct?
                String.format("Killing %s%s§7", Game.getTeamHandler().getPlayerTeamColor(victim), victim.getName())
        );
    }

    public void bedBreak(TeamColor colorDestroyed, Player breaker) {
        addBedDestroyed(breaker);

        // Get points to be awarded
        int points;
        switch (Game.getMode()) {
            case SOLO:
            case DUOS:
                points = 65; // TODO get correct points for SOLO
                break;
            default:
                points = 80;
        }
        if (Game.getGameTime() >= 900) {
            points *= 2;
        }

        // Award points
        for (Player player : Game.getTeamHandler().getPlayerTeam(breaker).getPlayers()) {
            addPoints(player, points);
            awardPoints(
                    player,
                    80,
                    String.format("Destroying %s%s§7's Bed", colorDestroyed.chatColor, colorDestroyed.chatName)
            );
        }
    }

    public void unlockGenerator(Player purchaser) {
        awardPoints(purchaser, 5, "Unlock Generator");
    }
    public void upgradeGenerator(Player purchaser) {
        awardPoints(purchaser, 5, "Upgraded Generator");
    }
}
