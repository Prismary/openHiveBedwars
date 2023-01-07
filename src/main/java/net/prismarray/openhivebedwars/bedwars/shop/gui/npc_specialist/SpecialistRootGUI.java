package net.prismarray.openhivebedwars.bedwars.shop.gui.npc_specialist;

import net.prismarray.openhivebedwars.bedwars.shop.gui.FramedFullRowsGUI;
import org.bukkit.DyeColor;

public class SpecialistRootGUI extends FramedFullRowsGUI {

    public SpecialistRootGUI() {
        super("The Specialist", 4, DyeColor.ORANGE, true, null);

        

        lock();
    }
}
