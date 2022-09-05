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

        // Remove player from team
        Team team = plugin.game.getTeamHandler().getPlayerTeam(player);
        plugin.game.getTeamHandler().broadcastPlayerLeave(team, player.getName());
        plugin.game.getTeamHandler().removePlayer(player);
        plugin.game.getTeamHandler().tryDissolution(team);
    }
}
