package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.bridgebuilder.BridgeBuilderItem;
import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableCustomHead;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class StainedClayBridgeBuilder extends PurchasableCustomHead {

    public StainedClayBridgeBuilder(InventoryGUIBase gui, int slot, DyeColor color) {

        super(
                gui,
                slot,
                BridgeBuilderItem.getURLForMaterial(
                        Material.STAINED_CLAY,
                        (OpenHiveBedwars.getBWConfig().getShopUseDefaultColorForPurchasableBridgeBuilders()) ?
                                DyeColor.WHITE.getWoolData() : color.getWoolData()
                ),
                1,
                false,
                "Stained Clay Bridge Builder",
                50,
                Currency.DIAMOND,
                false,
                false,
                new BridgeBuilderItem(
                        Material.STAINED_CLAY,
                        32,
                        (OpenHiveBedwars.getBWConfig().getBridgeBuilderUseDefaultColorForPlaceableBridgeBuilders()) ?
                                DyeColor.WHITE.getWoolData() : color.getWoolData()
                )
        );
    }
}
