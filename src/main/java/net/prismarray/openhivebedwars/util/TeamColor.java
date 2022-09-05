package net.prismarray.openhivebedwars.util;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

import java.util.Arrays;

public enum TeamColor {
    RED         (ChatColor.RED,             DyeColor.PINK,          true,   false),
    GOLD        (ChatColor.GOLD,            DyeColor.ORANGE,        true,   false),
    AQUA        (ChatColor.AQUA,            DyeColor.LIGHT_BLUE,    true,   false),
    GREEN       (ChatColor.GREEN,           DyeColor.LIME,          true,   true),
    GRAY        (ChatColor.GRAY,            DyeColor.GRAY,          true,   false),
    DARK_GREEN  (ChatColor.DARK_GREEN,      DyeColor.GREEN,         false,  false),
    BLUE        (ChatColor.BLUE,            DyeColor.BLUE,          true,   true),
    DARK_RED    (ChatColor.DARK_RED,        DyeColor.RED,           false,  true),
    YELLOW      (ChatColor.YELLOW,          DyeColor.YELLOW,        true,   true),
    MAGENTA     (ChatColor.LIGHT_PURPLE,    DyeColor.MAGENTA,       true,   false),
    PURPLE      (ChatColor.DARK_PURPLE,     DyeColor.PURPLE,        false,  false),
    DARK_AQUA   (ChatColor.DARK_AQUA,       DyeColor.CYAN,          false,  false);

    public final ChatColor chatColor;
    public final DyeColor woolColor;
    public final boolean isDuosModeColor;
    public final boolean isTeamsModeColor;

    TeamColor(ChatColor chatColor, DyeColor woolColor, boolean isDuosColor, boolean isTeamsColor) {
        this.chatColor = chatColor;
        this.woolColor = woolColor;
        this.isDuosModeColor = isDuosColor;
        this.isTeamsModeColor = isTeamsColor;
    }

    public static TeamColor[] getSoloModeColours() {
        return Arrays.stream(TeamColor.values()).sorted().toArray(TeamColor[]::new);
    }

    public static TeamColor[] getDuosModeColours() {
        return Arrays.stream(TeamColor.values()).filter((c) -> c.isDuosModeColor).sorted().toArray(TeamColor[]::new);
    }

    public static TeamColor[] getTeamsModeColours() {
        return Arrays.stream(TeamColor.values()).filter((c) -> c.isTeamsModeColor).sorted().toArray(TeamColor[]::new);
    }
}
