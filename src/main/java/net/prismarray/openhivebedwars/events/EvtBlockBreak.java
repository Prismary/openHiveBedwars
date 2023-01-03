package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class EvtBlockBreak extends EventBase {

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        switch (Game.getStatus()) {
            case WARMUP:
            case INGAME:
                if (event.getBlock().hasMetadata("placedBy") ||
                        OpenHiveBedwars.getBWConfig().getBreakables().contains(event.getBlock().getType())) {
                    break;
                } // else fall through to default
            default:
                event.setCancelled(true);
        }
    }
}
