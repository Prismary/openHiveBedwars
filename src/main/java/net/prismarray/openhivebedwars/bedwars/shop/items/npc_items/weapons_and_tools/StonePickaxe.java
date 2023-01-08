package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.weapons_and_tools;

import net.prismarray.openhivebedwars.bedwars.shop.items.PurchasableItem;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

public class StonePickaxe extends PurchasableItem {

    public StonePickaxe(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.STONE_PICKAXE,
                (short) 0,
                1,
                false,
                "Stone Pickaxe §4(unknown)", // todo find real price
                5,
                Currency.IRON,
                false,
                false
        );
    }
}
