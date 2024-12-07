package net.prismarray.openhivebedwars.gui.custom;

import net.prismarray.openhivebedwars.gui.components.PurchasableItem;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

@Deprecated
public class Enderpearl extends PurchasableItem {

    public Enderpearl(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.ENDER_PEARL,
                (short) 0,
                1,
                false,
                "Enderpearl",
                15,
                Currency.EMERALD,
                false,
                false
        );
    }
}
