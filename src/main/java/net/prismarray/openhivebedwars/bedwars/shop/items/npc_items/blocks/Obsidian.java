package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class Obsidian extends PurchasableItem {

    public Obsidian() {
        super(
                Material.OBSIDIAN,
                (short) 0,
                1,
                false,
                "Obsidian",
                1,
                Currency.EMERALD,
                false,
                false
        );
    }
}
