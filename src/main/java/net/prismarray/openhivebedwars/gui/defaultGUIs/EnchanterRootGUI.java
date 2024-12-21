package net.prismarray.openhivebedwars.gui.defaultGUIs;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.gui.InventoryGUIContext;
import net.prismarray.openhivebedwars.gui.components.EnchantableItem;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EnchanterRootGUI extends InventoryGUIBase {

    public EnchanterRootGUI(InventoryGUIContext context) {
        super(
                "§8§lChoose item to enchant",
                InventoryGUIBase.getSmallestPossibleInventorySize(getEnchantableItemCount(context.getOpeningPlayer()))
        );

        int currentSlot = 0;
        Map<String, ItemStack> enchantableItems = getEnchantableItems(context.getOpeningPlayer());
        List<String> sortedKeys = enchantableItems.keySet().stream().sorted(
                (a, b) -> (a.startsWith("A") ? -100 : 0) + (b.startsWith("A") ? +100 : 0)
                        + Integer.parseInt(a.replaceAll("A", ""))
                        - Integer.parseInt(b.replaceAll("A", ""))
        ).collect(Collectors.toList());

        for (String slotDescriptor : sortedKeys) {
            new EnchantableItem(
                    this,
                    currentSlot++,
                    enchantableItems.get(slotDescriptor),
                    slotDescriptor
            );
        }

        lock();
    }

    private static int getEnchantableItemCount(Player p) {

        return getEnchantableItems(p).size();
    }

    private static Map<String, ItemStack> getEnchantableItems(Player p) {

        Map<String, ItemStack> items = new HashMap<>();

        ItemStack[] armorContents = p.getInventory().getArmorContents();
        ItemStack[] contents = p.getInventory().getContents();

        items.putAll(
                IntStream.range(0, armorContents.length).boxed()
                        .filter(i -> Objects.nonNull(armorContents[i]))
                        .filter(i -> OpenHiveBedwars.getBWConfig().getEnchanterEnchantableItems()
                                .isEnchantableMaterial(armorContents[i].getType()))
                        .collect(Collectors.toMap(i -> String.format("A%s", i), i -> armorContents[i]))
        );

        items.putAll(
                IntStream.range(0, contents.length).boxed()
                        .filter(i -> Objects.nonNull(contents[i]))
                        .filter(i -> OpenHiveBedwars.getBWConfig().getEnchanterEnchantableItems()
                                .isEnchantableMaterial(contents[i].getType()))
                        .collect(Collectors.toMap(String::valueOf, i -> contents[i]))
        );

        return items;
    }

    public static ItemStack getItemBySlotDescriptor(PlayerInventory inventory, String slotDescriptor) {

        if (Objects.isNull(inventory) || Objects.isNull(slotDescriptor)) {
            return null;
        }

        if ("A3".equalsIgnoreCase(slotDescriptor)) {
            return inventory.getHelmet();

        } else if ("A2".equalsIgnoreCase(slotDescriptor)) {
            return inventory.getChestplate();

        } else if ("A1".equalsIgnoreCase(slotDescriptor)) {
            return inventory.getLeggings();

        } else if ("A0".equalsIgnoreCase(slotDescriptor)) {
            return inventory.getBoots();

        } else {
            try {
                return inventory.getItem(Integer.parseInt(slotDescriptor));

            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    public static boolean setItemBySlotDescriptor(PlayerInventory inventory, String slotDescriptor, ItemStack item) {

        if (Objects.isNull(inventory) || Objects.isNull(slotDescriptor)) {
            return false;
        }

        if ("A3".equalsIgnoreCase(slotDescriptor)) {
            inventory.setHelmet(item);
            return inventory.getHelmet() == item;

        } else if ("A2".equalsIgnoreCase(slotDescriptor)) {
            inventory.setChestplate(item);
            return inventory.getChestplate() == item;

        } else if ("A1".equalsIgnoreCase(slotDescriptor)) {
            inventory.setLeggings(item);
            return inventory.getLeggings() == item;

        } else if ("A0".equalsIgnoreCase(slotDescriptor)) {
            inventory.setBoots(item);
            return inventory.getBoots() == item;

        } else {
            try {
                inventory.setItem(Integer.parseInt(slotDescriptor), item);
                return inventory.getItem(Integer.parseInt(slotDescriptor)) == item;

            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    @Deprecated
    public static String getSlotDescriptorByItem(PlayerInventory inventory, ItemStack item) {
        return getSlotDescriptorByItem(inventory, item, false);
    }

    @Deprecated
    public static String getSlotDescriptorByItem(PlayerInventory inventory, ItemStack item, boolean exactMatch) {

        if (Objects.isNull(inventory) || Objects.isNull(item)) {
            return null;
        }

        if (exactMatch && inventory.getHelmet() == item || !exactMatch && Objects.equals(inventory.getHelmet(), item)) {
            return "A3";

        } else if (exactMatch && inventory.getChestplate() == item || !exactMatch && Objects.equals(inventory.getChestplate(), item)) {
            return "A2";

        } else if (exactMatch && inventory.getLeggings() == item || !exactMatch && Objects.equals(inventory.getLeggings(), item)) {
            return "A1";

        } else if (exactMatch && inventory.getBoots() == item || !exactMatch && Objects.equals(inventory.getBoots(), item)) {
            return "A0";
        }

        ItemStack[] contents = inventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            if (exactMatch && contents[i] == item || !exactMatch && Objects.equals(contents[i], item)) {
                return String.valueOf(i);
            }
        }

        return null;
    }
}
