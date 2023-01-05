package net.prismarray.openhivebedwars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.world.StructureGrowEvent;

public class EvtStructureGrow extends EventBase {

    @EventHandler
    public void structureGrow(StructureGrowEvent event) {
        event.setCancelled(true);
    }
}
