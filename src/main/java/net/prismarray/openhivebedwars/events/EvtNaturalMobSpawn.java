package net.prismarray.openhivebedwars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EvtNaturalMobSpawn extends EventBase {

    @EventHandler
    public void creatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            event.setCancelled(true);
        }
    }
}
