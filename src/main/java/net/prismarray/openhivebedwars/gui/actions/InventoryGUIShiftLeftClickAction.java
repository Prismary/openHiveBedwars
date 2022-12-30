package net.prismarray.openhivebedwars.gui.actions;

import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import org.bukkit.entity.Player;

public class InventoryGUIShiftLeftClickAction extends InventoryGUIClickAction {

    public InventoryGUIShiftLeftClickAction(InventoryGUIBase inventoryGUI, Player player, int clickedSlot) {
        super(inventoryGUI, player, clickedSlot);
    }
}
