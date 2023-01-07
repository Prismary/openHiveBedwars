package net.prismarray.openhivebedwars.bedwars.shop.items.npc_upgrades.root;

import net.prismarray.openhivebedwars.bedwars.shop.items.CategorySelector;
import org.bukkit.Material;

public class SummonerUpgrades extends CategorySelector {

    public SummonerUpgrades() {
        super(
                Material.BLAZE_POWDER,
                (short) 0,
                "§6§lSummoner Upgrades",
                new String[]{"§7Upgrade your summoner for:", "§7 → §eMore Speed", "§7 → §aMore Currencies"}
        );
    }
}
