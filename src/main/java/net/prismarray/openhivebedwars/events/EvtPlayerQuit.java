package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class EvtPlayerQuit extends EventBase {

    public EvtPlayerQuit(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);

        // Notify TeamHandler
        plugin.game.getTeamHandler().playerDisconnect(player);
    }
}
