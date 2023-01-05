package net.prismarray.openhivebedwars.bedwars.shop.items.general;

import net.prismarray.openhivebedwars.gui.InventoryGUIItem;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class ColoredGlassFrame extends InventoryGUIItem {

    public ColoredGlassFrame(DyeColor color) {
        super(Material.STAINED_GLASS_PANE, color.getWoolData(), 1, " ");
    }
}
