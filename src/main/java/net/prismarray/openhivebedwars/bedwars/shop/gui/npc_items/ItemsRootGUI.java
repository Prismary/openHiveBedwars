package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_items;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.Armor;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.Blocks;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.DummyCustomSlot;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.WeaponsAndTools;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.DyeColor;

public class ItemsRootGUI extends FramedFullRowsGUI {

    public ItemsRootGUI(TeamColor teamColor) {
        super("Item Shop", 6, DyeColor.PURPLE, true, null);


        new Blocks(this, 19, () -> new ItemsBlocksGUI(teamColor));
        new Armor(this, 21, () -> new ItemsArmorGUI(teamColor));
        new WeaponsAndTools(this, 23, () -> new ItemsWeaponsGUI(teamColor));
        // TODO Traps

        // Dummy custom slots
        new DummyCustomSlot(this, 39);
        new DummyCustomSlot(this, 40);
        new DummyCustomSlot(this, 41);

        lock();
    }
}
