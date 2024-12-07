package net.prismarray.openhivebedwars.gui.custom;

import net.prismarray.openhivebedwars.gui.components.PurchasableItem;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

@Deprecated
public class InstantTNT extends PurchasableItem {

    public InstantTNT(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.TNT,
                (short) 0,
                1,
                false,
                "Instant TNT",
                1,
                Currency.EMERALD,
                false,
                false
        );
    }
}
