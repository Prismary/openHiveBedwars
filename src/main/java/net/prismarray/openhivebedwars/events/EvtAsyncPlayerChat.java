package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Format;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EvtAsyncPlayerChat extends EventBase {

    public EvtAsyncPlayerChat(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void asyncPlayerChat(AsyncPlayerChatEvent event) {
        event.setMessage(Format.color(event.getMessage())); // Temporarily color-code all messages for testing
    }
}