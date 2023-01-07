package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class Obsidian extends PurchasableItem {

    public Obsidian(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.OBSIDIAN,
                (short) 0,
                1,
                false,
                "Obsidian",
                1,
                Currency.EMERALD,
                false,
                false
        );
    }
}
