package net.prismarray.openhivebedwars.bedwars.shop.items;

import net.prismarray.openhivebedwars.gui.InventoryGUICustomHead;
import net.prismarray.openhivebedwars.util.Currency;
import net.prismarray.openhivebedwars.util.ItemNameBuilder;

import javax.annotation.Nullable;

public abstract class PurchasableCustomHead extends InventoryGUICustomHead {

    public PurchasableCustomHead(String url, int amount, boolean enchanted, String name,
                                 int cost, Currency currency, boolean showFavStatus, boolean isFavorite) {
        this(url, amount, enchanted, name, cost, currency, showFavStatus, isFavorite, null);
    }

    public PurchasableCustomHead(String url, int amount, boolean enchanted, String name,
                                 int cost, Currency currency, boolean showFavStatus, boolean isFavorite, @Nullable String[] bonusLore) {
        super(
                url,
                amount,
                ItemNameBuilder.getPurchasableName(name, amount),
                ItemNameBuilder.getPurchasableLore(cost, currency, showFavStatus, isFavorite, bonusLore),
                enchanted
        );
    }
}
