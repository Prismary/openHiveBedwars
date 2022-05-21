package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.event.Listener;

public abstract class EventBase implements Listener {
    protected final OpenHiveBedwars plugin;

    public EventBase(OpenHiveBedwars plugin) {
        this.plugin = plugin;
    }
}
