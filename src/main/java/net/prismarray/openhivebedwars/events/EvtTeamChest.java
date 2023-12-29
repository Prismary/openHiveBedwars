package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.PlayerStatusManager;
import net.prismarray.openhivebedwars.bedwars.TeamChestManager;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EvtTeamChest extends EventBase {

    @EventHandler
    public void onInteraction(PlayerInteractEvent e) {

        if (!e.hasBlock() || e.getClickedBlock().getType() != Material.ENDER_CHEST
                || e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (Game.getStatus() != Status.INGAME) {
            e.setCancelled(true);
            return;
        }

        Player p = e.getPlayer();

        if (PlayerStatusManager.getPlayerStatus(p) != PlayerStatusManager.PlayerStatus.ALIVE) {
            e.setCancelled(true);
            return;
        }

        e.setCancelled(true);
        TeamChestManager.instance.openTeamChest(e.getPlayer());
    }
}
