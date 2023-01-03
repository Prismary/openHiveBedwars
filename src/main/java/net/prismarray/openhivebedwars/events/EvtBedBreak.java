package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.Team;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.SoundHandler;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class EvtBedBreak extends EventBase {

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        if (Game.getStatus() != Status.INGAME || event.getBlock().getType() != Material.BED_BLOCK) {
            return;
        }

        for (Team team : Game.getTeamHandler().getTeams()) {
            Location bedFootLocation = Game.getMapConfig().getTeamBedFootLocation(team.getColor());
            Block bedFootBlock = Game.getMapConfig().getArenaWorld().getBlockAt(bedFootLocation);

            Location bedHeadLocation = Game.getMapConfig().getTeamBedHeadLocation(team.getColor());
            Block bedHeadBlock = Game.getMapConfig().getArenaWorld().getBlockAt(bedHeadLocation);

            if (event.getBlock().equals(bedFootBlock) || event.getBlock().equals(bedHeadBlock)) {
                if (Game.getTeamHandler().getPlayerTeam(event.getPlayer()).equals(team)) { // Check whether player belongs to team bed
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("§c§lHey! §7You can't break your own bed!");
                    SoundHandler.playerSound(event.getPlayer(), Sound.NOTE_BASS);
                    return;
                }

                event.setCancelled(true);
                team.breakBed();
                Game.clearBed(team.getColor());
                Broadcast.bedBreak(team.getColor());
            }
        }
    }
}