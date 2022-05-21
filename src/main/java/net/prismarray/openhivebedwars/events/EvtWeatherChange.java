package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

public class EvtWeatherChange extends EventBase {

    public EvtWeatherChange(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void weatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
