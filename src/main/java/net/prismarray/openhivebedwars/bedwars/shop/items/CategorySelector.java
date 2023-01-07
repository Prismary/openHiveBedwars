package net.prismarray.openhivebedwars.bedwars.shop.items;

import net.prismarray.openhivebedwars.gui.InventoryGUIItem;
import org.bukkit.Material;

public abstract class CategorySelector extends InventoryGUIItem {

    public CategorySelector(Material material, short damage, String name, String[] lore) {
        super(
                material,
                damage,
                1,
                name,
                getFullLore(name, lore)
        );
    }

    public static String[] getFullLore(String name, String[] lore) {
        String[] fullLore = new String[lore.length + 3];

        fullLore[0] = "";
        fullLore[lore.length + 1] = "";
        fullLore[lore.length + 2] = "§b► Click to view " + name;

        System.arraycopy(lore, 0, fullLore, 1, lore.length);

        return fullLore;
    }
}
