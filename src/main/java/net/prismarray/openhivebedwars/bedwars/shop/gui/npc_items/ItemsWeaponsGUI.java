package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_items;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.DyeColor;

public class ItemsWeaponsGUI extends FramedFullRowsGUI {

    public ItemsWeaponsGUI(TeamColor teamColor) {
        super("Weapons & Tools", 6, DyeColor.BLUE, true, () -> new ItemsRootGUI(teamColor));

        lock();
    }
}
