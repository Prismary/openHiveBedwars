package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Team;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
            Location bedFootLocation = plugin.game.getMapConfig().getTeamBedFootLocation(team.getColor());
            Block bedFootBlock = plugin.game.getMapConfig().getArenaWorld().getBlockAt(bedFootLocation);

            Location bedHeadLocation = plugin.game.getMapConfig().getTeamBedHeadLocation(team.getColor());
            Block bedHeadBlock = plugin.game.getMapConfig().getArenaWorld().getBlockAt(bedHeadLocation);

            if (event.getBlock().equals(bedFootBlock) || event.getBlock().equals(bedHeadBlock)) {
                // Check whether bed head or foot block of team equals broken block
                event.setCancelled(true);
                team.breakBed();
                plugin.game.clearBed(team.getColor());
                Broadcast.bedBreak(team.getColor());
            }
        }
    }
}