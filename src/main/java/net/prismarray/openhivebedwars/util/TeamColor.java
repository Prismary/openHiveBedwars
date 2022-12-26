package net.prismarray.openhivebedwars.util;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

import java.util.Arrays;

public enum TeamColor {
    RED         ("Red",         ChatColor.RED,             DyeColor.PINK,          true,   false),
    GOLD        ("Gold",        ChatColor.GOLD,            DyeColor.ORANGE,        true,   false),
    AQUA        ("Aqua",        ChatColor.AQUA,            DyeColor.LIGHT_BLUE,    true,   false),
    GREEN       ("Green",       ChatColor.GREEN,           DyeColor.LIME,          true,   true),
    GRAY        ("Gray",        ChatColor.GRAY,            DyeColor.GRAY,          true,   false),
    DARK_GREEN  ("Dark Green",  ChatColor.DARK_GREEN,      DyeColor.GREEN,         false,  false),
    BLUE        ("Blue",        ChatColor.BLUE,            DyeColor.BLUE,          true,   true),
    DARK_RED    ("Dark Red",    ChatColor.DARK_RED,        DyeColor.RED,           false,  true),
    YELLOW      ("Yellow",      ChatColor.YELLOW,          DyeColor.YELLOW,        true,   true),
    MAGENTA     ("Magenta",     ChatColor.LIGHT_PURPLE,    DyeColor.MAGENTA,       true,   false),
    PURPLE      ("Purple",      ChatColor.DARK_PURPLE,     DyeColor.PURPLE,        false,  false),
    DARK_AQUA   ("Dark Aqua",   ChatColor.DARK_AQUA,       DyeColor.CYAN,          false,  false);

    public final String chatName;
    public final ChatColor chatColor;
    public final DyeColor woolColor;
    public final boolean isDuosModeColor;
    public final boolean isTeamsModeColor;

    TeamColor(String chatName, ChatColor chatColor, DyeColor woolColor, boolean isDuosColor, boolean isTeamsColor) {
        this.chatName = chatName;
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
