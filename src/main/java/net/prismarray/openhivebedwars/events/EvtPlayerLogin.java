package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.util.Format;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

public class EvtPlayerLogin extends EventBase {

    @EventHandler
    public void playerLogin(PlayerLoginEvent event) {
        switch (Game.getStatus()) {
            case STARTUP:
                disallow(event, "Server is starting up.");
                break;
            case CONFIRMATION:
                disallow(event, "The game is starting.");
                break;
            case SHUTDOWN:
                disallow(event, "Server is shutting down.");
                break;
        }
    }

    public void disallow(PlayerLoginEvent event, String message) {
        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Format.color("&c" + message));
    }
}
