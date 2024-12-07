package net.prismarray.openhivebedwars.gui.custom;

import net.prismarray.openhivebedwars.gui.components.PurchasableItem;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

@Deprecated
public class PoisonCure extends PurchasableItem {

    public PoisonCure(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.MAGMA_CREAM,
                (short) 0,
                1,
                false,
                "Poison Cure",
                5,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
