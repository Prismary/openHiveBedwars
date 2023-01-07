package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_items;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.DyeColor;

public class ItemsArmorGUI extends FramedFullRowsGUI {

    public ItemsArmorGUI(TeamColor teamColor) {
        super("Armor", 6, DyeColor.RED, true, () -> new ItemsRootGUI(teamColor));

        lock();
    }
}
