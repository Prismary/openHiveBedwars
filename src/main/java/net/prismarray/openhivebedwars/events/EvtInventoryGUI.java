package net.prismarray.openhivebedwars.events;

import net.prismarray.openhivebedwars.gui.InventoryGUIActionManager;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.actions.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.*;

import java.util.*;
import java.util.stream.Collectors;

public class EvtInventoryGUI extends EventBase {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(InventoryCloseEvent e) {

        if (!InventoryGUIActionManager.isRegistered(e.getInventory())) {
            return;
        }

        InventoryGUIBase invGUI = InventoryGUIActionManager.getCorrespondingInventoryGUIBase(e.getInventory());
        Player player = (Player) e.getPlayer();

        InventoryGUIActionManager.handleInventoryGUIAction(new InventoryGUICloseAction(invGUI, player));
        InventoryGUIActionManager.unregisterInventoryGUI(invGUI);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {

        if (!InventoryGUIActionManager.isRegistered(e.getInventory())) {
            return;
        }

        if (e.isCancelled() || Objects.equals(e.getResult(), Event.Result.DENY)) {
            return;
        }

        InventoryGUIBase invGUI = InventoryGUIActionManager.getCorrespondingInventoryGUIBase(e.getInventory());
        List<Integer> concernedSlots = e.getRawSlots().stream()
                .filter(slot -> slot < e.getInventory().getSize())
                .collect(Collectors.toList());

        if (concernedSlots.size() == 0) {
            return;
        }

        if (invGUI.isLocked() || concernedSlots.stream().anyMatch(invGUI::isSlotLocked)) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryInteraction(InventoryClickEvent e) {

        if (!InventoryGUIActionManager.isRegistered(e.getInventory())) {
            return;
        }

        if (e.isCancelled() || Objects.equals(e.getResult(), Event.Result.DENY)) {
            return;
        }

        InventoryGUIBase invGUI = InventoryGUIActionManager.getCorrespondingInventoryGUIBase(e.getInventory());

        if (!Objects.equals(e.getInventory(), e.getClickedInventory())) {

            if (
                    invGUI.isLocked()
                            && e.getClick() != ClickType.LEFT
                            && e.getClick() != ClickType.RIGHT
                            && e.getClick() != ClickType.MIDDLE
                            && e.getClick() != ClickType.DROP
                            && e.getClick() != ClickType.CONTROL_DROP
                            && e.getClick() != ClickType.NUMBER_KEY
            ) {
                e.setCancelled(true);
                e.setResult(Event.Result.DENY);
            }
            return;
        }

        Player player = (Player) e.getWhoClicked();
        int invSlot = e.getSlot();

        if (invGUI.isLocked() || invGUI.isSlotLocked(invSlot)) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }

        InventoryGUIAction action;

        switch (e.getClick()) {
            case LEFT:
                action = new InventoryGUILeftClickAction(invGUI, player, invSlot);
                break;

            case SHIFT_LEFT:
                action = new InventoryGUIShiftLeftClickAction(invGUI, player, invSlot);
                break;

            case RIGHT:
                action = new InventoryGUIRightClickAction(invGUI, player, invSlot);
                break;

            case SHIFT_RIGHT:
                action = new InventoryGUIShiftRightClickAction(invGUI, player, invSlot);
                break;

            case MIDDLE:
                action = new InventoryGUIMiddleClickAction(invGUI, player, invSlot);
                break;

            default:
                return;
        }

        InventoryGUIActionManager.handleInventoryGUIAction(action);
    }
}
