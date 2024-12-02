package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_upgrades;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_upgrades.root.SummonerUpgrades;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_upgrades.root.TeamUpgrades;
import net.prismarray.openhivebedwars.gui.InventoryGUIContext;

public class UpgradesRootGUI extends FramedFullRowsGUI {

    public UpgradesRootGUI(InventoryGUIContext context) {
        super(
                String.format("Team %s Upgrades", context.getOpeningPlayerTeam().getColor().chatName),
                5,
                context.getOpeningPlayerTeam().getColor().woolColor,
                true,
                null,
                null
        );

        new SummonerUpgrades(this, 20, "npc-upgrades-summoner");
        new TeamUpgrades(this, 22, "npc-upgrades-team");

        lock();
    }
}
