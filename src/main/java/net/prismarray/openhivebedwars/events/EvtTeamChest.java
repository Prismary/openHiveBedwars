package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.teamChest.TeamChestManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EvtTeamChest extends EventBase {

    @EventHandler
    public void onInteraction(PlayerInteractEvent e) {

        if (!e.hasBlock() || e.getClickedBlock().getType() != Material.ENDER_CHEST) {
            return;
        }

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player p = e.getPlayer();

        if (p.getAllowFlight()) {
            e.setCancelled(true);
            return;
        }

        e.setCancelled(true);
        TeamChestManager.instance.openTeamChest(e.getPlayer());
    }
}
