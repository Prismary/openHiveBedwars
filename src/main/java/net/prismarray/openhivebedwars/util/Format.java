package net.prismarray.openhivebedwars.util;

import org.bukkit.ChatColor;

public class Format {

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static char getRoundNumeral(int input) {
        switch (input) {
            case 1:
                return '➊';
            case 2:
                return '➋';
            case 3:
                return '➌';
            case 4:
                return '➍';
            case 5:
                return '➎';
            case 6:
                return '➏';
            case 7:
                return '➐';
            case 8:
                return '➑';
            case 9:
                return '➒';
            default:
                return ' ';
        }
    }
}
