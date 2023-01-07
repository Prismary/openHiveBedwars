package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.weapons_and_tools;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class IronAxe extends PurchasableItem {

    public IronAxe(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.IRON_AXE,
                (short) 0,
                1,
                false,
                "Iron Axe",
                5,
                Currency.GOLD,
                false,
                false
        );
    }
}
