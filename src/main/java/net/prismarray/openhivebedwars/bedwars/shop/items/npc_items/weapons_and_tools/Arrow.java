package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.weapons_and_tools;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class Arrow extends PurchasableItem {

    public Arrow(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.ARROW,
                (short) 0,
                10,
                false,
                "Arrow",
                15,
                Currency.IRON,
                false,
                false
        );
    }
}
