package net.prismarray.openhivebedwars.gui.actions;

import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import org.bukkit.entity.Player;

public class InventoryGUIClickAction extends InventoryGUIAction {

    private final int clickedSlot;

    public InventoryGUIClickAction(InventoryGUIBase inventoryGUI, Player player, int clickedSlot) {
        super(inventoryGUI, player);

        this.clickedSlot = clickedSlot;
    }

    public int getClickedSlot() {
        return clickedSlot;
    }
}
