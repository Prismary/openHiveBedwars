package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_items;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks.Glass;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.DyeColor;

public class ItemsBlocksGUI extends FramedFullRowsGUI {

    public ItemsBlocksGUI(TeamColor teamColor) {
        super("Blocks", 4, DyeColor.LIME, true, ItemsRootGUI::new);

        // Glass
        setItem(10, new Glass(teamColor.woolColor));


        lock();
    }
}
