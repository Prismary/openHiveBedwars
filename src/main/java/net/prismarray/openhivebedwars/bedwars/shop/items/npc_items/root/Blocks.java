package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root;

import net.prismarray.openhivebedwars.gui.components.CategorySelector;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import org.bukkit.Material;

public class Blocks extends CategorySelector {

    public Blocks(InventoryGUIBase gui, int slot, String destinationGUI) {
        super(
                gui,
                slot,
                Material.STONE,
                (short) 6,
                "§a§lBlocks",
                new String[]{"§7Buy blocks to bridge, build and", "§7protect your bed!"},
                destinationGUI
        );
    }
}
