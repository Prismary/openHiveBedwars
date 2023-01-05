package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root;

import net.prismarray.openhivebedwars.bedwars.shop.items.CategorySelector;
import org.bukkit.Material;

public class WeaponsTools extends CategorySelector {

    public WeaponsTools() {
        super(
                Material.IRON_SWORD,
                (short) 0,
                "§b§lWeapons & Tools",
                new String[]{"§7A choice of weapons and tools", "§7to use in your fights!"}
        );
    }
}
