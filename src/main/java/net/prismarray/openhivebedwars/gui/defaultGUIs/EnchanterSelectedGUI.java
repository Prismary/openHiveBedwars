package net.prismarray.openhivebedwars.gui.defaultGUIs;

import net.prismarray.openhivebedwars.gui.InventoryGUIContext;
import net.prismarray.openhivebedwars.gui.components.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

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

        System.out.println(slotDescriptor);

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

        // ToDo: add clickable enchantment books for all applicable enchants for the selected item
        //  which are centered in the inventory GUI, depending on how many there are

        new PreviousButton(this, 21, "npc-enchanter-root");
        new CancelButton(this, 22);

        lock();
    }

}
