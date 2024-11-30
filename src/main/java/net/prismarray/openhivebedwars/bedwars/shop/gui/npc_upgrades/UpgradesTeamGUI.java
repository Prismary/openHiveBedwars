package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_upgrades;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import net.prismarray.openhivebedwars.gui.InventoryGUIContext;
import org.bukkit.DyeColor;

public class UpgradesTeamGUI extends FramedFullRowsGUI {

    public UpgradesTeamGUI(InventoryGUIContext context) {
        super("Team Upgrades", 5, DyeColor.MAGENTA, true, () -> new UpgradesRootGUI(context), null);
        // todo get correct frame color


        lock();
    }
}
