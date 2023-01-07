package net.prismarray.openhivebedwars.bedwars.shop.items.npc_specialist;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class SplashPoisonPotion extends PurchasableItem {

    public SplashPoisonPotion() {
        super(
                Material.POTION,
                (short) 16388,
                1,
                true,
                "Splash Poison Potion",
                60,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
