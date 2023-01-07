package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.weapons_and_tools;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class IronSword extends PurchasableItem {

    public IronSword(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.IRON_SWORD,
                (short) 0,
                1,
                false,
                "Iron Sword",
                10,
                Currency.GOLD,
                false,
                false
        );
    }
}
