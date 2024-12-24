package net.prismarray.openhivebedwars.shop;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.gui.defaultGUIs.EnchanterRootGUI;
import net.prismarray.openhivebedwars.shop.npc.*;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.Currency;
import net.prismarray.openhivebedwars.util.ItemNameBuilder;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ShopManager {

    private static final ShopManager instance = new ShopManager();

    private final ArrayList<Shop> shops;

    private ShopManager() {
        shops = new ArrayList<>();
    }

    public static ShopManager getInstance() {
        return instance;
    }

    public void addShop(Shop shop) {
        shops.add(shop);
    }

    public void spawnNPCs() {
        Game.getMapConfig().spawnNPCs();
    }

    public Shop getShop(Entity entity) {
        for (Shop shop : shops) {
            if (shop.getEntity() == entity) {
                return shop;
            }
        }

        return null;
    }

    public static void purchase(Player player, String name, ItemStack item, Currency currency, int cost) {
        if (!takePayment(player, currency, cost, name)) { // Attempt to take payment
            return;
        }

        if (OpenHiveBedwars.getBWConfig().getUnbreakableTools().contains(item.getType())) {

            net.minecraft.server.v1_8_R3.ItemStack craftItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tag = craftItem.hasTag() ? craftItem.getTag() : new NBTTagCompound();
            tag.setInt("Unbreakable", 1);
            craftItem.setTag(tag);
            item = CraftItemStack.asBukkitCopy(craftItem);
        }

        // Add purchased item, if possible
        Map<Integer, ItemStack> leftOver = player.getInventory().addItem(item);

        // If target inventory is full, drop item(s) instead
        if (leftOver.size() > 0) {
            leftOver.values().forEach(itemStack -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
        }

        Broadcast.toPlayer(player, String.format(
                "§aPurchased §f%s §afor %s%s %s.",
                name,
                currency.color,
                cost,
                currency.getChatNameForAmount(cost)
        ));
    }

    public static void enchant(
            Player player,
            String enchantingItemSlotDescriptor,
            Enchantment enchantmentToApply,
            int enchantmentLevel,
            String enchantmentDisplayName,
            int cost,
            Currency currency
    ) {
        ItemStack item = EnchanterRootGUI.getItemBySlotDescriptor(player.getInventory(), enchantingItemSlotDescriptor);

        if (Objects.isNull(item)) {
            Broadcast.toPlayer(player, "§cThe selected item is no longer in your inventory!");
            return;
        }

        if (!takePayment(player, currency, cost, enchantmentDisplayName)) { // Attempt to take payment
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (!OpenHiveBedwars.getBWConfig().getEnchanterAllowMultipleEnchantmentsPerItem()) {
            List<Enchantment> toRemove = new ArrayList<>(meta.getEnchants().keySet());
            toRemove.forEach(meta::removeEnchant);
        }

        meta.addEnchant(enchantmentToApply, enchantmentLevel, true);
        item.setItemMeta(meta);

        EnchanterRootGUI.setItemBySlotDescriptor(player.getInventory(), enchantingItemSlotDescriptor, item);

        Broadcast.toPlayer(player, String.format(
                "§aEnchanted §f%s §awith §f%s §afor %s%s %s.",
                ItemNameBuilder.getItemName(item),
                enchantmentDisplayName,
                currency.color,
                cost,
                currency.getChatNameForAmount(cost)
        ));
    }

    public static boolean takePayment(Player player, Currency currency, int cost, String name) {
        int playerCurrency = countCurrency(player, currency);

        if (playerCurrency < cost) {
            Broadcast.toPlayer(player, String.format(
                    "§cYou need %s%s %s §c(§7%s §cmore) for §7%s§c!",
                    currency.color,
                    cost,
                    currency.getChatNameForAmount(cost),
                    cost - playerCurrency,
                    name
            ));
            return false;
        }

        player.getInventory().removeItem(new ItemStack(currency.material, cost));
        return true;
    }

    public static int countCurrency(Player player, Currency currency) {
        AtomicInteger amount = new AtomicInteger();
        player.getInventory().all(currency.material).forEach((integer, itemStack) -> {
            amount.addAndGet(itemStack.getAmount());
        });
        return amount.get();
    }
}
