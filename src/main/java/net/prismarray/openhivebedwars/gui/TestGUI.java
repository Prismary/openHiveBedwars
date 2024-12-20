package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.gui.actions.*;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIItem;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIPlayerHead;
import net.prismarray.openhivebedwars.util.Broadcast;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestGUI extends InventoryGUIBase {

    public TestGUI() {
        super("Test Shop", 5);

        setItem(0, new InventoryGUIItem(Material.DIAMOND, 1, "§bShop Diaz!!1!elf"));
        setItem(1, new InventoryGUIItem(Material.IRON_INGOT, 1, "§fSnack Iron ftw"));
        setItem(2, new InventoryGUIPlayerHead("paulklee2000"));
        setItem(3, new InventoryGUIItem(Material.STAINED_GLASS_PANE, (short) 2,1, "Color Test", Stream.of("Lore0", "§cLore1").collect(Collectors.toList()), false, null));
        setItem(4, new InventoryGUIItem(Material.BARRIER, 1, "§4Exit", null, true));

        addSlotClickActionHandler(0, new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUILeftClickAction a) {
                Broadcast.toPlayer(a.getPlayer(), "Ayyo, 1 dia...");
                a.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
            }
        });

        addSlotClickActionHandler(0, new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUIShiftLeftClickAction a) {
                Broadcast.toPlayer(a.getPlayer(), "Ayyo, all the dias!");
                a.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND, 64));
            }
        });


        addSlotClickActionHandler(1, new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUILeftClickAction a) {
                Broadcast.toPlayer(a.getPlayer(), "You shall get 1 iron ingot.");
                a.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_INGOT, 1));
            }
        });
        addSlotClickActionHandler(1, new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUIShiftLeftClickAction a) {
                Broadcast.toPlayer(a.getPlayer(), "You shall get 64 iron ingots.");
                a.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_INGOT, 64));
            }
        });


        addSlotClickActionHandler(4, new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUILeftOrShiftLeftClickAction a) {
                Broadcast.toPlayer(a.getPlayer(), "You quit, ya loser!");
                Bukkit.getScheduler().runTask(OpenHiveBedwars.getInstance(), () -> a.getPlayer().closeInventory());
            }
        });

        lock();
    }
}
