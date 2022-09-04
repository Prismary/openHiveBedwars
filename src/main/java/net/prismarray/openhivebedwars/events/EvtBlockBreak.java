package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class EvtBlockBreak extends EventBase {

    public EvtBlockBreak(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        switch (plugin.game.getStatus()) {
            case WARMUP:
            case INGAME:
                if (event.getBlock().hasMetadata("placedBy") ||
                        plugin.config.getBreakables().contains(event.getBlock().getType())) {
                    break;
                } // else fall through to default
            default:
                event.setCancelled(true);
        }
    }
}
