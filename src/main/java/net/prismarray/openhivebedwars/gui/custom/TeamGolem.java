package net.prismarray.openhivebedwars.gui.custom;

import net.prismarray.openhivebedwars.gui.components.PurchasableItem;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

@Deprecated
public class TeamGolem extends PurchasableItem {

    public TeamGolem(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.PUMPKIN,
                (short) 0,
                1,
                false,
                "Team Golem",
                64,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
