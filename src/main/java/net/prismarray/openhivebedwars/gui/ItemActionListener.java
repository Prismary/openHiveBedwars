package net.prismarray.openhivebedwars.gui;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;


public abstract class ItemActionListener implements Listener {

    private Inventory inventory;

    public ItemActionListener(Inventory target) {
        this.inventory = target;
    }


    @EventHandler
    public void onAction(InventoryEvent e) {

        if (!Objects.equals(e.getInventory(), inventory)) {
            return;
        }

        if (e instanceof InventoryCloseEvent) {
            onClose((InventoryCloseEvent) e);
            return;
        }

        if (e instanceof InventoryClickEvent) {

            InventoryClickEvent eClick = (InventoryClickEvent) e;

            eClick.getClick();

            return;
        }

        /*
        if (e instanceof InventoryDragEvent) {

        }
        */
    }


    public abstract void onClose(InventoryCloseEvent e);

    public abstract void onLeftClick();
    public abstract void onRightClick();

    public abstract void onShiftLeftClick();
    public abstract void onShiftRightClick();
}
