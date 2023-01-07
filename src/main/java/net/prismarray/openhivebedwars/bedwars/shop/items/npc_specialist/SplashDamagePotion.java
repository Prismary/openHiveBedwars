package net.prismarray.openhivebedwars.bedwars.shop.items.npc_specialist;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class SplashDamagePotion extends PurchasableItem {

    public SplashDamagePotion() {
        super(
                Material.POTION,
                (short) 16428,
                1,
                true,
                "Splash Damage Potion",
                40,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
