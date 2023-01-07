package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.armor;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class ChainChestplate extends PurchasableItem {

    public ChainChestplate() {
        super(
                Material.CHAINMAIL_CHESTPLATE,
                (short) 0,
                1,
                false,
                "Chain Chestplate",
                10,
                Currency.GOLD,
                false,
                false
        );
    }
}
