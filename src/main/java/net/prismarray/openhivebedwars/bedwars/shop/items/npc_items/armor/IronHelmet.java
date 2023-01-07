package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.armor;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class IronHelmet extends PurchasableItem {

    public IronHelmet(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.IRON_HELMET,
                (short) 0,
                1,
                false,
                "Iron Helmet",
                50,
                Currency.GOLD,
                false,
                false
        );
    }
}
