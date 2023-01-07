package net.prismarray.openhivebedwars.bedwars.shop.items.npc_upgrades.root;

import net.prismarray.openhivebedwars.bedwars.shop.items.CategorySelector;
import org.bukkit.Material;

public class TeamUpgrades extends CategorySelector {

    public TeamUpgrades() {
        super(
                Material.SKULL_ITEM,
                (short) 3,
                "§b§lTeam Upgrades",
                new String[]{"§7Buy upgrades that apply to all", "§7members on your team!"}
        );
    }
}
