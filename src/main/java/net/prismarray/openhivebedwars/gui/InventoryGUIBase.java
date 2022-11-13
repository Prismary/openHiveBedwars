package net.prismarray.openhivebedwars.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class InventoryGUIBase {

    private final Inventory inventory;


    public InventoryGUIBase() {
        this(null, null, null, null);
    }

    public InventoryGUIBase(@Nullable String title) {
        this(null, title, null, null);
    }

    public InventoryGUIBase(@Nullable InventoryType type) {
        this(null, null, type, null);
    }

    public InventoryGUIBase(@Nullable Integer size) {
        this(null, null, null, size);
    }

    public InventoryGUIBase(@Nullable String title, @Nullable InventoryType type) {
        this(null, title, type, null);
    }

    public InventoryGUIBase(@Nullable String title, @Nullable Integer size) {
        this(null, title, null, size);
    }

    public InventoryGUIBase(@Nullable String title, @Nullable InventoryType type, @Nullable Integer size) {
        this(null, title, type, size);
    }

    public InventoryGUIBase(@Nullable InventoryHolder holder, @Nullable String title, @Nullable InventoryType type, @Nullable Integer size) {

        if (Objects.isNull(title)) {
            title = "Inventory GUI";
        }

        if (Objects.isNull(type)) {
            type = (Objects.nonNull(size) && size == 5) ? InventoryType.HOPPER : InventoryType.CHEST;
        }

        if (Objects.isNull(size)) {
            size = type.getDefaultSize();
        }

        switch (type) {
            case CHEST:
                if (size < 0) {
                    throw new IllegalArgumentException("Argument 'size' must be positive.");
                }
                if (size % 9 != 0) {
                    throw new IllegalArgumentException("Argument 'size' must be a multiple of 9.");
                }

                this.inventory = Bukkit.createInventory(holder, size, title);
                break;

            case ENDER_CHEST:
                //fallthrough
            case DISPENSER:
                //fallthrough
            case DROPPER:
                //fallthrough
            case HOPPER:
                this.inventory = Bukkit.createInventory(holder, type, title);
                break;

            default:
                throw new IllegalArgumentException(
                        String.format("InventoryType '%s' is currently not supported!", type)
                );
        }
    }

    public InventoryHolder getHolder() {
        return this.inventory.getHolder();
    }

    public String getTitle() {
        return this.inventory.getTitle();
    }

    public InventoryType getType() {
        return this.inventory.getType();
    }

    public int getSize() {
        return this.inventory.getSize();
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }
}
