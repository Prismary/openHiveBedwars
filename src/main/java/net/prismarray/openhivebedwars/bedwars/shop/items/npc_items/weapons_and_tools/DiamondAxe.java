package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.weapons_and_tools;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class DiamondAxe extends PurchasableItem {

    public DiamondAxe() {
        super(
                Material.DIAMOND_AXE,
                (short) 0,
                1,
                false,
                "Diamond Axe",
                15,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
