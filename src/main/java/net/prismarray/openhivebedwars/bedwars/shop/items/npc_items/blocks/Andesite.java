package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class Andesite extends PurchasableItem {

    public Andesite(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.STONE,
                (short) 6,
                32,
                false,
                "Andesite Blocks",
                5,
                Currency.GOLD,
                false,
                false
        );
    }
}
