package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Team;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class EvtBedBreak extends EventBase {

    public EvtBedBreak(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        if (plugin.game.getStatus() != Status.INGAME || event.getBlock().getType() != Material.BED_BLOCK) {
            return;
        }

        for (Team team : plugin.game.getTeamHandler().getTeams()) {
            if (event.getBlock().equals(plugin.game.getMapConfig().getArenaWorld().getBlockAt(plugin.game.getMapConfig().getTeamBedFootLocation(team.getColor())))
            || event.getBlock().equals(plugin.game.getMapConfig().getArenaWorld().getBlockAt(plugin.game.getMapConfig().getTeamBedHeadLocation(team.getColor())))) {
                // Check whether bed head or foot block of team equals broken block
                event.setCancelled(true);
                team.breakBed();
                plugin.game.clearBed(team.getColor());
                Broadcast.broadcast(team.getColor().name() + " bed broken!");
            }
        }
    }
}