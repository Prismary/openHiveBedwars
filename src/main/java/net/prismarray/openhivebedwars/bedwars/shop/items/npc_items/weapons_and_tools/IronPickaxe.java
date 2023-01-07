package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.weapons_and_tools;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class IronPickaxe extends PurchasableItem {

    public IronPickaxe(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.IRON_PICKAXE,
                (short) 0,
                1,
                false,
                "Iron Pickaxe",
                10,
                Currency.GOLD,
                false,
                false
        );
    }
}
