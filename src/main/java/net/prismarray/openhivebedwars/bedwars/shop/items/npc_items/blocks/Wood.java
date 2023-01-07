package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class Wood extends PurchasableItem {

    public Wood(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.WOOD,
                (short) 0,
                32,
                false,
                "Wood Blocks",
                6,
                Currency.IRON,
                false,
                false
        );
    }
}
