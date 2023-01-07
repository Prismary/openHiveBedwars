package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.armor;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class ChainBoots extends PurchasableItem {

    public ChainBoots() {
        super(
                Material.CHAINMAIL_BOOTS,
                (short) 0,
                1,
                false,
                "Chain Boots",
                10,
                Currency.IRON,
                false,
                false
        );
    }
}
