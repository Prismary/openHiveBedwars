package net.prismarray.openhivebedwars.bedwars.shop.items.npc_specialist;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

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
