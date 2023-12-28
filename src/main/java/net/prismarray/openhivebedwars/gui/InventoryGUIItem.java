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
        this(actionHandlingInventoryGUI, slotInInventoryGUI, null);
    }

    public InventoryGUIItem(Material material) {
        this(material, 1);
    }

    public InventoryGUIItem(InventoryGUIBase actionHandlingInventoryGUI, int slotInInventoryGUI, Material material) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, material, 1);
    }

    public InventoryGUIItem(Material material, short damage) {
        this(material, damage, 1);
    }

    public InventoryGUIItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            Material material,
            short damage
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, material, damage, 1);
    }

    public InventoryGUIItem(Material material, int amount) {
        this(material, amount, null);
    }

    public InventoryGUIItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            Material material,
            int amount
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, material, amount, null);
    }

    public InventoryGUIItem(Material material, short damage, int amount) {
        this(material, damage, amount, null);
    }

    public InventoryGUIItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            Material material,
            short damage,
            int amount
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, material, damage, amount, null);
    }

    public InventoryGUIItem(Material material, int amount, String name) {
        this(material, amount, name, null);
    }

    public InventoryGUIItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            Material material,
            int amount,
            String name
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, material, amount, name, null);
    }

    public InventoryGUIItem(Material material, short damage, int amount, String name) {
        this(material, damage, amount, name, null);
    }

    public InventoryGUIItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            Material material,
            short damage,
            int amount,
            String name
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, material, damage, amount, name, null);
    }

    public InventoryGUIItem(Material material, int amount, String name, String[] lore) {
        this(material, amount, name, lore, false);
    }

    public InventoryGUIItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            Material material,
            int amount,
            String name,
            String[] lore
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, material, amount, name, lore, false);
    }

    public InventoryGUIItem(Material material, short damage, int amount, String name, String[] lore) {
        this(material, damage, amount, name, lore, false);
    }

    public InventoryGUIItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            Material material,
            short damage,
            int amount,
            String name,
            String[] lore
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, material, damage, amount, name, lore, false);
    }

    public InventoryGUIItem(Material material, int amount, String name, String[] lore, boolean enchanted) {
        this(material, amount, name, lore, enchanted, null);
    }

    public InventoryGUIItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            Material material,
            int amount,
            String name,
            String[] lore,
            boolean enchanted
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, material, amount, name, lore, enchanted, null);
    }

    public InventoryGUIItem(Material material, short damage, int amount, String name, String[] lore, boolean enchanted) {
        this(material, damage, amount, name, lore, enchanted, null);
    }

    public InventoryGUIItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            Material material,
            short damage,
            int amount,
            String name,
            String[] lore,
            boolean enchanted
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, material, damage, amount, name, lore, enchanted, null);
    }

    public InventoryGUIItem(Material material, int amount, String name, String[] lore, boolean enchanted, ItemFlag[] flags) {
        this(material, (short) 0, amount, name, lore, enchanted, flags);
    }

    public InventoryGUIItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            Material material,
            int amount,
            String name,
            String[] lore,
            boolean enchanted,
            ItemFlag[] flags
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, material, (short) 0, amount, name, lore, enchanted, flags);
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
        this(null, -1, material, damage, amount, name, lore, enchanted, flags);
    }

    public InventoryGUIItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            Material material,
            short damage,
            int amount,
            String name,
            String[] lore,
            boolean enchanted,
            ItemFlag[] flags
    ) {
        this(actionHandlingInventoryGUI, slotInInventoryGUI, material, damage, amount, name, lore, enchanted, flags, true);
    }

    /**
     * IMPORTANT: If this constructor is used by any subclass with autoAddToContainingInventory = False, the subclass
     *            MUST call addToContainingInventory() in its constructor manually!
     *
     * @param actionHandlingInventoryGUI
     * @param slotInInventoryGUI
     * @param material
     * @param damage
     * @param amount
     * @param name
     * @param lore
     * @param enchanted
     * @param flags
     * @param autoAddToContainingInventory
     */
    protected InventoryGUIItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            Material material,
            short damage,
            int amount,
            String name,
            String[] lore,
            boolean enchanted,
            ItemFlag[] flags,
            boolean autoAddToContainingInventory
    ) {

        super(
                (Objects.isNull(material)) ? Material.AIR : material,
                amount,
                damage
        );

        if (Objects.equals(getType(), Material.AIR)) {
            return;
        }

        ItemMeta meta = getItemMeta();

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

        if (autoAddToContainingInventory) {
            addToContainingInventory(actionHandlingInventoryGUI, slotInInventoryGUI);

        } else {
            this.actionHandlingInventoryGUI = null;
            this.slotInInventoryGUI = -1;
        }
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

    protected void addToContainingInventory(InventoryGUIBase containingInventory, int slot) {

        if (Objects.nonNull(this.actionHandlingInventoryGUI)) {
            throw new IllegalStateException(
                    "This InventoryGUIItem is already associated to an existing InventoryGUIBase object. It is " +
                            "impossible to add the same object to multiple GUIs. Please create a new instance for " +
                            "each GUI."
            );
        }

        if (Objects.isNull(containingInventory) || slot < 0 || slot >= containingInventory.getSize()) {

            this.actionHandlingInventoryGUI = null;
            this.slotInInventoryGUI = -1;
            return;
        }

        this.actionHandlingInventoryGUI = containingInventory;
        this.slotInInventoryGUI = slot;

        actionHandlingInventoryGUI.setItem(slotInInventoryGUI, this);
    }
}
