package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class EvtEntityDamage extends EventBase {

    public EvtEntityDamage(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void entityDamage(EntityDamageEvent event) {
        switch (plugin.game.getStatus()) {
            case INGAME:
                break;
            case LOBBY:
            case RESULTS:
                voidRespawn(event);
                break;
            default:
                event.setCancelled(true);
        }
    }

    public void voidRespawn(EntityDamageEvent event) {
        event.setCancelled(true);

        switch (event.getCause()) {
            case VOID:
                Entity e = event.getEntity();

                if (e instanceof Player) {
                    switch (plugin.game.getStatus()) {
                        case LOBBY:
                            plugin.game.setLobbyPlayer((Player) e);
                            break;
                        case RESULTS:
                            plugin.game.setResultsPlayer((Player) e);
                            break;
                    }
                }
        }
    }
}
