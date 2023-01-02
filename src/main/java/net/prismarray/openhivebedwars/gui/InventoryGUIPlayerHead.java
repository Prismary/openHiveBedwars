package net.prismarray.openhivebedwars.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.SkullMeta;

public class InventoryGUIPlayerHead extends InventoryGUIItem {

    public InventoryGUIPlayerHead(String playerName) {
        this(playerName, 1);
    }

    public InventoryGUIPlayerHead(String playerName, int amount) {
        this(playerName, amount, null);
    }

    public InventoryGUIPlayerHead(String playerName, int amount, String name) {
        this(playerName, amount, name, null);
    }

    public InventoryGUIPlayerHead(String playerName, int amount, String name, String[] lore) {
        this(playerName, amount, name, lore, false);
    }

    public InventoryGUIPlayerHead(String playerName, int amount, String name, String[] lore, boolean enchanted) {
        this(playerName, amount, name, lore, enchanted, null);
    }

    public InventoryGUIPlayerHead(
            String playerName,
            int amount,
            String name,
            String[] lore,
            boolean enchanted,
            ItemFlag[] flags
    ) {

        super(Material.SKULL_ITEM, (short) 3, amount, name, lore, enchanted, flags);

        SkullMeta meta = (SkullMeta) getItemMeta();
        meta.setOwner(playerName);
        setItemMeta(meta);
    }
}