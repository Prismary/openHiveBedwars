package net.prismarray.openhivebedwars.bedwars.shop.gui;

import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.GUIBuilder;
import org.bukkit.DyeColor;

public class FramedFullRows extends InventoryGUIBase {

    public FramedFullRows(String name, int rows, DyeColor color, boolean hasCancel, boolean hasPrevious) {
        super(name, correctRowCount(rows) * 9);
        rows = correctRowCount(rows);

        GUIBuilder.colorFrame(this, color, 9, rows);

        if (hasCancel) {
            GUIBuilder.setCancelButton(this, 9, rows);
        }

        if (hasPrevious) {
            // todo
        }
    }

    private static int correctRowCount(int rows) {
        return Math.min(Math.max(3, rows), 6);
    }
}
