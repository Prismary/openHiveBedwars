package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_upgrades;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.DyeColor;

public class UpgradesTeamGUI extends FramedFullRowsGUI {

    public UpgradesTeamGUI(TeamColor teamColor) {
        super("Team Upgrades", 5, DyeColor.MAGENTA, true, () -> new UpgradesRootGUI(teamColor));
        // todo get correct frame color


        lock();
    }
}
