package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class EvtPlayerQuit extends EventBase {

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);

        // Notify TeamHandler
        Game.getTeamHandler().playerDisconnect(player);
    }
}
