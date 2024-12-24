package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.util.PlayerMetadataValue;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

import java.util.Objects;

public class EvtInstantTNT extends EventBase {

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {

        if (event.getBlockPlaced().getType() != Material.TNT) {
            return;
        }

        if (Objects.isNull(event.getPlayer())) {
            return;
        }

        if (Game.getStatus() != Status.INGAME) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);
        Entity tnt = event.getPlayer().getWorld().spawnEntity(
                event.getBlockPlaced().getLocation().add(new Vector(0.5, 0, 0.5)),
                EntityType.PRIMED_TNT
        );
        tnt.setMetadata("placingPlayer", new PlayerMetadataValue(event.getPlayer()));

        if (event.getItemInHand().getAmount() > 1) {
            event.getItemInHand().setAmount(event.getItemInHand().getAmount() - 1);

        } else {
            event.getPlayer().setItemInHand(null);
        }
    }
}
