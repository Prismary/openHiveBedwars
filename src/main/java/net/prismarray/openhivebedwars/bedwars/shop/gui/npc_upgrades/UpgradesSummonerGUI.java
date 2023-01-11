package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_upgrades;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.bedwars.shop.items.npc_upgrades.summoner_upgrades.SummonerUpgrade;
import net.prismarray.openhivebedwars.util.Currency;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.DyeColor;

public class UpgradesSummonerGUI extends FramedFullRowsGUI {

    public UpgradesSummonerGUI(TeamColor teamColor) {
        super("Summoner Upgrades", 5, DyeColor.MAGENTA, true, () -> new UpgradesRootGUI(teamColor));

        // Iron row
        new SummonerUpgrade(this, 11, teamColor, Currency.IRON, 1, Currency.IRON, 0, 0, () -> new UpgradesSummonerGUI(teamColor));
        new SummonerUpgrade(this, 20, teamColor, Currency.IRON, 2, Currency.IRON, 5, 0, () -> new UpgradesSummonerGUI(teamColor));
        new SummonerUpgrade(this, 29, teamColor, Currency.IRON, 3, Currency.IRON, 10, 0, () -> new UpgradesSummonerGUI(teamColor));

        // Gold row
        new SummonerUpgrade(this, 13, teamColor, Currency.GOLD, 1, Currency.DIAMOND, 1, 1, () -> new UpgradesSummonerGUI(teamColor));
        new SummonerUpgrade(this, 22, teamColor, Currency.GOLD, 2, Currency.GOLD, 5, 1, () -> new UpgradesSummonerGUI(teamColor));
        new SummonerUpgrade(this, 31, teamColor, Currency.GOLD, 3, Currency.GOLD, 10, 1, () -> new UpgradesSummonerGUI(teamColor));

        // Diamond row
        new SummonerUpgrade(this, 15, teamColor, Currency.DIAMOND, 1, Currency.EMERALD, 5, 2, () -> new UpgradesSummonerGUI(teamColor));
        new SummonerUpgrade(this, 24, teamColor, Currency.DIAMOND, 2, Currency.DIAMOND, 5, 2, () -> new UpgradesSummonerGUI(teamColor));
        new SummonerUpgrade(this, 33, teamColor, Currency.DIAMOND, 3, Currency.DIAMOND, 10, 2, () -> new UpgradesSummonerGUI(teamColor));

        lock();
    }
}
