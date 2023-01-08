package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class EndStone extends PurchasableItem {

    public EndStone(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.ENDER_STONE,
                (short) 0,
                32,
                false,
                "End Stone Blocks §4(unknown)", // TODO find original price
                10,
                Currency.GOLD,
                false,
                false
        );
    }
}
