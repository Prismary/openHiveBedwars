package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

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
                BridgeBuilderItem.getURLForMaterial(Material.WOOL, color.getWoolData()), // ToDo: add config option to leave white wool for all teams (in shop)
                1,
                false,
                "Wool Bridge Builder",
                25,
                Currency.IRON,
                false,
                false,
                new BridgeBuilderItem(Material.WOOL, 32, color.getWoolData()) // ToDo: add config option to leave white wool for all teams (for all BBItems)
        );
    }
}
