package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.armor;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class IronLeggings extends PurchasableItem {

    public IronLeggings() {
        super(
                Material.IRON_LEGGINGS,
                (short) 0,
                1,
                false,
                "Iron Leggings",
                20,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
