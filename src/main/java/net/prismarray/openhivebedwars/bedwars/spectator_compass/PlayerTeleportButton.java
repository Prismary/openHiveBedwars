package net.prismarray.openhivebedwars.bedwars.spectator_compass;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.InventoryGUIPlayerHead;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerTeleportButton extends InventoryGUIPlayerHead {

    public PlayerTeleportButton(InventoryGUIBase gui, int slot, Player teleportTarget) {

        super(
                gui,
                slot,
                teleportTarget.getName(),
                1,
                teleportTarget.getDisplayName(),
                new String[]{String.format("Teleport yourself to §2%s§r.", teleportTarget.getDisplayName())}
        );

        addActionListenerToContainingInventory(new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUIClickAction a) {
                Bukkit.getScheduler().runTask(OpenHiveBedwars.getInstance(), () -> {
                    try {
                        // ToDo: remove debug messages
                        Bukkit.broadcastMessage("Teleport event fired");
                        if (!teleportTarget.isOnline()) {
                            return;
                        }
                        Bukkit.broadcastMessage("Target verified online");
                        a.getPlayer().teleport(teleportTarget.getLocation());
                        Bukkit.broadcastMessage("Player teleported to target");
                        a.getPlayer().closeInventory();
                        Bukkit.broadcastMessage("Player inventory closed");
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                });
            }
        });
    }
}
