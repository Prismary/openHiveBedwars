package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.StructureGrowEvent;

public class EvtStructureGrow extends EventBase {

    public EvtStructureGrow(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void structureGrow(StructureGrowEvent event) {
        event.setCancelled(true);
    }
}
