package net.prismarray.openhivebedwars.util;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.shop.items.general.PreviousButton;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.InventoryGUIItem;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import net.prismarray.openhivebedwars.bedwars.shop.items.general.CancelButton;
import net.prismarray.openhivebedwars.bedwars.shop.items.general.ColoredGlassFrame;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;

import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public class GUIBuilder {

    public static void setCancelButton(InventoryGUIBase gui, int width, int height) {
        int slot = (height - 1) * width + width / 2;
        gui.setItem(slot, new CancelButton());

        gui.addSlotClickActionHandler(slot, new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUIClickAction a) {
                Bukkit.getScheduler().runTask(OpenHiveBedwars.getInstance(), () -> a.getPlayer().closeInventory());
            }
        });
    }

    public static void setPreviousButton(InventoryGUIBase gui, int width, int height, Callable<? extends InventoryGUIBase> destinationGUIFactory) {
        int slot = ((height - 1) * width + width / 2) - 1;
        gui.setItem(slot, new PreviousButton());

        gui.addSlotClickActionHandler(slot, new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUIClickAction a) {
                Bukkit.getScheduler().runTask(OpenHiveBedwars.getInstance(), () -> {
                    try {
                        destinationGUIFactory.call().open(a.getPlayer());
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                });

            }
        });
    }

    public static void colorFrame(InventoryGUIBase gui, DyeColor color, int width, int height) {
        frame(gui, new ColoredGlassFrame(color), width, height);
    }

    public static void frame(InventoryGUIBase gui, InventoryGUIItem item, int width, int height) {

        IntStream.range(0, width * height)
                .filter(i -> i < width || i >= (height - 1) * width || i % width == 0 || i % width == height - 1)
                .forEach(i -> gui.setItem(i, item));
    }
}
