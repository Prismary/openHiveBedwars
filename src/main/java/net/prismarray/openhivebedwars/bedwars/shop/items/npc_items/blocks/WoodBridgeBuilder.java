package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.bridgebuilder.BridgeBuilderItem;
import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableCustomHead;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class WoodBridgeBuilder extends PurchasableCustomHead {

    public WoodBridgeBuilder(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                BridgeBuilderItem.getURLForMaterial(Material.WOOD),
                1,
                false,
                "Wood Bridge Builder",
                30,
                Currency.IRON,
                false,
                false,
                new BridgeBuilderItem(Material.WOOD, 32)
        );
    }
}
