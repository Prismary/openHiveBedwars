package net.prismarray.openhivebedwars.bedwars.shop.items.npc_items.root;

import net.prismarray.openhivebedwars.bedwars.shop.items.CategorySelector;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import org.bukkit.Material;

import java.util.concurrent.Callable;

public class WeaponsAndTools extends CategorySelector {

    public WeaponsAndTools(InventoryGUIBase gui, int slot, Callable<? extends InventoryGUIBase> destinationGUIFactory) {
        super(
                gui,
                slot,
                Material.IRON_SWORD,
                (short) 0,
                "§b§lWeapons & Tools",
                new String[]{"§7A choice of weapons and tools", "§7to use in your fights!"},
                destinationGUIFactory
        );
    }
}
