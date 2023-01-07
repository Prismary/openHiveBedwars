package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.weapons_and_tools;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class StoneSword extends PurchasableItem {

    public StoneSword() {
        super(
                Material.IRON_SWORD,
                (short) 0,
                1,
                false,
                "Stone Sword",
                5,
                Currency.IRON,
                false,
                false
        );
    }
}
