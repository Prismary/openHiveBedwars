package net.prismarray.openhivebedwars.bedwars.shop.gui;

import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.GUIBuilder;
import org.bukkit.DyeColor;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class FramedFullRowsGUI extends InventoryGUIBase {

    public FramedFullRowsGUI(String name, int rows, DyeColor color, boolean hasCancel, @Nullable String previousGUI, @Nullable String nextGUI) {
        super(name, correctRowCount(rows) * 9);
        rows = correctRowCount(rows);

        GUIBuilder.colorFrame(this, color, 9, rows);

        if (hasCancel) {
            GUIBuilder.setCancelButton(this, 9, rows);
        }

        if (Objects.nonNull(previousGUI)) {
            GUIBuilder.setPreviousButton(this, 9, rows, previousGUI);
        }

        if (Objects.nonNull(nextGUI)) {
            GUIBuilder.setNextButton(this, 9, rows, nextGUI);
        }
    }

    private static int correctRowCount(int rows) {
        return Math.min(Math.max(3, rows), 6);
    }
}
