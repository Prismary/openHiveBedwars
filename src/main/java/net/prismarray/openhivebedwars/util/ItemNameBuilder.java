package net.prismarray.openhivebedwars.util;

import javax.annotation.Nullable;

public class ItemNameBuilder {

    public static String getPurchasableName(String name, int amount) {
        return String.format(
                "§f§l%sx §b§l%s",
                amount,
                name
        );
    }

    public static String[] getPurchasableLore(int cost, Currency currency, boolean showFavStatus, boolean isFavorite, @Nullable String[] bonusLore) {

        // Format bonus lore
        if (bonusLore == null) {
            bonusLore = new String[]{""};
        } else {
            String[] tempLore = new String[bonusLore.length + 2];
            System.arraycopy(bonusLore, 0, tempLore, 1, bonusLore.length);
            tempLore[0] = "";
            tempLore[tempLore.length - 1] = "";
            bonusLore = tempLore;
        }

        // Prepare lore array
        String[] lore = new String[(showFavStatus) ? 6 + bonusLore.length : 5 + bonusLore.length];

        System.arraycopy(bonusLore, 0, lore, 0, bonusLore.length);

        lore[bonusLore.length] = "§6§lCost";
        lore[1 + bonusLore.length] = String.format(
                "  %s%s %s",
                currency.color,
                cost,
                ((cost > 1) ? Currency.getNamePlural(currency) : currency.chatName)
        );
        lore[2 + bonusLore.length] = "";
        lore[3 + bonusLore.length] = ((isFavorite) ? "§d➜ Right-Click to unfavorite" : "§d➜ Right-Click to favorite");
        lore[lore.length - 1] = "§b➜ Left-Click to purchase"; // Will overwrite previous line if fav isn't shown

        return lore;
    }
}
