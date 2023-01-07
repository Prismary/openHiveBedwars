package net.prismarray.openhivebedwars.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public enum Currency {
    IRON         ("Iron Ingot",         Material.IRON_INGOT,             ChatColor.WHITE),
    GOLD         ("Gold Ingot",         Material.GOLD_INGOT,             ChatColor.YELLOW),
    DIAMOND         ("Diamond",         Material.DIAMOND,             ChatColor.AQUA),
    EMERALD         ("Emerald",         Material.EMERALD,             ChatColor.GREEN);

    public final String chatName;
    public final Material material;
    public final ChatColor color;

    Currency(String name, Material material, ChatColor color) {
        this.chatName = name;
        this.material = material;
        this.color = color;
    }

    public static Currency[] getCurrencies() {
        return Arrays.stream(Currency.values()).sorted().toArray(Currency[]::new);
    }

    public static String getNamePlural(Currency currency) {
        switch (currency) {
            case IRON:
            case GOLD:
            case DIAMOND:
            case EMERALD:
                return currency.chatName + "s";
            default:
                return currency.chatName;
        }
    }
}
