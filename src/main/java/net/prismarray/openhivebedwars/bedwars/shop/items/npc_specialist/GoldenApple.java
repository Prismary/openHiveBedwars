package net.prismarray.openhivebedwars.bedwars.shop.items.npc_specialist;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class GoldenApple extends PurchasableItem {

    public GoldenApple(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.GOLDEN_APPLE,
                (short) 0,
                1,
                false,
                "Golden Apple",
                15,
                Currency.EMERALD,
                false,
                false
        );
    }
}
