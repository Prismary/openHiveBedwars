package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.weapons_and_tools;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class DiamondSword extends PurchasableItem {

    public DiamondSword() {
        super(
                Material.DIAMOND_SWORD,
                (short) 0,
                1,
                false,
                "Diamond Sword",
                5,
                Currency.EMERALD,
                false,
                false
        );
    }
}
