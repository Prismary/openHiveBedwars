package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.gui.InventoryGUIItem;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class SpectatorCompass extends InventoryGUIItem {

    public SpectatorCompass() {
        super(
                null,
                -1,
                Material.COMPASS,
                (short) 0,
                1,
                "Spectator Compass", // ToDo: change to actual name
                new String[]{ // ToDo: change to actual lore
                        "Lorem ipsum dolor sit amet."
                },
                true,
                null,
                false
        );
    }

    @Override
    public boolean equals(Object obj) {

        if (Objects.isNull(obj)) {
            return false;
        }

        if (!(obj instanceof ItemStack)) {
            return false;
        }

        ItemStack other = (ItemStack) obj;

        return other.getType() == getType()
                && other.getDurability() == getDurability()
                && other.getAmount() == getAmount()
                && other.getData().getData() == getData().getData()
                && Objects.equals(other.getItemMeta(), getItemMeta()); // itemMeta covers enchantments, name, lore and flags
    }

    public static void removeAllFromInventory(Inventory inventory) {

        SpectatorCompass reference = new SpectatorCompass();

        Arrays.stream(inventory.getContents())
                .filter(reference::equals)
                .forEach(inventory::remove);
    }
}
