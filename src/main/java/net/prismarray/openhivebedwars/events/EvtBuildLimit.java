package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.SoundHandler;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class EvtBuildLimit extends EventBase {

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        int blockY = event.getBlock().getLocation().getBlockY();

        // Check whether block is too high
        if (blockY > Game.getMapConfig().getMaxBuildHeight()) {
            Broadcast.toPlayer(event.getPlayer(), "§cYou cannot place blocks that high!");
            SoundHandler.playerSound(event.getPlayer(), Sound.ITEM_BREAK);
            event.setCancelled(true);
            return;
        }

        // Check whether block is too low
        if (blockY < Game.getMapConfig().getMinBuildHeight()) {
            Broadcast.toPlayer(event.getPlayer(), "§cYou cannot place blocks that low!");
            SoundHandler.playerSound(event.getPlayer(), Sound.ITEM_BREAK);
            event.setCancelled(true);
        }
    }
}
