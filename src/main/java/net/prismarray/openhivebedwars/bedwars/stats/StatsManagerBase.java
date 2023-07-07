package net.prismarray.openhivebedwars.bedwars.stats;

import org.bukkit.entity.Player;

public abstract class StatsManagerBase implements StatsManager {

    public void kill(Player killer, Player victim) {
        addKill(killer);
        addDeath(victim);
        // todo add points
    }

    public void finalKill(Player killer, Player victim) {
        addKill(killer);
        addDeath(victim);
        addFinalKill(killer);
        // todo add points
    }

    public void bedBreak(Player breaker) {
        addBedDestroyed(breaker);
        // todo add points
    }

    public void unlockGenerator(Player purchaser) {
        addPoints(purchaser, 5);
        StatsManager.pointsMessage(5, "Unlock Generator");
    }
}
