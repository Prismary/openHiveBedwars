package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_items;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.Armor;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.Blocks;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.DummyCustomSlot;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root.WeaponsAndTools;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;

public class ItemsRootGUI extends FramedFullRowsGUI {

    public ItemsRootGUI() {
        super("Item Shop", 6, DyeColor.PURPLE, true, null);

        // Blocks
        setItem(19, new Blocks());
        addSlotClickActionHandler(19, new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUIClickAction a) {
                Bukkit.getScheduler().runTask(OpenHiveBedwars.getInstance(), () -> (
                        new ItemsBlocksGUI(Game.getTeamHandler().getPlayerTeam(a.getPlayer()).getColor())).open(a.getPlayer())
                );
            }
        });

        // Armor
        setItem(21, new Armor());
        addSlotClickActionHandler(21, new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUIClickAction a) {
                Bukkit.getScheduler().runTask(
                        OpenHiveBedwars.getInstance(), () -> (new ItemsArmorGUI()).open(a.getPlayer())
                );
            }
        });


        // Weapons and Tools
        setItem(23, new WeaponsAndTools());
        addSlotClickActionHandler(23, new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUIClickAction a) {
                Bukkit.getScheduler().runTask(
                        OpenHiveBedwars.getInstance(), () -> (new ItemsWeaponsGUI()).open(a.getPlayer())
                );
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
