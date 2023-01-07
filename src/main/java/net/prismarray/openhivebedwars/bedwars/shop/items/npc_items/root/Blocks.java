package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root;

import net.prismarray.openhivebedwars.bedwars.shop.items.CategorySelector;
import org.bukkit.Material;

public class Blocks extends CategorySelector {

    public Blocks() {
        super(
                Material.STONE,
                (short) 6,
                "§a§lBlocks",
                new String[]{"§7Buy blocks to bridge, build and", "§7protect your bed!"}
        );
    }
}
