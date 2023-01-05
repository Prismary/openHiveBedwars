package net.prismarray.openhivebedwars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPhysicsEvent;

public class EvtBlockPhysics extends EventBase {

    @EventHandler
    public void blockPhysics(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }
}
