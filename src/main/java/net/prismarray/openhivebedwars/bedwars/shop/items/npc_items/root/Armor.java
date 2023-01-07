package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root;

import net.prismarray.openhivebedwars.bedwars.shop.items.CategorySelector;
import org.bukkit.Material;

public class Armor extends CategorySelector {

    public Armor() {
        super(
                Material.IRON_CHESTPLATE,
                (short) 0,
                "§c§lArmor",
                new String[]{"§7Be prepared when you encounter", "§7your evil enemies!"}
        );
    }
}
