package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_items;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.Armor;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.Blocks;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.DummyCustomSlot;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.WeaponsAndTools;
import net.prismarray.openhivebedwars.gui.InventoryGUIContext;
import org.bukkit.DyeColor;

public class ItemsRootGUI extends FramedFullRowsGUI {

    public ItemsRootGUI(InventoryGUIContext context) {
        super("Item Shop", 6, DyeColor.PURPLE, true, null, null);


        new Blocks(this, 19, () -> new ItemsBlocksGUI(context));
        new Armor(this, 21, () -> new ItemsArmorGUI(context));
        new WeaponsAndTools(this, 23, () -> new ItemsWeaponsGUI(context));
        // TODO Traps

        // Dummy custom slots
        new DummyCustomSlot(this, 39);
        new DummyCustomSlot(this, 40);
        new DummyCustomSlot(this, 41);

        lock();
    }
}
