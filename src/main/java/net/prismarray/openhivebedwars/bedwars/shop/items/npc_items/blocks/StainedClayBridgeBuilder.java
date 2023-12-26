package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

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
                BridgeBuilderItem.getURLForMaterial(Material.STAINED_CLAY, color.getWoolData()), // ToDo: add config option to leave white stained clay for all teams (in shop)
                1,
                false,
                "Stained Clay Bridge Builder",
                50,
                Currency.DIAMOND,
                false,
                false,
                new BridgeBuilderItem(Material.STAINED_CLAY, 32, color.getWoolData()) // ToDo: add config option to leave white stained clay for all teams (for all BBItems)
        );
    }
}
