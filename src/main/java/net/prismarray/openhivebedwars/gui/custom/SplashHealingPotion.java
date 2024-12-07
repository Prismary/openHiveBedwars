package net.prismarray.openhivebedwars.gui.custom;

import net.prismarray.openhivebedwars.gui.components.PurchasableItem;
import net.prismarray.openhivebedwars.gui.components.InventoryGUIBase;
import net.prismarray.openhivebedwars.util.Currency;
import org.bukkit.Material;

@Deprecated
public class SplashHealingPotion extends PurchasableItem {

    public SplashHealingPotion(InventoryGUIBase gui, int slot) {
        super(
                gui,
                slot,
                Material.POTION,
                (short) 16421,
                1,
                true,
                "Splash Healing Potion",
                30,
                Currency.DIAMOND,
                false,
                false
        );
    }
}
