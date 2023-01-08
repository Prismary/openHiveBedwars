package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_upgrades;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.DyeColor;

public class UpgradesSummonerGUI extends FramedFullRowsGUI {

    public UpgradesSummonerGUI(TeamColor teamColor) {
        super("Summoner Upgrades", 5, DyeColor.MAGENTA, true, () -> new UpgradesRootGUI(teamColor));


        lock();
    }
}
