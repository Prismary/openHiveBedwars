package net.prismarray.openhivebedwars.gui;

import net.prismarray.openhivebedwars.gui.actions.InventoryGUIAction;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InventoryGUIBase implements Inventory {

    private final Inventory inventory;

    private final List<InventoryGUIActionListener> actionHandlers = new ArrayList<>();

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

    public void addActionHandler(InventoryGUIActionListener handler) {
        actionHandlers.add(handler);
    }

    public void handleAction(InventoryGUIAction action) {

        // handle all actions on top-level
        actionHandlers.forEach(l -> getMatchingHandlers(l, action).forEach(m -> invokeMethod(m, l, action)));

        if (!(action instanceof InventoryGUIClickAction)) {
            return;
        }

        InventoryGUIClickAction ica = (InventoryGUIClickAction) action;
        ItemStack item = getItem(ica.getClickedSlot());

        if (!(Objects.nonNull(item) && item instanceof InventoryGUIItem)) {
            return;
        }

        // handle all click actions on item-level
        ((InventoryGUIItem) item).handleClickAction(ica);
    }

    public static List<Method> getMatchingHandlers(InventoryGUIActionListener listener, InventoryGUIAction action) {

        return Arrays.stream(listener.getClass().getDeclaredMethods())
                .filter(m -> Arrays.stream(m.getAnnotations()).anyMatch(a -> a instanceof InventoryGUIActionHandler))
                .filter(m -> m.getParameterTypes().length == 1 && Objects.equals(m.getParameterTypes()[0], action.getClass()))
                .collect(Collectors.toList());
    }

    public static Object invokeMethod(Method method, Object obj, Object... args) {
        try {
            return method.invoke(obj, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
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
