package net.prismarray.openhivebedwars.gui.components;

import net.prismarray.openhivebedwars.gui.InventoryGUIManager;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUILeftOrShiftLeftClickAction;
import net.prismarray.openhivebedwars.shop.ShopManager;
import net.prismarray.openhivebedwars.util.Currency;
import net.prismarray.openhivebedwars.util.ItemNameBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class EnchantingBookItem extends InventoryGUIItem {

    public EnchantingBookItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            Enchantment enchantmentToApply,
            String enchantmentDisplayName,
            int enchantmentLevel,
            int enchantmentCost,
            Currency enchantmentCostCurrency,
            String enchantableItemSlotDescriptor
    ) {

        super(
                actionHandlingInventoryGUI,
                slotInInventoryGUI,
                Material.ENCHANTED_BOOK,
                1,
                ItemNameBuilder.getEnchantingBookName(enchantmentDisplayName, enchantmentLevel),
                ItemNameBuilder.getEnchantingBookLore(enchantmentCost, enchantmentCostCurrency, null),
                true
        );

        addActionListenerToContainingInventory(new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUILeftOrShiftLeftClickAction a) {

                ShopManager.enchant(
                        a.getPlayer(),
                        enchantableItemSlotDescriptor,
                        enchantmentToApply,
                        enchantmentLevel,
                        enchantmentDisplayName,
                        enchantmentCost,
                        enchantmentCostCurrency
                );
                InventoryGUIManager.openInventoryGUI("npc-enchanter-root", a.getPlayer());
            }
        });
    }
}
