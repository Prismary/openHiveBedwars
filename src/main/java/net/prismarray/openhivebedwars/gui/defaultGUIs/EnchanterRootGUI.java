package net.prismarray.openhivebedwars.gui.defaultGUIs;

import net.prismarray.openhivebedwars.gui.InventoryGUIContext;
import net.prismarray.openhivebedwars.gui.components.EnchantableItem;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnchanterRootGUI extends InventoryGUIBase {

    public static final Set<Material> ENCHANTMENT_SUPPORTED = Stream.of(

            Material.DIAMOND_SWORD,
            Material.GOLD_SWORD,
            Material.IRON_SWORD,
            Material.STONE_SWORD,
            Material.WOOD_SWORD,

            Material.DIAMOND_AXE,
            Material.GOLD_AXE,
            Material.IRON_AXE,
            Material.STONE_AXE,
            Material.WOOD_AXE,

            Material.DIAMOND_PICKAXE,
            Material.GOLD_PICKAXE,
            Material.IRON_PICKAXE,
            Material.STONE_PICKAXE,
            Material.WOOD_PICKAXE,

            Material.DIAMOND_SPADE,
            Material.GOLD_SPADE,
            Material.IRON_SPADE,
            Material.STONE_SPADE,
            Material.WOOD_SPADE,

            Material.BOW,
            Material.SHEARS,

            Material.DIAMOND_HELMET,
            Material.GOLD_HELMET,
            Material.IRON_HELMET,
            Material.CHAINMAIL_HELMET,
            Material.LEATHER_HELMET,

            Material.DIAMOND_CHESTPLATE,
            Material.GOLD_CHESTPLATE,
            Material.IRON_CHESTPLATE,
            Material.CHAINMAIL_CHESTPLATE,
            Material.LEATHER_CHESTPLATE,

            Material.DIAMOND_LEGGINGS,
            Material.GOLD_LEGGINGS,
            Material.IRON_LEGGINGS,
            Material.CHAINMAIL_LEGGINGS,
            Material.LEATHER_LEGGINGS,

            Material.DIAMOND_BOOTS,
            Material.GOLD_BOOTS,
            Material.IRON_BOOTS,
            Material.CHAINMAIL_BOOTS,
            Material.LEATHER_BOOTS

    ).collect(Collectors.toSet());


    public EnchanterRootGUI(InventoryGUIContext context) {
        super(
                "§8§lChoose item to enchant",
                InventoryGUIBase.getSmallestPossibleInventorySize(getEnchantableItemCount(context.getOpeningPlayer()))
        );

        int currentSlot = 0;

        for (ItemStack enchantableItem : getEnchantableItems(context.getOpeningPlayer())) {

            new EnchantableItem(
                    this,
                    currentSlot++,
                    enchantableItem,
                    getSlotDescriptorByItem(context.getOpeningPlayer().getInventory(), enchantableItem)
            );
        }

        lock();
    }

    private static int getEnchantableItemCount(Player p) {

        return getEnchantableItems(p).size();
    }

    private static List<ItemStack> getEnchantableItems(Player p) {

        return Stream.concat(
                        Arrays.stream(p.getInventory().getContents()),
                        Arrays.stream(p.getInventory().getArmorContents())
                )
                .filter(Objects::nonNull)
                .filter(item -> ENCHANTMENT_SUPPORTED.contains(item.getType()))
                .collect(Collectors.toList());
    }

    public static ItemStack getItemBySlotDescriptor(PlayerInventory inventory, String slotDescriptor) {

        if (Objects.isNull(inventory) || Objects.isNull(slotDescriptor)) {
            return null;
        }

        if ("A0".equalsIgnoreCase(slotDescriptor)) {
            return inventory.getHelmet();

        } else if ("A1".equalsIgnoreCase(slotDescriptor)) {
            return inventory.getChestplate();

        } else if ("A2".equalsIgnoreCase(slotDescriptor)) {
            return inventory.getLeggings();

        } else if ("A3".equalsIgnoreCase(slotDescriptor)) {
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

        if ("A0".equalsIgnoreCase(slotDescriptor)) {
            inventory.setHelmet(item);
            return inventory.getHelmet() == item;

        } else if ("A1".equalsIgnoreCase(slotDescriptor)) {
            inventory.setChestplate(item);
            return inventory.getChestplate() == item;

        } else if ("A2".equalsIgnoreCase(slotDescriptor)) {
            inventory.setLeggings(item);
            return inventory.getLeggings() == item;

        } else if ("A3".equalsIgnoreCase(slotDescriptor)) {
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

    public static String getSlotDescriptorByItem(PlayerInventory inventory, ItemStack item) {
        return getSlotDescriptorByItem(inventory, item, false);
    }

    public static String getSlotDescriptorByItem(PlayerInventory inventory, ItemStack item, boolean exactMatch) {

        if (Objects.isNull(inventory) || Objects.isNull(item)) {
            return null;
        }

        if (exactMatch && inventory.getHelmet() == item || !exactMatch && Objects.equals(inventory.getHelmet(), item)) {
            return "A0";

        } else if (exactMatch && inventory.getChestplate() == item || !exactMatch && Objects.equals(inventory.getChestplate(), item)) {
            return "A1";

        } else if (exactMatch && inventory.getLeggings() == item || !exactMatch && Objects.equals(inventory.getLeggings(), item)) {
            return "A2";

        } else if (exactMatch && inventory.getBoots() == item || !exactMatch && Objects.equals(inventory.getBoots(), item)) {
            return "A3";
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
