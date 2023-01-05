package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_items;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.DummyCustomSlot;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.WeaponsTools;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUILeftClickAction;
import net.prismarray.openhivebedwars.util.Broadcast;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemsRootGUI extends FramedFullRowsGUI {

    public ItemsRootGUI() {
        super("Item Shop", 6, DyeColor.PURPLE, true, null);

        // TODO Blocks

        // TODO Armor

        // Weapons and Tools
        setItem(23, new WeaponsTools());

        addSlotClickActionHandler(23, new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUILeftClickAction a) {
                (new ItemsWeaponsGUI()).open(a.getPlayer());
            }
        });

        // TODO Traps

        // Dummy custom slots
        setItem(39, new DummyCustomSlot());
        setItem(40, new DummyCustomSlot());
        setItem(41, new DummyCustomSlot());

        lock();
    }
}
