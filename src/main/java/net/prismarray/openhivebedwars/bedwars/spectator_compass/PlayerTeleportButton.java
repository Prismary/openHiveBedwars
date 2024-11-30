package net.prismarray.openhivebedwars.bedwars.spectator_compass;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.PlayerStatusManager;
import net.prismarray.openhivebedwars.bedwars.PlayerStatusManager.PlayerStatus;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIPlayerHead;
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
                        if (!teleportTarget.isOnline()) {
                            return;
                        }

                        a.getPlayer().closeInventory();

                        if (PlayerStatusManager.getPlayerStatus(a.getPlayer()) != PlayerStatus.SPECTATOR) {
                            return;
                        }

                        a.getPlayer().teleport(teleportTarget.getLocation());

                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                });
            }
        });
    }
}
