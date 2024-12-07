package net.prismarray.openhivebedwars.gui.custom;

import net.prismarray.openhivebedwars.gui.components.PurchasableItem;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

@Deprecated
public class SplashDamagePotion extends PurchasableItem {

    public SplashDamagePotion(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.POTION,
                (short) 16428,
                1,
                true,
                "Splash Damage Potion",
                40,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
