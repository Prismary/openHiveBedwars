package net.prismarray.openhivebedwars.bedwars.shop.items.npc_upgrades.root;

import net.prismarray.openhivebedwars.gui.components.CategorySelector;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import org.bukkit.Material;

public class TeamUpgrades extends CategorySelector {

    public TeamUpgrades(InventoryGUIBase gui, int slot, String destinationGUI) {
        super(
                gui,
                slot,
                Material.SKULL_ITEM,
                (short) 3,
                "§b§lTeam Upgrades",
                new String[]{"§7Buy upgrades that apply to all", "§7members on your team!"},
                destinationGUI
        );
    }
}
