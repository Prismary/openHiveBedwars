package net.prismarray.openhivebedwars.gui.components;

import net.prismarray.openhivebedwars.gui.InventoryGUIManager;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUILeftOrShiftLeftClickAction;
import net.prismarray.openhivebedwars.util.ItemNameBuilder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnchantableItem extends InventoryGUIItem {


    public EnchantableItem(
            InventoryGUIBase actionHandlingInventoryGUI,
            int slotInInventoryGUI,
            ItemStack reference,
            @Nonnull String referenceSlot
    ) {
        super(
                actionHandlingInventoryGUI,
                slotInInventoryGUI,
                reference.getType(),
                reference.getDurability(),
                reference.getAmount(),
                reference.getItemMeta().getDisplayName(),
                ItemNameBuilder.getEnchantableLore(reference.getItemMeta().getLore()),
                reference.getEnchantments().size() > 0,
                Stream.concat(
                        reference.getItemMeta().getItemFlags().stream(),
                        Stream.of(ItemFlag.HIDE_ATTRIBUTES)
                ).collect(Collectors.toList())
        );

        addActionListenerToContainingInventory(new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUILeftOrShiftLeftClickAction a) {

                Map<String, String> additionalContext = new HashMap<>();
                additionalContext.put("enchanter_reference_slot", referenceSlot);
                additionalContext.put("enchanter_reference_item_name", ItemNameBuilder.getItemName(reference));

                InventoryGUIManager.openInventoryGUI("npc-enchanter-selected", a.getPlayer(), additionalContext);
            }
        });
    }
}
