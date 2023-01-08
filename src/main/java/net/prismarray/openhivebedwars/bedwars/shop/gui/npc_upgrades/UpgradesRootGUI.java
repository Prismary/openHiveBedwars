package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_upgrades;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_upgrades.root.SummonerUpgrades;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_upgrades.root.TeamUpgrades;
import net.prismarray.openhivebedwars.util.TeamColor;

public class UpgradesRootGUI extends FramedFullRowsGUI {

    public UpgradesRootGUI(TeamColor teamColor) {
        super(String.format("Team %s Upgrades", teamColor.chatName), 5, teamColor.woolColor, true, null);

        new SummonerUpgrades(this, 20, () -> new UpgradesSummonerGUI(teamColor));
        new TeamUpgrades(this, 22, () -> new UpgradesTeamGUI(teamColor));

        lock();
    }
}
