package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class Andesite extends PurchasableItem {

    public Andesite() {
        super(
                Material.STONE,
                (short) 6,
                32,
                false,
                "Andesite Blocks",
                5,
                Currency.GOLD,
                false,
                false
        );
    }
}
