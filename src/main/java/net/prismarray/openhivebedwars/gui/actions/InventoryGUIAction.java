package net.prismarray.openhivebedwars.gui.actions;

import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import org.bukkit.entity.Player;

public class InventoryGUIAction {

    private final InventoryGUIBase inventoryGUI;
    private final Player player;

    public InventoryGUIAction(InventoryGUIBase inventoryGUI, Player player) {
        this.inventoryGUI = inventoryGUI;
        this.player = player;
    }

    public InventoryGUIBase getInventoryGUI() {
        return inventoryGUI;
    }

    public Player getPlayer() {
        return player;
    }
}
