package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EvtNaturalMobSpawn extends EventBase {
    public EvtNaturalMobSpawn(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void creatureSpawn(CreatureSpawnEvent event) {
        switch  (event.getSpawnReason()) {
            case NATURAL:
                event.setCancelled(true);
                break;
        }
    }
}
