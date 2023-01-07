package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.weapons_and_tools;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class Arrow extends PurchasableItem {

    public Arrow() {
        super(
                Material.ARROW,
                (short) 0,
                10,
                false,
                "Arrow",
                15,
                Currency.IRON,
                false,
                false
        );
    }
}
