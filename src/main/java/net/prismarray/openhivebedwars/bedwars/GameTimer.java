package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.bedwars.scoreboard.ScoreboardManager;
import net.prismarray.openhivebedwars.bedwars.summoner.SummonerManager;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.Status;

public class GameTimer extends Stopwatch {

    public GameTimer() {
        super(-1, true);
    }

    @Override
    protected void onIncrement() {
        if (Game.getStatus() == Status.INGAME) { // Regular increment loop
            SummonerManager.tickSummoners(getCount());
            ScoreboardManager.updateAll();

            if (getCount() == 30) {
                SummonerManager.enableEmeraldSummoners();
                Broadcast.broadcast("§a§lEmerald §e§lSummoners are now ACTIVE!");
            }

            return;
        }

        // End of warmup reset
        if (Game.getStatus() == Status.WARMUP && getCount() == 9) {
            set(-1);
        }
    }
}
