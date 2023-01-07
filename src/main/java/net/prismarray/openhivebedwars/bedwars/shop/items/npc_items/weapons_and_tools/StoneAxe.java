package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.weapons_and_tools;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class StoneAxe extends PurchasableItem {

    public StoneAxe(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.STONE_AXE,
                (short) 0,
                1,
                false,
                "Stone Axe",
                10,
                Currency.IRON,
                false,
                false
        );
    }
}
