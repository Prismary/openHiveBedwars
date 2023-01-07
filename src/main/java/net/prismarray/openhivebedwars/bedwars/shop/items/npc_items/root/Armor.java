package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root;

import net.prismarray.openhivebedwars.bedwars.shop.items.CategorySelector;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import org.bukkit.Material;

import java.util.concurrent.Callable;

public class Armor extends CategorySelector {

    public Armor(InventoryGUIBase gui, int slot, Callable<? extends InventoryGUIBase> destinationGUIFactory) {
        super(
                gui,
                slot,
                Material.IRON_CHESTPLATE,
                (short) 0,
                "§c§lArmor",
                new String[]{"§7Be prepared when you encounter", "§7your evil enemies!"},
                destinationGUIFactory
        );
    }
}
