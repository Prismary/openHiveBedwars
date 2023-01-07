package net.prismarray.openhivebedwars.bedwars.shop.items.npc_specialist;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class SplashHealingPotion extends PurchasableItem {

    public SplashHealingPotion() {
        super(
                Material.POTION,
                (short) 16421,
                1,
                true,
                "Splash Healing Potion",
                30,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
