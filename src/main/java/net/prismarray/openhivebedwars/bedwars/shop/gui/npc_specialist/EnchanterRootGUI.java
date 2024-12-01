package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_specialist;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_specialist.*;
import net.prismarray.openhivebedwars.gui.InventoryGUIContext;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
            // Material.SHEARS,

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
        super("§8§lChoose item to enchant", Math.max(5, getEnchantableItemCount(context.getOpeningPlayer())));

        // ToDo: implement

        lock();
    }

    private static int getEnchantableItemCount(Player p) {
        return getEnchantableItems(p).size();
    }

    private static List<ItemStack> getEnchantableItems(Player p) {

        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(p.getInventory().iterator(), Spliterator.ORDERED), false
        )
                .filter(item -> ENCHANTMENT_SUPPORTED.contains(item.getType()))
                .collect(Collectors.toList());
    }
}
