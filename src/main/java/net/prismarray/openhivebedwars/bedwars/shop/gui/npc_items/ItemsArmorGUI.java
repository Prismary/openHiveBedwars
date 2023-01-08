package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_items;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.armor.*;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.DyeColor;

public class ItemsArmorGUI extends FramedFullRowsGUI {

    public ItemsArmorGUI(TeamColor teamColor) {
        super("Armor", 6, DyeColor.RED, true, () -> new ItemsRootGUI(teamColor));

        new ChainHelmet(this, 10);
        new ChainChestplate(this, 19);
        new ChainLeggings(this, 28);
        new ChainBoots(this, 37);

        new IronHelmet(this, 13);
        new IronChestplate(this, 22);
        new IronLeggings(this, 31);
        new IronBoots(this, 40);

        new DiamondHelmet(this, 16);
        new DiamondChestplate(this, 25);
        new DiamondLeggings(this, 34);
        new DiamondBoots(this, 43);

        lock();
    }
}
