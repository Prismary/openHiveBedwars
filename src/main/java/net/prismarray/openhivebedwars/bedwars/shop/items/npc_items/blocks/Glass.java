package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class Glass extends PurchasableItem {

    public Glass(InventoryGUIBase gui, int slot, DyeColor color) {
        super(
                gui,
                slot,
                Material.STAINED_GLASS,
                color.getWoolData(),
                32,
                false,
                "Glass Blocks",
                5,
                Currency.IRON,
                false,
                false
        );
    }
}
