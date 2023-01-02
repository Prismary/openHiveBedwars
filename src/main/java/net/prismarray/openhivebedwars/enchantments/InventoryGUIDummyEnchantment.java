package net.prismarray.openhivebedwars.enchantments;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class InventoryGUIDummyEnchantment extends Enchantment {

    public InventoryGUIDummyEnchantment(int id) {
        super(id);
    }

    @Override
    public String getName() {
        return "InventoryGUIDummyEnchantment";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return enchantment instanceof InventoryGUIDummyEnchantment;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return true;
    }
}
