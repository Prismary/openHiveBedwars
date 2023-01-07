package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root;

import net.prismarray.openhivebedwars.bedwars.shop.items.CategorySelector;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import org.bukkit.Material;

import java.util.concurrent.Callable;

public class Blocks extends CategorySelector {

    public Blocks(InventoryGUIBase gui, int slot, Callable<? extends InventoryGUIBase> destinationGUIFactory) {
        super(
                gui,
                slot,
                Material.STONE,
                (short) 6,
                "§a§lBlocks",
                new String[]{"§7Buy blocks to bridge, build and", "§7protect your bed!"},
                destinationGUIFactory
        );
    }
}
