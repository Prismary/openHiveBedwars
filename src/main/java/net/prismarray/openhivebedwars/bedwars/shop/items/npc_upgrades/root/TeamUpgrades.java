package net.prismarray.openhivebedwars.bedwars.shop.items.npc_upgrades.root;

import net.prismarray.openhivebedwars.bedwars.shop.items.CategorySelector;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import org.bukkit.Material;

import java.util.concurrent.Callable;

public class TeamUpgrades extends CategorySelector {

    public TeamUpgrades(InventoryGUIBase gui, int slot, Callable<? extends InventoryGUIBase> destinationGUIFactory) {
        super(
                gui,
                slot,
                Material.SKULL_ITEM,
                (short) 3,
                "§b§lTeam Upgrades",
                new String[]{"§7Buy upgrades that apply to all", "§7members on your team!"},
                destinationGUIFactory
        );
    }
}
