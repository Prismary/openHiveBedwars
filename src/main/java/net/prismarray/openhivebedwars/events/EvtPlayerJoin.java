package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.scoreboard.ScoreboardManager;
import net.prismarray.openhivebedwars.util.Broadcast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class EvtPlayerJoin extends EventBase {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Game.hideHiddenPlayers(player);

        event.setJoinMessage(null);

        switch (Game.getStatus()) {
            case LOBBY:
                Game.setLobbyPlayer(player);
                Broadcast.raw("§9" + player.getName() + " §7wants to protect their bed!");
                Game.getMapVoting().onPlayerJoin(player);
                break;
            case WARMUP:
            case INGAME:
            case CONCLUDED:
                Game.setSpectatorPlayer(player);
                break;
            case RESULTS:
                Game.setResultsPlayer(player);
                break;
        }

        ScoreboardManager.setScoreboard(player, true);
    }
}
