package net.prismarray.openhivebedwars.bedwars.shop.items.npc_specialist;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class PoisonCure extends PurchasableItem {

    public PoisonCure() {
        super(
                Material.MAGMA_CREAM,
                (short) 0,
                1,
                false,
                "Poison Cure",
                5,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
