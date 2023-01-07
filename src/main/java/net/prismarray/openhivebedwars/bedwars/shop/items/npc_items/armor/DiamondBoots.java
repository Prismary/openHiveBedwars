package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.armor;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class DiamondBoots extends PurchasableItem {

    public DiamondBoots(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.DIAMOND_BOOTS,
                (short) 0,
                1,
                false,
                "Diamond Boots",
                200,
                Currency.IRON,
                false,
                false
        );
    }
}
