package net.prismarray.openhivebedwars.gui.custom;

import net.prismarray.openhivebedwars.gui.components.PurchasableItem;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

@Deprecated
public class SplashPoisonPotion extends PurchasableItem {

    public SplashPoisonPotion(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.POTION,
                (short) 16388,
                1,
                true,
                "Splash Poison Potion",
                60,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
