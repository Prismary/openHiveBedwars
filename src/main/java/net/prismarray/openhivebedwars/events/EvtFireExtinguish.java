package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

public class EvtFireExtinguish extends EventBase {

    public EvtFireExtinguish(OpenHiveBedwars plugin) {
        super(plugin);
    }

    // NOT YET WORKING :(
    @EventHandler
    public void fireExtinguish(PlayerInteractEvent event) {
        switch (plugin.game.getStatus()) {
            case WARMUP:
            case INGAME:
                break;
            default:
                if (event.getAction() == Action.LEFT_CLICK_BLOCK &&
                        event.getPlayer().getTargetBlock((Set<Material>) null, 5).getType() == Material.FIRE) {
                    event.setCancelled(true);
                }
        }
    }
}
