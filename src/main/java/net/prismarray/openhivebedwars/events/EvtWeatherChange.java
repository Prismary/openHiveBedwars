package net.prismarray.openhivebedwars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

public class EvtWeatherChange extends EventBase {

    @EventHandler
    public void weatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
