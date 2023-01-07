package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.armor;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class IronBoots extends PurchasableItem {

    public IronBoots(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.IRON_BOOTS,
                (short) 0,
                1,
                false,
                "Iron Boots",
                50,
                Currency.IRON,
                false,
                false
        );
    }
}
