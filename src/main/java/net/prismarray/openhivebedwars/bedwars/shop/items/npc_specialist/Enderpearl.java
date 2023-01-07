package net.prismarray.openhivebedwars.bedwars.shop.items.npc_specialist;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class Enderpearl extends PurchasableItem {

    public Enderpearl() {
        super(
                Material.ENDER_PEARL,
                (short) 0,
                1,
                false,
                "Enderpearl",
                15,
                Currency.EMERALD,
                false,
                false
        );
    }
}
