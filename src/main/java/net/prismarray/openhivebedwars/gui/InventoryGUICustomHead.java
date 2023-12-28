package net.prismarray.openhivebedwars.gui;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.UUID;

public class InventoryGUICustomHead extends InventoryGUIItem {

    public InventoryGUICustomHead() {
        this(null);
    }

    public InventoryGUICustomHead( InventoryGUIBase actionHandlingInventoryGUI, int slotInInventoryGUI) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, null);
    }

    public InventoryGUICustomHead(String url) {
        this(url, 1);
    }

    public InventoryGUICustomHead(InventoryGUIBase actionHandlingInventoryGUI, int slotInInventoryGUI, String url) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, url, 1);
    }

    public InventoryGUICustomHead(String url, int amount) {
        this(url, amount, null);
    }

    public InventoryGUICustomHead(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            String url,
            int amount
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, url, amount, null);
    }

    public InventoryGUICustomHead(String url, int amount, String name) {
        this(url, amount, name, null);
    }

    public InventoryGUICustomHead(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            String url,
            int amount,
            String name
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, url, amount, name, null);
    }

    public InventoryGUICustomHead(String url, int amount, String name, String[] lore) {
        this(url, amount, name, lore, false);
    }

    public InventoryGUICustomHead(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            String url,
            int amount,
            String name,
            String[] lore
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, url, amount, name, lore, false);
    }

    public InventoryGUICustomHead(String url, int amount, String name, String[] lore, boolean enchanted) {
        this(url, amount, name, lore, enchanted, null);
    }

    public InventoryGUICustomHead(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            String url,
            int amount,
            String name,
            String[] lore,
            boolean enchanted
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, url, amount, name, lore, enchanted, null);
    }

    public InventoryGUICustomHead(
            String url,
            int amount,
            String name,
            String[] lore,
            boolean enchanted,
            ItemFlag[] flags
    ) {
        this(null, -1, url, amount, name, lore, enchanted, flags);
    }

    public InventoryGUICustomHead(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            String url,
            int amount,
            String name,
            String[] lore,
            boolean enchanted,
            ItemFlag[] flags
    ) {

        super(actionHandlingInventoryGUI, slotInInventoryGUI, Material.SKULL_ITEM, (short) 3, amount, name, lore, enchanted, flags, false);

        if (Objects.isNull(url)) {
            addToContainingInventory(actionHandlingInventoryGUI, slotInInventoryGUI);
            return;
        }

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));


        SkullMeta meta = (SkullMeta) getItemMeta();

        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException();
        }

        setItemMeta(meta);

        addToContainingInventory(actionHandlingInventoryGUI, slotInInventoryGUI);
    }
}
