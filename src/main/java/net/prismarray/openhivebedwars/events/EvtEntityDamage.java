package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Status;
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
                checkForDeath(event);
                break;
            case LOBBY:
            case CONFIRMATION:
            case WARMUP:
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
                Entity entity = event.getEntity();

                if (entity instanceof Player) {
                    switch (plugin.game.getStatus()) {
                        case LOBBY:
                        case CONFIRMATION:
                            plugin.game.setLobbyPlayer((Player) entity);
                            break;
                        case WARMUP:
                            plugin.game.spawnPlayer((Player) entity);
                            break;
                        case RESULTS:
                            plugin.game.setResultsPlayer((Player) entity);
                            break;
                    }
                }
        }
    }

    public void checkForDeath(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player && plugin.game.getStatus() == Status.INGAME) {
            Player player = (Player) entity;

            if (!((player).getHealth() - event.getFinalDamage() > 0)) {
                event.setCancelled(true); // Prevent player death
                player.setHealth(20);
                plugin.game.spawnPlayer(player);
            }
        }
    }
}
