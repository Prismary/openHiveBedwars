package net.prismarray.openhivebedwars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class EvtEntityDamage extends EventBase {

    @EventHandler
    public void entityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) { // Return early if entity is player
            return;
        }

        event.setCancelled(true);
    }
}
