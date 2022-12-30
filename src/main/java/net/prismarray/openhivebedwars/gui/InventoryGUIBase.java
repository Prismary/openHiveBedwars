package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.gui.actions.InventoryGUIAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InventoryGUIBase implements Inventory {

    private final Inventory inventory;

    private final List<InventoryGUIActionHandler<? extends InventoryGUIAction>> actionHandlers = new ArrayList<>();

    private boolean isLocked = true;


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

        InventoryGUIActionManager.registerInventoryGUI(this);
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    public void addActionHandler(InventoryGUIActionHandler<? extends InventoryGUIAction> handler) {
        actionHandlers.add(handler);
    }

    public void handleAction(InventoryGUIAction action) {

        // TODO: handle all top-level actions
        actionHandlers.stream()
                .filter(h -> Objects.equals(action.getClass(), h.getActionType()))
                .forEach(h -> h.onAction(action));

        // TODO: handle all item-level actions
    }

    public void lock() {
        this.isLocked = true;
    }

    public void unlock() {
        this.isLocked = false;
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    @Override
    public int getSize() {
        return inventory.getSize();
    }

    @Override
    public int getMaxStackSize() {
        return inventory.getMaxStackSize();
    }

    @Override
    public void setMaxStackSize(int i) {
        inventory.setMaxStackSize(i);
    }

    @Override
    public String getName() {
        return inventory.getName();
    }

    @Override
    public ItemStack getItem(int i) {
        return inventory.getItem(i);
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {

        if (isLocked) {
            return;
        }
        inventory.setItem(i, itemStack);
    }

    @Override
    public HashMap<Integer, ItemStack> addItem(ItemStack... itemStacks) throws IllegalArgumentException {

        if (isLocked) {
            HashMap<Integer, ItemStack> notAdded = new HashMap<>();
            AtomicInteger index = new AtomicInteger();

            Arrays.stream(itemStacks).forEach(e -> notAdded.put(index.getAndIncrement(), e));
            return notAdded;
        }
        return inventory.addItem(itemStacks);
    }

    @Override
    public HashMap<Integer, ItemStack> removeItem(ItemStack... itemStacks) throws IllegalArgumentException {

        if (isLocked) {
            HashMap<Integer, ItemStack> notAdded = new HashMap<>();
            AtomicInteger index = new AtomicInteger();

            Arrays.stream(itemStacks).forEach(e -> notAdded.put(index.getAndIncrement(), e));
            return notAdded;
        }
        return inventory.removeItem(itemStacks);
    }

    @Override
    public ItemStack[] getContents() {
        return inventory.getContents();
    }

    @Override
    public void setContents(ItemStack[] itemStacks) throws IllegalArgumentException {

        if (isLocked) {
            return;
        }
        inventory.setContents(itemStacks);
    }

    @Deprecated
    @Override
    public boolean contains(int i) {
        return inventory.contains(i);
    }

    @Override
    public boolean contains(Material material) throws IllegalArgumentException {
        return inventory.contains(material);
    }

    @Override
    public boolean contains(ItemStack itemStack) {
        return inventory.contains(itemStack);
    }

    @Deprecated
    @Override
    public boolean contains(int i, int i1) {
        return inventory.contains(i, i1);
    }

    @Override
    public boolean contains(Material material, int i) throws IllegalArgumentException {
        return inventory.contains(material, i);
    }

    @Override
    public boolean contains(ItemStack itemStack, int i) {
        return inventory.contains(itemStack, i);
    }

    @Override
    public boolean containsAtLeast(ItemStack itemStack, int i) {
        return inventory.containsAtLeast(itemStack, i);
    }

    @Deprecated
    @Override
    public HashMap<Integer, ? extends ItemStack> all(int i) {
        return inventory.all(i);
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(Material material) throws IllegalArgumentException {
        return inventory.all(material);
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(ItemStack itemStack) {
        return inventory.all(itemStack);
    }

    @Deprecated
    @Override
    public int first(int i) {
        return inventory.first(i);
    }

    @Override
    public int first(Material material) throws IllegalArgumentException {
        return inventory.first(material);
    }

    @Override
    public int first(ItemStack itemStack) {
        return inventory.first(itemStack);
    }

    @Override
    public int firstEmpty() {
        return inventory.firstEmpty();
    }

    @Deprecated
    @Override
    public void remove(int i) {

        if (isLocked) {
            return;
        }
        inventory.remove(i);
    }

    @Override
    public void remove(Material material) throws IllegalArgumentException {

        if (isLocked) {
            return;
        }
        inventory.remove(material);
    }

    @Override
    public void remove(ItemStack itemStack) {

        if (isLocked) {
            return;
        }
        inventory.remove(itemStack);
    }

    @Override
    public void clear(int i) {

        if (isLocked) {
            return;
        }
        inventory.clear(i);
    }

    @Override
    public void clear() {

        if (isLocked) {
            return;
        }
        inventory.clear();
    }

    @Override
    public List<HumanEntity> getViewers() {
        return inventory.getViewers();
    }

    @Override
    public String getTitle() {
        return inventory.getTitle();
    }

    @Override
    public InventoryType getType() {
        return inventory.getType();
    }

    @Override
    public InventoryHolder getHolder() {
        return inventory.getHolder();
    }

    @Override
    public ListIterator<ItemStack> iterator() {
        return inventory.iterator();
    }

    @Override
    public ListIterator<ItemStack> iterator(int i) {
        return inventory.iterator(i);
    }
}
