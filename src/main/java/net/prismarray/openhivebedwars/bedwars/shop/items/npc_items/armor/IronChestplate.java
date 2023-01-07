package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.armor;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class IronChestplate extends PurchasableItem {

    public IronChestplate() {
        super(
                Material.IRON_CHESTPLATE,
                (short) 0,
                1,
                false,
                "Iron Chestplate",
                2,
                Currency.EMERALD,
                false,
                false
        );
    }
}
