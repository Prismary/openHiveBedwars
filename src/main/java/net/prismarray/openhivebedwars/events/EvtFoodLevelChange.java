package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class EvtFoodLevelChange extends EventBase {

    public EvtFoodLevelChange(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void foodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
