package net.prismarray.openhivebedwars.gui.custom;

import net.prismarray.openhivebedwars.gui.components.PurchasableItem;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

@Deprecated
public class Speed extends PurchasableItem {

    public Speed(InventoryGUIBase gui, int slot, int level) {
        super(
                gui,
                slot,
                Material.FEATHER,
                (short) 0,
                1,
                false,
                "Speed " + String.valueOf(level),
                5,
                Currency.EMERALD,
                false,
                false,
                new String[]{"§7Everyone on the team will", "§7get a speed boost!"}
        );
    }
}
