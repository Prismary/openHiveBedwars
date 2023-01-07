package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.armor;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class DiamondLeggings extends PurchasableItem {

    public DiamondLeggings() {
        super(
                Material.DIAMOND_LEGGINGS,
                (short) 0,
                1,
                false,
                "Diamond Leggings",
                50,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
