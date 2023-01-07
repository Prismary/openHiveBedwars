package net.prismarray.openhivebedwars.bedwars.shop.items.npc_upgrades.root;

import net.prismarray.openhivebedwars.bedwars.shop.items.CategorySelector;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import org.bukkit.Material;

import java.util.concurrent.Callable;

public class SummonerUpgrades extends CategorySelector {

    public SummonerUpgrades(InventoryGUIBase gui, int slot, Callable<? extends InventoryGUIBase> destinationGUIFactory) {
        super(
                gui,
                slot,
                Material.BLAZE_POWDER,
                (short) 0,
                "§6§lSummoner Upgrades",
                new String[]{"§7Upgrade your summoner for:", "§7 → §eMore Speed", "§7 → §aMore Currencies"},
                destinationGUIFactory
        );
    }
}
