package net.prismarray.openhivebedwars.bedwars.shop.items.npc_specialist;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

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
