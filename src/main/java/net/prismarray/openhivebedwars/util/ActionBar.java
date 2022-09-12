package net.prismarray.openhivebedwars.util;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar {
    public static void send(Player player, String message)
    {
        CraftPlayer craftPlayer = (CraftPlayer)player;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
        craftPlayer.getHandle().playerConnection.sendPacket(ppoc);

        // Thanks to MisterFantasy on spigotmc.org!
    }

    public static void sendToAll(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> send(player, message));
    }
}
