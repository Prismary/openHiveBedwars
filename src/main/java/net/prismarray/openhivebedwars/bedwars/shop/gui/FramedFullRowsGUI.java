package net.prismarray.openhivebedwars.bedwars.shop.gui;

import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.GUIBuilder;
import org.bukkit.DyeColor;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.Callable;

public abstract class FramedFullRowsGUI extends InventoryGUIBase {

    public FramedFullRowsGUI(String name, int rows, DyeColor color, boolean hasCancel, @Nullable Callable<? extends InventoryGUIBase> previousGUIFactory) {
        super(name, correctRowCount(rows) * 9);
        rows = correctRowCount(rows);

        GUIBuilder.colorFrame(this, color, 9, rows);

        if (hasCancel) {
            GUIBuilder.setCancelButton(this, 9, rows);
        }

        if (Objects.nonNull(previousGUIFactory)) {
            GUIBuilder.setPreviousButton(this, 9, rows, previousGUIFactory);
        }
    }

    private static int correctRowCount(int rows) {
        return Math.min(Math.max(3, rows), 6);
    }
}
