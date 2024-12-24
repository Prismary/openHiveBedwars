package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class EvtUnbreakingTools extends EventBase {

    @EventHandler
    public void onDurabilityChange(PlayerItemDamageEvent event) {

        if (!OpenHiveBedwars.getBWConfig().getUnbreakingTools()) {
            return;
        }

        event.setCancelled(true);
    }
}
