package net.prismarray.openhivebedwars.gui.custom;

import net.prismarray.openhivebedwars.gui.components.PurchasableItem;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

@Deprecated
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
