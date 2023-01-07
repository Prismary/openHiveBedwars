package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class StainedClay extends PurchasableItem {

    public StainedClay() {
        super(
                Material.STAINED_CLAY,
                (short) 0,
                32,
                false,
                "Stained Clay",
                10,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
