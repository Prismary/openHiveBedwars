package net.prismarray.openhivebedwars.bedwars.spectator_compass;

import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import org.bukkit.entity.Player;

import java.util.List;

public class SpectatorCompassInventoryGUI extends InventoryGUIBase {

    public SpectatorCompassInventoryGUI(List<Player> listedPlayers) {
        super("Spectator Compass", (int) Math.ceil((double) listedPlayers.size() / 9) * 9);

        int slot = 0;

        for (Player p : listedPlayers) {
            new PlayerTeleportButton(this, slot++, p);
        }

        this.lock();
    }
}
