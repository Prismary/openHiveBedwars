package net.prismarray.openhivebedwars.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

public class InventoryGUIPlayerHead extends InventoryGUIItem {

    public InventoryGUIPlayerHead() {
        this(null);
    }

    public InventoryGUIPlayerHead(InventoryGUIBase actionHandlingInventoryGUI, int slotInInventoryGUI) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, null);
    }

    public InventoryGUIPlayerHead(String playerName) {
        this(playerName, 1);
    }

    public InventoryGUIPlayerHead(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            String playerName
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, playerName, 1);
    }

    public InventoryGUIPlayerHead(String playerName, int amount) {
        this(playerName, amount, null);
    }

    public InventoryGUIPlayerHead(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            String playerName,
            int amount
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, playerName, amount, null);
    }

    public InventoryGUIPlayerHead(String playerName, int amount, String name) {
        this(playerName, amount, name, null);
    }

    public InventoryGUIPlayerHead(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            String playerName,
            int amount,
            String name
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, playerName, amount, name, null);
    }

    public InventoryGUIPlayerHead(String playerName, int amount, String name, String[] lore) {
        this(playerName, amount, name, lore, false);
    }

    public InventoryGUIPlayerHead(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            String playerName,
            int amount,
            String name,
            String[] lore
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, playerName, amount, name, lore, false);
    }

    public InventoryGUIPlayerHead(String playerName, int amount, String name, String[] lore, boolean enchanted) {
        this(playerName, amount, name, lore, enchanted, null);
    }

    public InventoryGUIPlayerHead(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            String playerName,
            int amount,
            String name,
            String[] lore,
            boolean enchanted
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, playerName, amount, name, lore, enchanted, null);
    }

    public InventoryGUIPlayerHead(
            String playerName,
            int amount,
            String name,
            String[] lore,
            boolean enchanted,
            ItemFlag[] flags
    ) {
        this(null, -1, playerName, amount, name, lore, enchanted, flags);
    }

    public InventoryGUIPlayerHead(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            String playerName,
            int amount,
            String name,
            String[] lore,
            boolean enchanted,
            ItemFlag[] flags
    ) {

        super(actionHandlingInventoryGUI, slotInInventoryGUI, Material.SKULL_ITEM, (short) 3, amount, name, lore, enchanted, flags);

        if (Objects.isNull(playerName)) {
            return;
        }

        SkullMeta meta = (SkullMeta) getItemMeta();
        meta.setOwner(playerName);
        setItemMeta(meta);
    }
}
