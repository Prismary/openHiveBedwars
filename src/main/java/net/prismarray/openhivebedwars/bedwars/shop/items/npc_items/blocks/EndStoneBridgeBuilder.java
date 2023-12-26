package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.bridgebuilder.BridgeBuilderItem;
import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableCustomHead;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class EndStoneBridgeBuilder extends PurchasableCustomHead {

    public EndStoneBridgeBuilder(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                BridgeBuilderItem.getURLForMaterial(Material.ENDER_STONE),
                1,
                false,
                "End Stone Bridge Builder",
                40,
                Currency.GOLD,
                false,
                false,
                new BridgeBuilderItem(Material.ENDER_STONE, 32)
        );
    }
}
