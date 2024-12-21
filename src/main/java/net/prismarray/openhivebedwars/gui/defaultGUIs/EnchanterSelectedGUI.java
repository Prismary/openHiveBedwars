package net.prismarray.openhivebedwars.gui.defaultGUIs;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.config.Config;
import net.prismarray.openhivebedwars.gui.InventoryGUIContext;
import net.prismarray.openhivebedwars.gui.components.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchanterSelectedGUI extends InventoryGUIBase {

    public EnchanterSelectedGUI(InventoryGUIContext context) {
        super(
                String.format(
                        "§8Enchanting %s",
                        context.getAdditionalContext("enchanter_reference_item_name", "Item")
                ),
                27
        );

        String slotDescriptor = context.getAdditionalContext("enchanter_reference_slot", "0");

        ItemStack referenceItem = EnchanterRootGUI.getItemBySlotDescriptor(
                context.getOpeningPlayer().getInventory(), slotDescriptor
        );

        new InventoryGUIItem(
                this,
                4,
                referenceItem.getType(),
                referenceItem.getDurability(),
                referenceItem.getAmount(),
                referenceItem.getItemMeta().getDisplayName(),
                referenceItem.getItemMeta().getLore(),
                referenceItem.getEnchantments().size() > 0,
                new ArrayList<>(referenceItem.getItemMeta().getItemFlags())
        );

        List<Config.EnchantableItemsConfig.EnchantmentSpecification> supportedEnchantments =
                OpenHiveBedwars.getBWConfig().getEnchanterEnchantableItems().getSupportedEnchantmens(referenceItem.getType());
        int currentSlot = 9 + 4 - (int) Math.floor(supportedEnchantments.size() / 2.0);

        for (Config.EnchantableItemsConfig.EnchantmentSpecification enchantmentSpec : supportedEnchantments) {
            new EnchantingBookItem(
                    this,
                    currentSlot++,
                    enchantmentSpec.getEnchantment(),
                    enchantmentSpec.getDisplayName(),
                    enchantmentSpec.getLevel(),
                    enchantmentSpec.getCost(),
                    enchantmentSpec.getCurrency(),
                    slotDescriptor
            );
        }

        if (OpenHiveBedwars.getBWConfig().getEnchanterAddPreviousAndCancelButtons()) {
            new PreviousButton(this, 21, "npc-enchanter-root");
            new CancelButton(this, 22);
        }

        lock();
    }

}
