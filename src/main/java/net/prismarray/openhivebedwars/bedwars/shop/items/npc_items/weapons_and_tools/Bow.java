package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.weapons_and_tools;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class Bow extends PurchasableItem {

    public Bow() {
        super(
                Material.BOW,
                (short) 0,
                1,
                false,
                "Bow",
                5,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
