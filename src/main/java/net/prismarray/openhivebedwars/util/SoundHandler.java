package net.prismarray.openhivebedwars.util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundHandler {

    public static void playerSound(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static void playerSound(Player player, String sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static void playerSound(Player player, Sound sound) {
        playerSound(player, sound, 1f, 1f);
    }

    public static void playerSound(Player player, String sound) {
        playerSound(player, sound, 1f, 1f);
    }

    public static void globalPlayerSound(Sound sound, float volume, float pitch) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerSound(player, sound, volume, pitch);
        }
    }

    public static void globalPlayerSound(String sound, float volume, float pitch) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerSound(player, sound, volume, pitch);
        }
    }

    public static void globalPlayerSound(Sound sound) {
        globalPlayerSound(sound, 1f, 1f);
    }

    public static void globalPlayerSound(String sound) {
        globalPlayerSound(sound, 1f, 1f);
    }
}
