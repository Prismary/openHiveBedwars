package net.prismarray.openhivebedwars.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Objects;

public class EvtEntityDamage extends EventBase {

    @EventHandler
    public void entityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) { // Return early if entity is player
            return;
        }

        if (Objects.equals(event.getEntityType(), EntityType.WOLF)) {
            return;
        }

        if (Objects.equals(event.getEntityType(), EntityType.IRON_GOLEM)) {
            return;
        }

        event.setCancelled(true);
    }
}
