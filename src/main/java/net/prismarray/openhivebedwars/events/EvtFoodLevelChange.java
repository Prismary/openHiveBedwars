package net.prismarray.openhivebedwars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class EvtFoodLevelChange extends EventBase {

    @EventHandler
    public void foodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
