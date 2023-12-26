package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_items;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks.*;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.DyeColor;

public class ItemsBlocksGUI extends FramedFullRowsGUI {

    public ItemsBlocksGUI(TeamColor teamColor) {
        super("Blocks", 4, DyeColor.LIME, true, () -> new ItemsRootGUI(teamColor));

        new Glass(this, 10, teamColor.woolColor);
        new Wool(this, 11, teamColor.woolColor);
        new Wood(this, 12);
        new Andesite(this, 13);
        new EndStone(this, 14);
        new StainedClay(this, 15);
        new Obsidian(this, 16);

        new WoolBridgeBuilder(this, 20, teamColor.woolColor);
        new WoodBridgeBuilder(this, 21);
        new AndesiteBridgeBuilder(this, 22);
        new EndStoneBridgeBuilder(this, 23);
        new StainedClayBridgeBuilder(this, 24, teamColor.woolColor);

        lock();
    }
}
