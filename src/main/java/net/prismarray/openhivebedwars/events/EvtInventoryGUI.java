package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionManager;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.actions.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EvtInventoryGUI extends EventBase {

    public EvtInventoryGUI(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(InventoryCloseEvent e) {

        if (!(e.getInventory() instanceof InventoryGUIBase)) {
            return;
        }

        InventoryGUIBase invGUI = (InventoryGUIBase) e.getInventory();
        Player player = (Player) e.getPlayer();

        InventoryGUIActionManager.handleInventoryGUIAction(new InventoryGUICloseAction(invGUI, player));
        InventoryGUIActionManager.unregisterInventoryGUI(invGUI);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryInteraction(InventoryInteractEvent e) {

        if (!(e.getInventory() instanceof InventoryGUIBase)) {
            return;
        }

        if (e.isCancelled() || Objects.equals(e.getResult(), Event.Result.DENY)) {
            return;
        }

        InventoryGUIBase invGUI = (InventoryGUIBase) e.getInventory();
        Player player = (Player) e.getWhoClicked();

        if (invGUI.isLocked()) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }

        if (!(e instanceof InventoryClickEvent)) {
            return;
        }

        InventoryClickEvent eClick = (InventoryClickEvent) e;
        int invSlot = eClick.getSlot();


        List<InventoryGUIAction> actions = new ArrayList<>();

        if (eClick.isLeftClick()) {

            actions.add(new InventoryGUILeftOrShiftLeftClickAction(invGUI, player, invSlot));

            if (eClick.isShiftClick()) {
                actions.add(new InventoryGUIShiftLeftClickAction(invGUI, player, invSlot));
            } else {
                actions.add(new InventoryGUILeftClickAction(invGUI, player, invSlot));
            }
        }

        if (eClick.isRightClick()) {

            actions.add(new InventoryGUIRightOrShiftRightClickAction(invGUI, player, invSlot));

            if (eClick.isShiftClick()) {
                actions.add(new InventoryGUIShiftRightClickAction(invGUI, player, invSlot));
            } else {
                actions.add(new InventoryGUIRightClickAction(invGUI, player, invSlot));
            }
        }

        if (Objects.equals(eClick.getClick(), ClickType.MIDDLE)) {
            actions.add(new InventoryGUIMiddleClickAction(invGUI, player, invSlot));
        }

        actions.forEach(InventoryGUIActionManager::handleInventoryGUIAction);
    }
}
