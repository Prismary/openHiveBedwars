package net.prismarray.openhivebedwars.gui.custom;

import net.prismarray.openhivebedwars.gui.components.PurchasableItem;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

@Deprecated
public class PersonalDoggo extends PurchasableItem {

    public PersonalDoggo(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.MONSTER_EGG,
                (short) 95,
                1,
                false,
                "Personal Doggo",
                1,
                Currency.EMERALD,
                false,
                false
        );
    }
}
