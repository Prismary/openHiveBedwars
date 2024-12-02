package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root;

import net.prismarray.openhivebedwars.gui.components.CategorySelector;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import org.bukkit.Material;

public class Armor extends CategorySelector {

    public Armor(InventoryGUIBase gui, int slot, String destinationGUI) {
        super(
                gui,
                slot,
                Material.IRON_CHESTPLATE,
                (short) 0,
                "§c§lArmor",
                new String[]{"§7Be prepared when you encounter", "§7your evil enemies!"},
                destinationGUI
        );
    }
}
