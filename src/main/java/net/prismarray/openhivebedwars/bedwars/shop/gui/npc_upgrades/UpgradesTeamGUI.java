package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_upgrades;

import net.prismarray.openhivebedwars.gui.components.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.gui.InventoryGUIContext;
import org.bukkit.DyeColor;

public class UpgradesTeamGUI extends FramedFullRowsGUI {

    public UpgradesTeamGUI(InventoryGUIContext context) {
        super("Team Upgrades", 5, DyeColor.MAGENTA, true, "npc-upgrades-root", null);
        // todo get correct frame color


        lock();
    }
}
