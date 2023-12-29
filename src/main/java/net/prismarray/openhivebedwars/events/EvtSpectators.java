package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.PlayerStatusManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;

public class EvtSpectators extends EventBase {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockInteraction(PlayerInteractEvent e) {

        if (PlayerStatusManager.getPlayerStatus(e.getPlayer()) == PlayerStatusManager.PlayerStatus.ALIVE) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockInteraction(PlayerInteractEntityEvent e) {

        if (PlayerStatusManager.getPlayerStatus(e.getPlayer()) == PlayerStatusManager.PlayerStatus.ALIVE) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockInteraction(PlayerInteractAtEntityEvent e) {

        if (PlayerStatusManager.getPlayerStatus(e.getPlayer()) == PlayerStatusManager.PlayerStatus.ALIVE) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockInteraction(PlayerDropItemEvent e) {

        if (PlayerStatusManager.getPlayerStatus(e.getPlayer()) == PlayerStatusManager.PlayerStatus.ALIVE) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockInteraction(PlayerPickupItemEvent e) {

        if (PlayerStatusManager.getPlayerStatus(e.getPlayer()) == PlayerStatusManager.PlayerStatus.ALIVE) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProjectileHit(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player damaged = (Player) e.getEntity();

        if (PlayerStatusManager.getPlayerStatus(damaged) == PlayerStatusManager.PlayerStatus.ALIVE) {
            return;
        }

        if (!(e.getDamager() instanceof Arrow)) {
            return;
        }

        damaged.teleport(damaged.getLocation().add(new Vector(0, 3, 0)));
        damaged.setFlying(true);

        Arrow oldArrow = (Arrow) e.getDamager();

        Arrow newArrow = oldArrow.getShooter().launchProjectile(Arrow.class);
        newArrow.setShooter(oldArrow.getShooter());
        newArrow.setVelocity(oldArrow.getVelocity());
        newArrow.setBounce(false);

        e.setCancelled(true);
        oldArrow.remove();
    }
}
