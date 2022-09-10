package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class EvtPlayerJoin extends EventBase {

    public EvtPlayerJoin(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        switch (plugin.game.getStatus()) {
            case LOBBY:
                plugin.game.setLobbyPlayer(player);
                break;
            case WARMUP:
            case INGAME:
            case CONCLUDED:
                plugin.game.setSpectatorPlayer(player);
                break;
            case RESULTS:
                plugin.game.setResultsPlayer(player);
                break;
        }
    }
}
