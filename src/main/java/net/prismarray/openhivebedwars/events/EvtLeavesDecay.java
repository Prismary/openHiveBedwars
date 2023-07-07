package net.prismarray.openhivebedwars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EvtLeavesDecay extends EventBase {

    @EventHandler
    public void leavesDecay(LeavesDecayEvent event) {
        event.setCancelled(true);
    }
}
