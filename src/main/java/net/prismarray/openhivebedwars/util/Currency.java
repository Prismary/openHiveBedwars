package net.prismarray.openhivebedwars.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public enum Currency {
    IRON        ("Iron Ingot",  "Iron Ingots",  Material.IRON_INGOT,    ChatColor.WHITE),
    GOLD        ("Gold Ingot",  "Gold Ingots",  Material.GOLD_INGOT,    ChatColor.YELLOW),
    DIAMOND     ("Diamond",     "Diamonds",     Material.DIAMOND,       ChatColor.AQUA),
    EMERALD     ("Emerald",     "Emeralds",     Material.EMERALD,       ChatColor.GREEN);

    public final String chatName;
    public final String chatNamePlural;
    public final Material material;
    public final ChatColor color;

    Currency(String name, String namePlural, Material material, ChatColor color) {
        this.chatName = name;
        this.chatNamePlural = namePlural;
        this.material = material;
        this.color = color;
    }

    public String getChatNameForAmount(int amount) {

        if (amount == 1 || amount == -1) {
            return this.chatName;

        } else {
            return this.chatNamePlural;
        }
    }

    public static Currency[] getCurrencies() {
        return Arrays.stream(Currency.values()).sorted().toArray(Currency[]::new);
    }
}
