package net.prismarray.openhivebedwars.gui.actions;

import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import org.bukkit.entity.Player;

public class InventoryGUILeftOrShiftLeftClickAction extends InventoryGUIClickAction {

    public InventoryGUILeftOrShiftLeftClickAction(InventoryGUIBase inventoryGUI, Player player, int clickedSlot) {
        super(inventoryGUI, player, clickedSlot);
    }
}
