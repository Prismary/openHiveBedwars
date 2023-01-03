package net.prismarray.openhivebedwars.util;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title {

    public static void send(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        IChatBaseComponent mainText = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}");

        PacketPlayOutTitle mainTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, mainText);
        PacketPlayOutTitle mainLength = new PacketPlayOutTitle(fadeIn, stay, fadeOut);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(mainTitle);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(mainLength);

        if (subtitle == null) {
            return;
        }

        IChatBaseComponent subText = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subtitle + "\"}");

        PacketPlayOutTitle subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subText);
        PacketPlayOutTitle subLength = new PacketPlayOutTitle(fadeIn, stay, fadeOut);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitle);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subLength);

        // Thanks to CostelabrMn on bukkit.org!
    }

    public static void send(Player player, String title, String subtitle) {
        send(player, title, subtitle, 10, 70, 20);
    }

    public static void sendToAll(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        Bukkit.getOnlinePlayers().forEach(player -> send(player, title, subtitle, fadeIn, stay, fadeOut));
    }

    public static void sendToAll(String title, String subtitle) {
       sendToAll(title, subtitle, 10, 70, 20);
    }

    public static void clear(Player player) {
        // TODO: implement
        throw new NotImplementedException();
    }
}
