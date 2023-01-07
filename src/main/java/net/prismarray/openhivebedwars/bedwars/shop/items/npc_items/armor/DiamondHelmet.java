package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.armor;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class DiamondHelmet extends PurchasableItem {

    public DiamondHelmet(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.DIAMOND_HELMET,
                (short) 0,
                1,
                false,
                "Diamond Helmet",
                200,
                Currency.GOLD,
                false,
                false
        );
    }
}
