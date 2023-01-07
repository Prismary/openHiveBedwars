package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class Wood extends PurchasableItem {

    public Wood() {
        super(
                Material.WOOD,
                (short) 0,
                32,
                false,
                "Wood Blocks",
                6,
                Currency.IRON,
                false,
                false
        );
    }
}
