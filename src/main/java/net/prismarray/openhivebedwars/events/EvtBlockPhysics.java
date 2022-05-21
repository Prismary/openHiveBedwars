package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class EvtBlockPhysics extends EventBase {

    public EvtBlockPhysics(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void blockPhysics(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }
}
