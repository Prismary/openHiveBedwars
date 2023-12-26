package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.bridgebuilder.BridgeBuilderItem;
import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableCustomHead;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class AndesiteBridgeBuilder extends PurchasableCustomHead {

    public AndesiteBridgeBuilder(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                BridgeBuilderItem.getURLForMaterial(Material.STONE, (byte) 5),
                1,
                false,
                "Andesite Bridge Builder",
                25,
                Currency.GOLD,
                false,
                false,
                new BridgeBuilderItem(Material.STONE, 32, (byte) 5)
        );
    }
}
