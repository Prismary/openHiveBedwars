package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class StainedClay extends PurchasableItem {

    public StainedClay(InventoryGUIBase gui, int slot, DyeColor color) {
        super(
                gui,
                slot,
                Material.STAINED_CLAY,
                (OpenHiveBedwars.getBWConfig().getShopUseDefaultColorsForPurchasableBlocks()) ? DyeColor.WHITE.getWoolData() : color.getWoolData(),
                32,
                false,
                "Stained Clay",
                10,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
