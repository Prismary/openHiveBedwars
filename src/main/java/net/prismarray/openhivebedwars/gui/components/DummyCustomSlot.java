package net.prismarray.openhivebedwars.gui.components;

import org.bukkit.Material;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DummyCustomSlot extends InventoryGUIItem {

    public DummyCustomSlot(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.INK_SACK,
                (short) 8,
                1,
                "§9Customizable Slot",
                Stream.of(
                        "",
                        "§7This feature is",
                        "§7not yet available."
                ).collect(Collectors.toList())
        );
    }
}
