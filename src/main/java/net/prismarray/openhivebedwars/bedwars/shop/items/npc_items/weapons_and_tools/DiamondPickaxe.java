package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.weapons_and_tools;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class DiamondPickaxe extends PurchasableItem {

    public DiamondPickaxe() {
        super(
                Material.DIAMOND_PICKAXE,
                (short) 0,
                1,
                false,
                "Diamond Pickaxe",
                2,
                Currency.EMERALD,
                false,
                false
        );
    }
}
