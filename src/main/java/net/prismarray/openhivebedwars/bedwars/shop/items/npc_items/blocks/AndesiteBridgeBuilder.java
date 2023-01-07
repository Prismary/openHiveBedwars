package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.blocks;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableCustomHead;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AndesiteBridgeBuilder extends PurchasableCustomHead {

    public AndesiteBridgeBuilder(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                "http://textures.minecraft.net/texture/2705fd94a0c431927fb4e639b0fcfb49717e412285a02b439e0112da22b2e2ec",
                1,
                false,
                "Andesite Bridge Builder",
                25,
                Currency.GOLD,
                false,
                false,
                new ItemStack(Material.STONE) // todo remove placeholder
        );
    }
}
