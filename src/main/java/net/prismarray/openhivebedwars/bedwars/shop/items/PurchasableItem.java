package net.prismarray.openhivebedwars.bedwars.shop.items;

import net.prismarray.openhivebedwars.gui.InventoryGUIItem;
import net.prismarray.openhivebedwars.util.Currency;
import net.prismarray.openhivebedwars.util.ItemNameBuilder;
import org.bukkit.Material;

import javax.annotation.Nullable;

public abstract class PurchasableItem extends InventoryGUIItem {

    public PurchasableItem(Material material, short damage, int amount, boolean enchanted, String name,
                                 int cost, Currency currency, boolean showFavStatus, boolean isFavorite) {
        this(material, damage, amount, enchanted, name, cost, currency, showFavStatus, isFavorite, null);
    }

    public PurchasableItem(Material material, short damage, int amount, boolean enchanted, String name,
                           int cost, Currency currency, boolean showFavStatus, boolean isFavorite, @Nullable String[] bonusLore) {
        super(
                material,
                damage,
                amount,
                ItemNameBuilder.getPurchasableName(name, amount),
                ItemNameBuilder.getPurchasableLore(cost, currency, showFavStatus, isFavorite, bonusLore),
                enchanted
        );

    }
}
