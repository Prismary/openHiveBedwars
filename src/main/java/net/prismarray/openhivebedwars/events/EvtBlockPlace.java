package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class EvtBlockPlace extends EventBase {

    public EvtBlockPlace(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        switch (plugin.game.getStatus()) {
            case WARMUP:
            case INGAME:
                event.getBlock().setMetadata("placedBy", new FixedMetadataValue(plugin, event.getPlayer().getUniqueId()));
                break;
            default:
                event.setCancelled(true);
        }
    }
}
