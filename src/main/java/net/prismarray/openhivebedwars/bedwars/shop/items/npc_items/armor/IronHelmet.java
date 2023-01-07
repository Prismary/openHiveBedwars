package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.armor;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class IronHelmet extends PurchasableItem {

    public IronHelmet() {
        super(
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
