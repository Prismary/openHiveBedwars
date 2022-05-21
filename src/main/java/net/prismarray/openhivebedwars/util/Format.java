package net.prismarray.openhivebedwars.util;

import org.bukkit.ChatColor;

public class Format {

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

}
