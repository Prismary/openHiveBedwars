package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.bridgebuilder.BridgeBuilderItem;
import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableCustomHead;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class WoolBridgeBuilder extends PurchasableCustomHead {

    public WoolBridgeBuilder(InventoryGUIBase gui, int slot, DyeColor color) {
        super(
                gui,
                slot,
                BridgeBuilderItem.getURLForMaterial(
                        Material.WOOL,
                        (OpenHiveBedwars.getBWConfig().getShopUseDefaultColorForPurchasableBridgeBuilders()) ?
                                DyeColor.WHITE.getWoolData() : color.getWoolData()
                ),
                1,
                false,
                "Wool Bridge Builder",
                25,
                Currency.IRON,
                false,
                false,
                new BridgeBuilderItem(
                        Material.WOOL,
                        32,
                        (OpenHiveBedwars.getBWConfig().getBridgeBuilderUseDefaultColorForPlaceableBridgeBuilders()) ?
                                DyeColor.WHITE.getWoolData() : color.getWoolData()
                )
        );
    }
}
