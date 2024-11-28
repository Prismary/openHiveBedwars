package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.bedwars.PlayerStatusManager;
import net.prismarray.openhivebedwars.bedwars.SpectatorCompass;
import net.prismarray.openhivebedwars.bedwars.spectator_compass.SpectatorCompassInventoryGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EvtSpectatorCompass extends EventBase {

    @EventHandler(ignoreCancelled = false)
    public void onRightClick(PlayerInteractEvent e) {

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        handleEventChecks(e, e.getPlayer());
    }

    @EventHandler(ignoreCancelled = false)
    public void onRightClick(PlayerInteractEntityEvent e) {
        handleEventChecks(e, e.getPlayer());
    }

    private void handleEventChecks(Event e, Player p) {

        if (!(new SpectatorCompass()).equals(p.getItemInHand())) {
            return;
        }

        if (PlayerStatusManager.getPlayerStatus(p) == PlayerStatusManager.PlayerStatus.ALIVE) {
            return;
        }

        List<Player> onlinePlayers = Bukkit.getOnlinePlayers().stream()
                .map((player) -> (Player) player)
                .filter((player) -> !Objects.equals(player, p))
                .collect(Collectors.toList());

        (new SpectatorCompassInventoryGUI(onlinePlayers, 0)).open(p);
    }
}
