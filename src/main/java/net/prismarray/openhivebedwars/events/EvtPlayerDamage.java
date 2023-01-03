package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class EvtPlayerDamage extends EventBase {

    @EventHandler
    public void playerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) { // Return early if entity is not player
            return;
        } // todo clean up methods to take player object directly instead of event and cast a million times

        switch (Game.getStatus()) {
            case INGAME:
                Game.getCombatHandler().playerDamage(event);
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

    public void checkForDeath(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (Game.getStatus() == Status.INGAME) {
            Player player = (Player) entity;

            if (!((player).getHealth() - event.getFinalDamage() > 0)) {
                event.setCancelled(true); // Prevent player death
                player.setHealth(20);

                Game.getCombatHandler().playerDeath(event);
            }
        }
    }

    public void voidRespawn(EntityDamageEvent event) {
        event.setCancelled(true);

        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            Entity entity = event.getEntity();

            switch (Game.getStatus()) {
                case LOBBY:
                case CONFIRMATION:
                    Game.setLobbyPlayer((Player) entity);
                    break;
                case WARMUP:
                    Game.spawnPlayer((Player) entity);
                    break;
                case RESULTS:
                    Game.setResultsPlayer((Player) entity);
                    break;
            }
        }
    }
}
