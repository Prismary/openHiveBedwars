package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_items;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import org.bukkit.DyeColor;

public class ItemsWeaponsGUI extends FramedFullRowsGUI {

    public ItemsWeaponsGUI() {
        super("Weapons & Tools", 6, DyeColor.BLUE, true, ItemsRootGUI::new);
    }
}
