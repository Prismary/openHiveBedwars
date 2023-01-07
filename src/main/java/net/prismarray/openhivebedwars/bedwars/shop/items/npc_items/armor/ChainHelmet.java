package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.armor;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class ChainHelmet extends PurchasableItem {

    public ChainHelmet(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.CHAINMAIL_HELMET,
                (short) 0,
                1,
                false,
                "Chain Helmet",
                10,
                Currency.IRON,
                false,
                false
        );
    }
}
