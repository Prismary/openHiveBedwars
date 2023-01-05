package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class EvtBlockPlace extends EventBase {

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        switch (Game.getStatus()) {
            case WARMUP:
            case INGAME:
                event.getBlock().setMetadata(
                        "placedBy", new FixedMetadataValue(
                                OpenHiveBedwars.getInstance(), event.getPlayer().getUniqueId()
                        )
                );
                break;
            default:
                event.setCancelled(true);
        }
    }
}
