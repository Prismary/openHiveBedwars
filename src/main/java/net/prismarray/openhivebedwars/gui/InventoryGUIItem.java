package net.prismarray.openhivebedwars.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;

public class InventoryGUIItem extends ItemStack {

    private InventoryGUIBase actionHandlingInventoryGUI;
    private int slotInInventoryGUI;

    public InventoryGUIItem() {
        this(null);
    }

    public InventoryGUIItem(InventoryGUIBase actionHandlingInventoryGUI, int slotInInventoryGUI) {
        this(null, actionHandlingInventoryGUI, slotInInventoryGUI);
    }

    public InventoryGUIItem(Material material) {
        this(material, 1);
    }

    public InventoryGUIItem(Material material, InventoryGUIBase actionHandlingInventoryGUI, int slotInInventoryGUI) {
        this(material, 1, actionHandlingInventoryGUI, slotInInventoryGUI);
    }

    public InventoryGUIItem(Material material, short damage) {
        this(material, damage, 1);
    }

    public InventoryGUIItem(
            Material material,
            short damage,
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI
    ) {
        this(material, damage, 1, actionHandlingInventoryGUI, slotInInventoryGUI);
    }

    public InventoryGUIItem(Material material, int amount) {
        this(material, amount, null);
    }

    public InventoryGUIItem(
            Material material,
            int amount,
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI
    ) {
        this(material, amount, null, actionHandlingInventoryGUI, slotInInventoryGUI);
    }

    public InventoryGUIItem(Material material, short damage, int amount) {
        this(material, damage, amount, null);
    }

    public InventoryGUIItem(
            Material material,
            short damage,
            int amount,
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI
    ) {
        this(material, damage, amount, null, actionHandlingInventoryGUI, slotInInventoryGUI);
    }

    public InventoryGUIItem(Material material, int amount, String name) {
        this(material, amount, name, null);
    }

    public InventoryGUIItem(
            Material material,
            int amount,
            String name,
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI
    ) {
        this(material, amount, name, null, actionHandlingInventoryGUI, slotInInventoryGUI);
    }

    public InventoryGUIItem(Material material, short damage, int amount, String name) {
        this(material, damage, amount, name, null);
    }

    public InventoryGUIItem(
            Material material,
            short damage,
            int amount,
            String name,
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI
    ) {
        this(material, damage, amount, name, null, actionHandlingInventoryGUI, slotInInventoryGUI);
    }

    public InventoryGUIItem(Material material, int amount, String name, String[] lore) {
        this(material, amount, name, lore, false);
    }

    public InventoryGUIItem(
            Material material,
            int amount,
            String name,
            String[] lore,
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI
    ) {
        this(material, amount, name, lore, false, actionHandlingInventoryGUI, slotInInventoryGUI);
    }

    public InventoryGUIItem(Material material, short damage, int amount, String name, String[] lore) {
        this(material, damage, amount, name, lore, false);
    }

    public InventoryGUIItem(
            Material material,
            short damage,
            int amount,
            String name,
            String[] lore,
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI
    ) {
        this(material, damage, amount, name, lore, false, actionHandlingInventoryGUI, slotInInventoryGUI);
    }

    public InventoryGUIItem(Material material, int amount, String name, String[] lore, boolean enchanted) {
        this(material, amount, name, lore, enchanted, null);
    }

    public InventoryGUIItem(
            Material material,
            int amount,
            String name,
            String[] lore,
            boolean enchanted,
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI
    ) {
        this(material, amount, name, lore, enchanted, null, actionHandlingInventoryGUI, slotInInventoryGUI);
    }

    public InventoryGUIItem(Material material, short damage, int amount, String name, String[] lore, boolean enchanted) {
        this(material, damage, amount, name, lore, enchanted, null);
    }

    public InventoryGUIItem(
            Material material,
            short damage,
            int amount,
            String name,
            String[] lore,
            boolean enchanted,
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI
    ) {
        this(material, damage, amount, name, lore, enchanted, null, actionHandlingInventoryGUI, slotInInventoryGUI);
    }

    public InventoryGUIItem(Material material, int amount, String name, String[] lore, boolean enchanted, ItemFlag[] flags) {
        this(material, (short) 0, amount, name, lore, enchanted, flags);
    }

    public InventoryGUIItem(
            Material material,
            int amount,
            String name,
            String[] lore,
            boolean enchanted,
            ItemFlag[] flags,
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI
    ) {
        this(material, (short) 0, amount, name, lore, enchanted, flags, actionHandlingInventoryGUI, slotInInventoryGUI);
    }

    public InventoryGUIItem(
            Material material,
            short damage,
            int amount,
            String name,
            String[] lore,
            boolean enchanted,
            ItemFlag[] flags
    ) {
        this(material, damage, amount, name, lore, enchanted, flags, null, -1);
    }

    public InventoryGUIItem(
            Material material,
            short damage,
            int amount,
            String name,
            String[] lore,
            boolean enchanted,
            ItemFlag[] flags,
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI
    ) {

        super(
                (Objects.isNull(material)) ? Material.AIR : material,
                amount,
                damage
        );

        if (Objects.equals(getType(), Material.AIR)) {
            return;
        }

        ItemMeta meta = Bukkit.getItemFactory().getItemMeta(getType());

        if (Objects.nonNull(name)) {
            meta.setDisplayName(name);
        }

        if (Objects.nonNull(lore)) {
            meta.setLore(Arrays.asList(lore));
        }

        if (enchanted) {
            meta.addEnchant(Enchantment.getByName("InventoryGUIDummyEnchantment"), 1, true);
        }

        if (Objects.nonNull(flags)) {
            meta.addItemFlags(flags);

        } else {
            meta.addItemFlags(
                    ItemFlag.HIDE_ENCHANTS,
                    ItemFlag.HIDE_DESTROYS,
                    ItemFlag.HIDE_PLACED_ON,
                    ItemFlag.HIDE_POTION_EFFECTS,
                    ItemFlag.HIDE_UNBREAKABLE,
                    ItemFlag.HIDE_ATTRIBUTES
            );
        }

        setItemMeta(meta);

        this.actionHandlingInventoryGUI = actionHandlingInventoryGUI;
        this.slotInInventoryGUI = slotInInventoryGUI;

        if (Objects.isNull(actionHandlingInventoryGUI) || this.slotInInventoryGUI < 0
                || this.slotInInventoryGUI >= this.actionHandlingInventoryGUI.getSize()) {

            this.actionHandlingInventoryGUI = null;
            this.slotInInventoryGUI = -1;
            return;
        }

        actionHandlingInventoryGUI.setItem(slotInInventoryGUI, this);
    }

    protected void addActionListenerToContainingInventory(InventoryGUIActionListener listener) {

        if (Objects.isNull(actionHandlingInventoryGUI)) {
            throw new IllegalStateException(
                    "This InventoryGUIItem has no InventoryGUIBase instance associated with it. It is not possible " +
                            "to add ActionListeners to this instance without specifying the InventoryGUIBase " +
                            "instance that is supposed to manage the listeners in the constructor."
            );
        }

        actionHandlingInventoryGUI.addSlotClickActionHandler(slotInInventoryGUI, listener);
    }
}
