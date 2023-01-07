package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_items;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import org.bukkit.DyeColor;

public class ItemsArmorGUI extends FramedFullRowsGUI {

    public ItemsArmorGUI() {
        super("Armor", 6, DyeColor.RED, true, ItemsRootGUI::new);

        lock();
    }
}
