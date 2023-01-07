package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.armor;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class DiamondChestplate extends PurchasableItem {

    public DiamondChestplate(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.DIAMOND_CHESTPLATE,
                (short) 0,
                1,
                false,
                "Diamond Chestplate",
                5,
                Currency.EMERALD,
                false,
                false
        );
    }
}
